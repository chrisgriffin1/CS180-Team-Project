import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;

public class Client extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton createButton;
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;

    public Client() {
        super("Restaurant Client - Create User");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        try {
            JOptionPane.showMessageDialog(null, "Welcome to the restaurant!",
                    "Restaurant Client", JOptionPane.INFORMATION_MESSAGE);

            socket = new Socket("localhost", 47906);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);

            JOptionPane.showMessageDialog(null, "Connection established!",
                    "Restaurant Client", JOptionPane.INFORMATION_MESSAGE);

            setupGUI();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Failed to connect to server: " + e.getMessage(),
                    "Connection Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    private void setupGUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setBackground(new Color(40, 44, 52));

        JLabel title = new JLabel("Create New User");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 20));

        JLabel userLabel = new JLabel("Username:");
        userLabel.setForeground(Color.WHITE);
        usernameField = new JTextField(15);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(Color.WHITE);
        passwordField = new JPasswordField(15);

        createButton = new JButton("Create");

        panel.add(title);
        panel.add(userLabel);
        panel.add(usernameField);
        panel.add(passLabel);
        panel.add(passwordField);
        panel.add(createButton);

        add(panel);

        createButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            if (!username.isEmpty() && !password.isEmpty()) {
                String response = sendCommand("New user", username, password);
                if ("Invalid command".equals(response)) {
                    JOptionPane.showMessageDialog(this, "Failed to create user.");
                } else {
                    JOptionPane.showMessageDialog(this, "User created successfully.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please enter username and password.");
            }
        });

        setVisible(true);
    }

    private String sendCommand(String command, String... params) {
        try {
            String cmd = command + ";" + String.join(" ", params);
            writer.println(cmd);
            System.out.println(cmd);
            return reader.readLine();
        } catch (IOException e) {
            return "Error";
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Client::new);
    }
}
