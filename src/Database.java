import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
 * @author Ishaan Limaye, Jaden Fang, Aiden Prananta, Christopher Griffin
 * @version November 9, 2025
 */

public class Database implements DatabaseGuide {

    public static Object lock = new Object();
    File reservationsFile = new File("reservations.txt");
    File usersFile = new File("users.txt");

    ArrayList<User> users;
    ArrayList<Reservation> reservations;

    public Database() {
        users = readUsers();
        reservations = readReservations();
    }

    public void run() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(47906);
        } catch (IOException e) {
            System.out.println("Could not listen on port: 47906");
            return;
        }

        System.out.println("Server is listening on port 47906");

        while (true) {
            try {
                Socket socket = serverSocket.accept();
                new Thread(() -> handleClient(socket)).start();
            } catch (IOException e) {
                System.out.println("Accept failed: 47906");
            }
        }
    }

    private void handleClient(Socket socket) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {

            String commandLine;
            while ((commandLine = reader.readLine()) != null) {
                System.out.println("Received: " + commandLine);
                String[] parts = commandLine.split(";");
                String command = parts[0];

                if (command.equals("LOGIN")) {
                    // LOGIN;username;password
                    if (parts.length < 3) {
                        writer.println("false");
                        continue;
                    }
                    String u = parts[1];
                    String p = parts[2];
                    boolean valid = false;
                    synchronized (lock) {
                        users = readUsers(); // Refresh
                        for (User user : users) {
                            if (user.getUserName().equals(u) && user.getPassword().equals(p)) {
                                valid = true;
                                break;
                            }
                        }
                    }
                    writer.println(valid ? "true" : "false");

                } else if (command.equals("CHECK_USER")) {
                    // CHECK_USER;username
                    if (parts.length < 2) {
                        writer.println("false");
                        continue;
                    }
                    String u = parts[1];
                    boolean exists = false;
                    synchronized (lock) {
                        users = readUsers(); // Refresh
                        for (User user : users) {
                            if (user.getUserName().equals(u)) {
                                exists = true;
                                break;
                            }
                        }
                    }
                    writer.println(exists ? "true" : "false");

                } else if (command.equals("CREATE_USER")) {
                    // CREATE_USER;username;password
                    if (parts.length < 3) {
                        writer.println("false");
                        continue;
                    }
                    String u = parts[1];
                    String p = parts[2];
                    makeNewUser(u, p);
                    writer.println("true");

                } else if (command.equals("DELETE_USER")) {
                    // DELETE_USER;username
                    if (parts.length < 2) {
                        writer.println("false");
                        continue;
                    }
                    String u = parts[1];
                    deleteUser(u);
                    // Also delete reservations for this user
                    synchronized (lock) {
                        reservations = readReservations();
                        ArrayList<Reservation> toRemove = new ArrayList<>();
                        for (Reservation r : reservations) {
                            if (r.getUser().getUserName().equals(u)) {
                                toRemove.add(r);
                            }
                        }
                        for (Reservation r : toRemove) {
                            deleteReservation(r);
                        }
                    }
                    writer.println("true");

                } else if (command.equals("IS_BOOKED")) {
                    // IS_BOOKED;key (Date|Time|TableID)
                    if (parts.length < 2) {
                        writer.println("false");
                        continue;
                    }
                    String key = parts[1];
                    boolean booked = false;
                    String[] keyParts = key.split("\\|");
                    if (keyParts.length == 3) {
                        String date = keyParts[0];
                        String timeStr = keyParts[1];
                        String tableStr = keyParts[2];
                        double time = convertTimeToDouble(timeStr);

                        synchronized (lock) {
                            reservations = readReservations();
                            for (Reservation r : reservations) {
                                int rRow = r.getTable().getTableRow();
                                int rCol = r.getTable().getTableColumn();
                                int rTableNum = (rRow - 1) * 3 + rCol;

                                if (r.getDay().equals(date) &&
                                        Math.abs(r.getTime() - time) < 0.01 &&
                                        ("Table " + rTableNum).equals(tableStr)) {
                                    booked = true;
                                    break;
                                }
                            }
                        }
                    }
                    writer.println(booked ? "true" : "false");

                } else if (command.equals("MAKE_RESERVATION")) {
                    // MAKE_RESERVATION;key;username;partySize
                    if (parts.length < 4) {
                        writer.println("false");
                        continue;
                    }
                    String key = parts[1];
                    String username = parts[2];
                    int partySize = Integer.parseInt(parts[3]);

                    String[] keyParts = key.split("\\|");
                    if (keyParts.length == 3) {
                        String date = keyParts[0];
                        String timeStr = keyParts[1];
                        String tableStr = keyParts[2];
                        double time = convertTimeToDouble(timeStr);
                        int tableNum = Integer.parseInt(tableStr.replace("Table ", ""));
                        int row = (tableNum - 1) / 3 + 1;
                        int col = (tableNum - 1) % 3 + 1;

                        User userObj = null;
                        synchronized (lock) {
                            users = readUsers();
                            for (User u : users) {
                                if (u.getUserName().equals(username)) {
                                    userObj = u;
                                    break;
                                }
                            }
                        }

                        if (userObj != null) {
                            boolean booked = false;
                            synchronized (lock) {
                                reservations = readReservations();
                                for (Reservation r : reservations) {
                                    int rRow = r.getTable().getTableRow();
                                    int rCol = r.getTable().getTableColumn();
                                    int rTableNum = (rRow - 1) * 3 + rCol;

                                    if (r.getDay().equals(date) &&
                                            Math.abs(r.getTime() - time) < 0.01 &&
                                            rTableNum == tableNum) {
                                        booked = true;
                                        break;
                                    }
                                }
                            }

                            if (!booked) {
                                Table tableObj = new Table(row, col, 2, 0);
                                createReservation(date, time, userObj, partySize, tableObj);
                                writer.println("true");
                            } else {
                                writer.println("false");
                            }
                        } else {
                            writer.println("false");
                        }
                    } else {
                        writer.println("false");
                    }

                } else if (command.equals("CANCEL_RESERVATION")) {
                    // CANCEL_RESERVATION;prettyString
                    if (parts.length < 2) {
                        writer.println("false");
                        continue;
                    }
                    String pretty = parts[1];
                    String[] pParts = pretty.split(" - ");
                    if (pParts.length == 3) {
                        String date = pParts[0];
                        String timeStr = pParts[1];
                        String tableStr = pParts[2];
                        double time = convertTimeToDouble(timeStr);

                        Reservation toDelete = null;
                        synchronized (lock) {
                            reservations = readReservations();
                            for (Reservation r : reservations) {
                                int rRow = r.getTable().getTableRow();
                                int rCol = r.getTable().getTableColumn();
                                int rTableNum = (rRow - 1) * 3 + rCol;

                                if (r.getDay().equals(date) &&
                                        Math.abs(r.getTime() - time) < 0.01 &&
                                        ("Table " + rTableNum).equals(tableStr)) {
                                    toDelete = r;
                                    break;
                                }
                            }
                        }

                        if (toDelete != null) {
                            deleteReservation(toDelete);
                            writer.println("true");
                        } else {
                            writer.println("false");
                        }
                    } else {
                        writer.println("false");
                    }

                } else if (command.equals("GET_RESERVATIONS")) {
                    // GET_RESERVATIONS
                    StringBuilder sb = new StringBuilder();
                    synchronized (lock) {
                        reservations = readReservations();
                        for (Reservation r : reservations) {
                            if (sb.length() > 0)
                                sb.append(";");
                            sb.append(r.getDay()).append("|")
                                    .append(r.getTime()).append("|")
                                    .append(r.getUser().getUserName()).append("|")
                                    .append(r.getPartySize()).append("|")
                                    .append(r.getTable().getTableRow()).append("|")
                                    .append(r.getTable().getTableColumn());
                        }
                    }
                    writer.println(sb.toString());

                } else if (command.equals("GET_USERS")) {
                    // GET_USERS
                    StringBuilder sb = new StringBuilder();
                    synchronized (lock) {
                        users = readUsers();
                        for (User u : users) {
                            if (sb.length() > 0)
                                sb.append(";");
                            sb.append(u.getUserName()).append(",").append(u.getPassword());
                        }
                    }
                    writer.println(sb.toString());

                } else {
                    writer.println("Invalid command");
                }
            }
        } catch (IOException e) {
            System.out.println("Client disconnected");
        }
    }

    // method creates a new User object given a username and password as input, and
    // adds the new user object to an ArrayList of type User
    public void makeNewUser(String username, String password) {
        synchronized (lock) {
            users = readUsers(); // Reload to get latest data

            // Check for duplicates to prevent race conditions
            for (User u : users) {
                if (u.getUserName().equals(username)) {
                    return; // User already exists, do nothing
                }
            }

            User newUser = new User(username, password);
            users.add(newUser);
            saveUsers();
        }
    }

    // method gets a User object given a username as input from the users ArrayList
    // and removes it from the users ArrayList.
    public void deleteUser(String username) {
        synchronized (lock) {
            users = readUsers(); // Reload
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getUserName().equals(username)) {
                    users.remove(i);
                    break;
                }
            }
            saveUsers();
        }
    }

    // method which creates a new Reservation object given day, time, user,
    // partySize, and table as input and adds the object to the reservations
    // ArrayList
    public void createReservation(String day, double time, User user, int partySize, Table table) {
        synchronized (lock) {
            reservations = readReservations(); // Reload
            Reservation reservation = new Reservation(day, time, user, partySize, table);
            reservations.add(reservation);
            saveReservations();
        }
    }

    // method which deletes a Reservation given day, time, user, partySize, and
    // table as input and removes the Reservation object from the reservations
    // ArrayList
    public void deleteReservation(Reservation reservation) {
        synchronized (lock) {
            reservations = readReservations(); // Reload

            // Find the reservation by value since the object instance might be different
            // after reload
            for (int i = 0; i < reservations.size(); i++) {
                Reservation r = reservations.get(i);
                // Match by Day, Time, and Table (Row/Col)
                if (r.getDay().equals(reservation.getDay()) &&
                        Math.abs(r.getTime() - reservation.getTime()) < 0.001 &&
                        r.getTable().getTableRow() == reservation.getTable().getTableRow() &&
                        r.getTable().getTableColumn() == reservation.getTable().getTableColumn()) {
                    reservations.remove(i);
                    break;
                }
            }
            saveReservations();
        }
    }

    /**
     * saves users to file
     */
    public void saveUsers() {
        synchronized (lock) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(usersFile))) {
                oos.writeObject(this.users);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Reads users from file
     */
    public ArrayList<User> readUsers() {
        synchronized (lock) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(usersFile))) {
                return (ArrayList<User>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                // e.printStackTrace();
            }
            return new ArrayList<User>();
        }
    }

    /**
     * saves reservations to file
     */
    public void saveReservations() {
        synchronized (lock) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(reservationsFile))) {
                oos.writeObject(this.reservations);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Reads reservations from file
     */
    public ArrayList<Reservation> readReservations() {
        synchronized (lock) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(reservationsFile))) {
                return (ArrayList<Reservation>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                // e.printStackTrace();
            }
            return new ArrayList<>();
        }
    }

    /**
     * Gets users
     */
    public ArrayList<User> getUsers() {
        synchronized (lock) {
            users = readUsers(); // Reload
            return users;
        }
    }

    /**
     * Gets reservations
     */
    public ArrayList<Reservation> getReservations() {
        synchronized (lock) {
            reservations = readReservations(); // Reload
            return reservations;
        }
    }

    private double convertTimeToDouble(String timeStr) {
        try {
            String[] parts = timeStr.split(" ");
            String[] hm = parts[0].split(":");
            int h = Integer.parseInt(hm[0]);
            if (parts[1].equals("PM") && h != 12)
                h += 12;
            if (parts[1].equals("AM") && h == 12)
                h = 0;
            return h + (Integer.parseInt(hm[1]) / 60.0);
        } catch (Exception e) {
            return 0.0;
        }
    }

}