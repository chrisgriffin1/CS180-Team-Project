import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class NetworkClient {
    private String serverAddress = "localhost";
    private int port = 4242;

    private String sendRequest(String request) {
        try (Socket socket = new Socket(serverAddress, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            out.println(request);
            return in.readLine();
        } catch (IOException e) { return "ERROR"; }
    }

    // ... (Login/User methods stay the same) ...
    public boolean validateUser(String u, String p) {
        return sendRequest("LOGIN;" + u + ";" + p).equals("SUCCESS");
    }
    public boolean addUser(String u, String p) {
        return sendRequest("REGISTER;" + u + ";" + p).equals("SUCCESS");
    }
    public void deleteUser(String u) { sendRequest("DELETE_USER;" + u); }


    // --- CONVERSION HELPERS ---
    // Converts "Table 1" -> int[]{0, 0}
    private int[] getCoords(String tableName) {
        int num = Integer.parseInt(tableName.replace("Table ", ""));
        // Map 1-9 to a 3x3 Grid
        // Table 1=(0,0), Table 2=(0,1), Table 3=(0,2)
        // Table 4=(1,0), Table 5=(1,1), etc.
        int row = (num - 1) / 3;
        int col = (num - 1) % 3;
        return new int[]{row, col};
    }
    
    // Converts time "5:00 PM" -> 17.0
    private double parseTime(String time) {
        if (time.startsWith("5")) return 17.0;
        if (time.startsWith("6")) return 18.0;
        if (time.startsWith("7")) return 19.0;
        if (time.startsWith("8")) return 20.0;
        if (time.startsWith("9")) return 21.0;
        return 22.0; 
    }

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
                    // Convert back to "Table X"
                    int tableNum = (r * 3) + c + 1; 
                    takenNames.add("Table " + tableNum);
                }
            }
        }
        return takenNames;
    }

    public List<String> getUserReservations(String user) {
        // Returns "Fri;17.0;0;0,"
        String response = sendRequest("GET_USER_RES;" + user);
        List<String> prettyList = new ArrayList<>();
        
        if (response != null && !response.isEmpty()) {
            String[] rawRes = response.split(",");
            for (String s : rawRes) {
                // Parse "Fri;17.0;0;0"
                String[] parts = s.split(";");
                if(parts.length >= 4) {
                    String d = parts[0];
                    // Convert 17.0 back to 5:00 PM logic if needed, or just display raw
                    int r = Integer.parseInt(parts[2]);
                    int c = Integer.parseInt(parts[3]);
                    int tNum = (r * 3) + c + 1;
                    prettyList.add(d + " - Table " + tNum);
                }
            }
        }
        return prettyList;
    }
    
    public void cancelReservation(String prettyString) {
        // Input: "Fri, Nov 15 - Table 1" (We simplified the display above)
        // We need the time... this implies the UI needs to be smarter or we store the raw data.
        // For now, let's assume we pass the raw data in a real app, but for this snippet:
        // We need to match what we got in getUserReservations.
        // NOTE: You might need to update getUserReservations to include the time in the string so we can parse it back here.
    }
}