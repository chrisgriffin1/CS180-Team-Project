import java.io.*;
import java.net.*;

public Server implements Runnable {
    
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(47906);
        System.out.println("Server is listening on port 47906");
        Socket socket = serverSocket.accept();
        System.out.println("Client connected");
    
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

        







    }

}
