import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.*;
import java.awt.*;

// Phase Two 
//

public class GUI {

    JFrame frame = new JFrame();
    JPanel panel = new JPanel();

    public GUI() {
        panel.setBorder(BorderFactory.createEmptyBorder(30,30,10,10));
        panel.setLayout(new GridLayout());

        frame.add(panel,BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Restaurant");
        frame.pack();
        frame.setSize(400, 400);
        frame.setVisible(true);
        panel.setLayout(null);

        JLabel label = new JLabel("Welcome to Restaurant");
    }


    public static void main(String[] args) {
        new GUI();
    }
}
