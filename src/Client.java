import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;

public class Client extends JFrame implements ClientGuide {

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
            int existingAccountQ = -122;
            JOptionPane.showMessageDialog(null, "Welcome to the restaurant!",
                    "Restaurant Client", JOptionPane.INFORMATION_MESSAGE);

            socket = new Socket("localhost", 47906);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);

            JOptionPane.showMessageDialog(null, "Connection established!",
                    "Restaurant Client", JOptionPane.INFORMATION_MESSAGE);


            existingAccountQ = JOptionPane.showConfirmDialog(null, "Do you have an existing account?", "Restaurant Client", JOptionPane.YES_NO_OPTION);
            if (existingAccountQ == JOptionPane.YES_OPTION) {
                loginGUI();
            } else {
                setupGUI(); 
            }

            

            

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Failed to connect to server: " + e.getMessage(),
                    "Connection Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    public void loginGUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setBackground(new Color(40, 44, 52));

        JLabel title = new JLabel("Log In");
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
                    createReservationGUI();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please enter username and password.");
            }

            dispose();
        });

        setVisible(true);
    }


    public void setupGUI() {
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
                    createReservationGUI();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please enter username and password.");
            }
            dispose();
        });

        setVisible(true);
    }

    public String sendCommand(String command, String... params) {
        try {
            String cmd = command + ";" + String.join(" ", params);
            writer.println(cmd);
            System.out.println(cmd);
            return reader.readLine();
        } catch (IOException e) {
            return "Error";
        }
    }

    public void createReservationGUI() {
        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
            inputPanel.add(new JLabel("Date:"));
            JComboBox<String> dateCombo = new JComboBox<>(new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"});
            inputPanel.add(dateCombo);
            inputPanel.add(new JLabel("Time:"));
            JComboBox<String> timeCombo = new JComboBox<>(new String[]{"18:00", "19:00", "20:00", "21:00"});
            inputPanel.add(timeCombo);
            inputPanel.add(new JLabel("Party Size:"));
            JComboBox<Integer> partyCombo = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8});
            inputPanel.add(partyCombo);
            int result = JOptionPane.showConfirmDialog(null, inputPanel, "Enter Reservation Details", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                String selectedDate = (String) dateCombo.getSelectedItem();
                String selectedTime = (String) timeCombo.getSelectedItem();
                int selectedParty = (Integer) partyCombo.getSelectedItem();
                // TODO: Use these values, e.g., send to server for reservation
                JOptionPane.showMessageDialog(null, "Reservation for " + selectedDate + " at " + selectedTime + " for " + selectedParty + " people.");
            }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Client::new);
    }
}
