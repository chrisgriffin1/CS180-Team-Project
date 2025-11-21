import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.net.Socket;
import javax.swing.JOptionPane;

public class Server implements Runnable {
    
    public void run() {
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
                    
                    String clientMessage = reader.readLine();

                    

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
