import javax.swing.*;
import java.io.*;
import java.net.*;
import java.lang.reflect.Array;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.JOptionPane;
    
import static java.lang.Integer.parseInt;

public class Client extends JComponent implements Runnable {
    private Image image;
    private int curX;
    private int curY;
    private int oldX;
    private int oldY;
    Graphics2D graphics2D;
    private JButton logInButton;
    private JButton createNewUserButton;
    private Color penColor = Color.black;
    Paint paint;

    
    public static void main(String[] args) throws UnknownHostException, IOException {

        try {   
            JOptionPane.showMessageDialog(null, "Welcome to the restaurant!",
                    "Search Engine", JOptionPane.INFORMATION_MESSAGE);

            Scanner scanner = new Scanner(System.in);
            Socket socket = new Socket("localhost", 47906);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            JOptionPane.showMessageDialog(null, "Connection established!",
                    "Search Engine", JOptionPane.INFORMATION_MESSAGE);
        
            
                
                

        public void run() {
            JFrame frame = new JFrame("Paint"); //change if needed.
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 700);
            frame.setLocationRelativeTo(null);
            Container content = frame.getContentPane();
            content.setLayout(new BorderLayout());
            logInButton = new JButton("Log In");
            createNewUserButton = new JButton("Create New User");
            clearButton.addActionListener(actionListener);
            fillButton.addActionListener(actionListener);
            

        }

        /* public static void main(String[] args) {
            SwingUtilities.invokeLater();
        } */    
        

        ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == logInButton) {
                
            } 
            
            if (e.getSource() == createNewUserButton) {

            } 






        }
        }        

        } catch (UnknownHostException uhe) {
            uhe.printStackTrace();
        }
        
        


    }



}
