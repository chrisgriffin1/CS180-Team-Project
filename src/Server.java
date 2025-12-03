import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.net.Socket;
import javax.swing.JOptionPane;

public class Server implements Runnable {

    public static void main(String[] args) {
        Thread serverThread = new Thread(new Server());
        serverThread.start();
    }

    public void run() {
        Database db = new Database();
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
                new Thread(() -> handleClient(socket, db)).start();
            } catch (IOException e) {
                System.out.println("Accept failed: 47906");
            }
        }
    }

    private void handleClient(Socket socket, Database db) {
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
                    for (User user : db.getUsers()) {
                        if (user.getUserName().equals(u) && user.getPassword().equals(p)) {
                            valid = true;
                            break;
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
                    for (User user : db.getUsers()) {
                        if (user.getUserName().equals(u)) {
                            exists = true;
                            break;
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
                    db.makeNewUser(u, p);
                    writer.println("true");

                } else if (command.equals("DELETE_USER")) {
                    // DELETE_USER;username
                    if (parts.length < 2) {
                        writer.println("false");
                        continue;
                    }
                    String u = parts[1];
                    db.deleteUser(u);
                    // Also delete reservations for this user
                    ArrayList<Reservation> toRemove = new ArrayList<>();
                    for (Reservation r : db.getReservations()) {
                        if (r.getUser().getUserName().equals(u)) {
                            toRemove.add(r);
                        }
                    }
                    for (Reservation r : toRemove) {
                        db.deleteReservation(r);
                    }
                    writer.println("true");

                } else if (command.equals("IS_BOOKED")) {
                    // IS_BOOKED;key (Date|Time|TableID)
                    if (parts.length < 2) {
                        writer.println("false");
                        continue;
                    }
                    String key = parts[1];
                    // Parse key: "Date|Time|TableID"
                    // Example: "Fri, Nov 15|6:00 PM|Table 1"
                    // But Reservation object stores: day, time (double), user, partySize, table
                    // We need to match this.
                    // Let's assume we store the "key" or reconstruct it.
                    // For simplicity, let's check if any reservation matches the description.

                    boolean booked = false;
                    String[] keyParts = key.split("\\|");
                    if (keyParts.length == 3) {
                        String date = keyParts[0];
                        String timeStr = keyParts[1]; // "6:00 PM"
                        String tableStr = keyParts[2]; // "Table 1"

                        // Convert timeStr to double if needed
                        double time = convertTimeToDouble(timeStr);

                        for (Reservation r : db.getReservations()) {
                            // Calculate table number from row/col
                            // Assuming 3 columns grid
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

                        // Map tableNum to Row/Col
                        // Table 1 -> 1, 1
                        // Table 2 -> 1, 2
                        // Table 3 -> 1, 3
                        // Table 4 -> 2, 1
                        int row = (tableNum - 1) / 3 + 1;
                        int col = (tableNum - 1) % 3 + 1;

                        // Find user object
                        User userObj = null;
                        for (User u : db.getUsers()) {
                            if (u.getUserName().equals(username)) {
                                userObj = u;
                                break;
                            }
                        }

                        if (userObj != null) {
                            // Check if already booked (double check)
                            boolean booked = false;
                            for (Reservation r : db.getReservations()) {
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

                            if (!booked) {
                                // Table(int tableRow, int tableColumn, int capacity, int price)
                                Table tableObj = new Table(row, col, 2, 0); // Capacity 2, Price 0 for now
                                db.createReservation(date, time, userObj, partySize, tableObj);
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
                    // prettyString: "Fri, Nov 15 - 6:00 PM - Table 1"
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
                        for (Reservation r : db.getReservations()) {
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

                        if (toDelete != null) {
                            db.deleteReservation(toDelete);
                            writer.println("true");
                        } else {
                            writer.println("false");
                        }
                    } else {
                        writer.println("false");
                    }

                } else if (command.equals("GET_RESERVATIONS")) {
                    // GET_RESERVATIONS;username
                    if (parts.length < 2) {
                        writer.println("");
                        continue;
                    }
                    String u = parts[1];
                    StringBuilder sb = new StringBuilder();
                    for (Reservation r : db.getReservations()) {
                        if (r.getUser().getUserName().equals(u)) {
                            // Format: "Fri, Nov 15 - 6:00 PM - Table 1"
                            int rRow = r.getTable().getTableRow();
                            int rCol = r.getTable().getTableColumn();
                            int rTableNum = (rRow - 1) * 3 + rCol;

                            String timeStr = convertDoubleToTime(r.getTime());
                            String s = r.getDay() + " - " + timeStr + " - Table " + rTableNum;
                            if (sb.length() > 0)
                                sb.append(";");
                            sb.append(s);
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

    private double convertTimeToDouble(String timeStr) {
        // "6:00 PM" -> 18.0
        // "10:00 PM" -> 22.0
        try {
            String[] parts = timeStr.split(" "); // ["6:00", "PM"]
            String[] hm = parts[0].split(":"); // ["6", "00"]
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

    private String convertDoubleToTime(double time) {
        int h = (int) time;
        int m = (int) Math.round((time - h) * 60);
        String suffix = "AM";
        if (h >= 12) {
            suffix = "PM";
            if (h > 12)
                h -= 12;
        }
        if (h == 0)
            h = 12;
        return String.format("%d:%02d %s", h, m, suffix);
    }

}
