import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server implements Runnable {

    private Database db;

    public static void main(String[] args) {
        new Thread(new Server()).start();
    }

    @Override
    public void run() {
        db = new Database(); 
        try (ServerSocket serverSocket = new ServerSocket(4242)) { 
            System.out.println("Server is listening on port 4242...");
            while (true) {
                new Thread(new ClientHandler(serverSocket.accept(), db)).start();
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    private static class ClientHandler implements Runnable {
        private Socket socket;
        private Database db;

        public ClientHandler(Socket socket, Database db) {
            this.socket = socket;
            this.db = db;
        }

        @Override
        public void run() {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {

                String request = reader.readLine();
                if (request == null) return;
                System.out.println("Rec: " + request);
                
                String[] parts = request.split(";");
                String command = parts[0];

                // SHARED LOGIC for finding a user
                User userObj = null;
                if (parts.length > 5) { // Only look for user if command is long enough
                    for (User u : db.getUsers()) {
                         if (u.getUserName().equals(parts[5])) userObj = u;
                    }
                }

                switch (command) {
                    case "LOGIN": // LOGIN;user;pass
                        boolean valid = false;
                        for (User u : db.getUsers()) {
                            if (u.getUserName().equals(parts[1]) && u.getPassword().equals(parts[2])) valid = true;
                        }
                        writer.println(valid ? "SUCCESS" : "FAIL");
                        break;

                    case "REGISTER": // REGISTER;user;pass
                        db.makeNewUser(parts[1], parts[2]);
                        writer.println("SUCCESS");
                        break;

                    case "BOOK":
                        // PROTOCOL: BOOK;Day;Time;ROW;COL;Username;Party
                        try {
                            String day = parts[1];
                            double time = Double.parseDouble(parts[2]); // Client will send "17.0"
                            int row = Integer.parseInt(parts[3]);
                            int col = Integer.parseInt(parts[4]);
                            int party = Integer.parseInt(parts[6]);

                            // 1. Create the Table Object using YOUR Constructor
                            // We assume capacity 2 and price 10 since GUI doesn't set them yet
                            Table tableObj = new Table(row, col, 2, 10); 

                            // 2. Check Conflicts using Row/Col
                            boolean conflict = false;
                            for (Reservation r : db.getReservations()) {
                                if (r.getDay().equals(day) && r.getTime() == time) {
                                    // Check coordinates
                                    if (r.getTable().getTableRow() == row && 
                                        r.getTable().getTableColumn() == col) {
                                        conflict = true;
                                    }
                                }
                            }

                            if (conflict || userObj == null) {
                                writer.println("FAIL");
                            } else {
                                db.createReservation(day, time, userObj, party, tableObj);
                                writer.println("SUCCESS");
                            }
                        } catch (Exception e) { e.printStackTrace(); writer.println("ERROR"); }
                        break;

                    case "CANCEL":
                        // CANCEL;Day;Time;ROW;COL
                        String cDay = parts[1];
                        double cTime = Double.parseDouble(parts[2]);
                        int cRow = Integer.parseInt(parts[3]);
                        int cCol = Integer.parseInt(parts[4]);

                        Reservation toRemove = null;
                        for (Reservation r : db.getReservations()) {
                            if (r.getDay().equals(cDay) && r.getTime() == cTime &&
                                r.getTable().getTableRow() == cRow && 
                                r.getTable().getTableColumn() == cCol) {
                                toRemove = r;
                                break;
                            }
                        }
                        if (toRemove != null) {
                            db.deleteReservation(toRemove);
                            writer.println("SUCCESS");
                        } else {
                            writer.println("FAIL");
                        }
                        break;

                    case "GET_MAP":
                        // GET_MAP;Day;Time
                        // Returns: "0,0|0,1|2,2|" (List of Row,Col pairs that are taken)
                        String mDay = parts[1];
                        double mTime = Double.parseDouble(parts[2]);
                        StringBuilder taken = new StringBuilder();
                        for (Reservation r : db.getReservations()) {
                            if (r.getDay().equals(mDay) && r.getTime() == mTime) {
                                taken.append(r.getTable().getTableRow())
                                     .append(",")
                                     .append(r.getTable().getTableColumn())
                                     .append("|");
                            }
                        }
                        writer.println(taken.toString());
                        break;
                        
                    case "GET_USER_RES":
                        // Returns: "Fri...;17.0;0;0,Sat...;18.0;1;1"
                        String uName = parts[1];
                        StringBuilder sb = new StringBuilder();
                        for (Reservation r : db.getReservations()) {
                            if (r.getUser().getUserName().equals(uName)) {
                                sb.append(r.getDay()).append(";")
                                  .append(r.getTime()).append(";")
                                  .append(r.getTable().getTableRow()).append(";")
                                  .append(r.getTable().getTableColumn()).append(",");
                            }
                        }
                        writer.println(sb.toString());
                        break;
                }
            } catch (IOException e) { e.printStackTrace(); }
        }
    }
}