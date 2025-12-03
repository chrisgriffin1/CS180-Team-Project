import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles all networking logic.
 * Sends String commands to the Server and parses the String responses.
 */
public class Client {
    private String serverAddress = "localhost";
    private int port = 4242;

    // --- CORE NETWORKING METHOD ---
    private String sendRequest(String request) {
        try (Socket socket = new Socket(serverAddress, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            
            out.println(request);
            return in.readLine();
            
        } catch (IOException e) {
            // e.printStackTrace(); // Uncomment for debugging if needed
            return "ERROR";
        }
    }

    // --- USER MANAGEMENT ---
    
    public boolean validateUser(String u, String p) {
        return sendRequest("LOGIN;" + u + ";" + p).equals("SUCCESS");
    }

    public boolean addUser(String u, String p) {
        return sendRequest("REGISTER;" + u + ";" + p).equals("SUCCESS");
    }

    public void deleteUser(String u) {
        sendRequest("DELETE_USER;" + u);
    }

    // --- HELPER: CONVERTS TABLE NAME TO ROW/COL ---
    private int[] getCoords(String tableName) {
        // Example: "Table 5" -> 5 -> Row 1, Col 1
        int num = Integer.parseInt(tableName.replace("Table ", ""));
        int row = (num - 1) / 3;
        int col = (num - 1) % 3;
        return new int[]{row, col};
    }
    
    // --- HELPER: TIME CONVERSION ---
    private double parseTime(String time) {
        if (time.startsWith("5")) return 17.0;
        if (time.startsWith("6")) return 18.0;
        if (time.startsWith("7")) return 19.0;
        if (time.startsWith("8")) return 20.0;
        if (time.startsWith("9")) return 21.0;
        return 22.0; 
    }
    
    private String formatTime(double time) {
        if (time == 17.0) return "5:00 PM";
        if (time == 18.0) return "6:00 PM";
        if (time == 19.0) return "7:00 PM";
        if (time == 20.0) return "8:00 PM";
        if (time == 21.0) return "9:00 PM";
        return "10:00 PM";
    }

    // --- RESERVATION ACTIONS ---

    public boolean makeReservation(String day, String timeStr, String tableName, String user, String partySize) {
        int[] coords = getCoords(tableName);
        double timeDbl = parseTime(timeStr);
        String sizeInt = partySize.replaceAll("[^0-9]", ""); 
        if(sizeInt.isEmpty()) sizeInt = "2";

        // Protocol: BOOK;Day;TimeDouble;Row;Col;User;Party
        String cmd = "BOOK;" + day + ";" + timeDbl + ";" + coords[0] + ";" + coords[1] + ";" + user + ";" + sizeInt;
        return sendRequest(cmd).equals("SUCCESS");
    }

    public List<String> getTakenTables(String day, String timeStr) {
        double timeDbl = parseTime(timeStr);
        String response = sendRequest("GET_MAP;" + day + ";" + timeDbl);
        
        List<String> takenNames = new ArrayList<>();
        if (response != null && !response.isEmpty()) {
            String[] pairs = response.split("\\|"); // Split "0,0|0,1|"
            for (String pair : pairs) {
                if(pair.contains(",")) {
                    String[] rc = pair.split(",");
                    int r = Integer.parseInt(rc[0]);
                    int c = Integer.parseInt(rc[1]);
                    // Convert row/col back to "Table X"
                    int tableNum = (r * 3) + c + 1; 
                    takenNames.add("Table " + tableNum);
                }
            }
        }
        return takenNames;
    }

    public List<String> getUserReservations(String user) {
        // Server response format: "Fri, Nov 15;17.0;0;0,"
        String response = sendRequest("GET_USER_RES;" + user);
        List<String> prettyList = new ArrayList<>();
        
        if (response != null && !response.isEmpty()) {
            String[] rawRes = response.split(",");
            for (String s : rawRes) {
                String[] parts = s.split(";");
                if(parts.length >= 4) {
                    String d = parts[0];
                    double t = Double.parseDouble(parts[1]);
                    int r = Integer.parseInt(parts[2]);
                    int c = Integer.parseInt(parts[3]);
                    
                    int tNum = (r * 3) + c + 1;
                    String tStr = formatTime(t);
                    
                    // FORMAT: "Fri, Nov 15 | 5:00 PM | Table 1"
                    prettyList.add(d + " | " + tStr + " | Table " + tNum);
                }
            }
        }
        return prettyList;
    }
    
    public void cancelReservation(String prettyString) {
        // Input: "Fri, Nov 15 | 5:00 PM | Table 1"
        try {
            String[] parts = prettyString.split(" \\| "); // Split by " | "
            if (parts.length == 3) {
                String day = parts[0];
                String timeStr = parts[1];
                String tableStr = parts[2];
                
                double timeDbl = parseTime(timeStr);
                int[] coords = getCoords(tableStr);
                
                // Protocol: CANCEL;Day;Time;ROW;COL
                String cmd = "CANCEL;" + day + ";" + timeDbl + ";" + coords[0] + ";" + coords[1];
                sendRequest(cmd);
            }
        } catch (Exception e) {
            System.out.println("Error parsing cancellation string: " + e.getMessage());
        }
    }
}