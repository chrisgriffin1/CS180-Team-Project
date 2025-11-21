import javax.swing.*;
import java.io.*;
import java.net.*;
import java.io.*;
import java.lang.reflect.Array;
import java.net.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.JOptionPane;
import static java.lang.Integer.parseInt;

public class Client {
    
    public static void main(String[] args) throws UnknownHostException, IOException {


        JOptionPane.showMessageDialog(null, "Welcome to the restaurant!",
                    "Search Engine", JOptionPane.INFORMATION_MESSAGE);

        
        try{            
            
            Socket socket = new Socket("localhost", 47906);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            JOptionPane.showMessageDialog(null, "Connection established!",
                    "Search Engine", JOptionPane.INFORMATION_MESSAGE);
        }catch (UnknownHostException uhe) {

        }        
    }



}
