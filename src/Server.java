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
            
        }

        System.out.println("Server is listening on port 47906");
    
        
        while (true) {
            Socket socket = null;
            try {
                socket = serverSocket.accept();
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {
                    String command = reader.readLine();
                    
                    // new user command are in the format "new user;username;password"

                    // delete user command is in the format "delete user;username"



                    // new reservation command are in the format 
                    // "new reservation;day time username partySize tableRow tableColumn"

                    // delete reservation command are in the format



                    if (command.contains("new user")) {
                        String[] data = command.split(";");

                        ArrayList<User> validUsers = db.getUsers();

                        db.makeNewUser(data[1], data[2]);
                    } else if (command.contains("delete user")) {
                        //db.deleteUser();
                    } else if (command.contains("new reservation")) {
                        // db.createReservation();
                    } else if (command.contains("delete reservation")) {
                        // db.deleteReservation();
                    } else {
                        writer.println("Invalid command");
                    }

                    

                } catch (IOException e) {
                    System.out.println("Client error");
                } finally {
                    if (socket != null) {
                        socket.close();
                    }
                }

            } catch (Exception e) {
                
            }
        }
    }

        







    }
