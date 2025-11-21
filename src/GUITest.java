import javax.swing.*;
import java.awt.*;

public class GUITest extends JFrame {

    // 1. The "Deck" that holds all screens
    private JPanel mainDeck;
    private CardLayout cardLayout;
    private JPanel logInPanel;

    // 2. Shared Data (Session State)
    private String currentUser = "";

    // 3. Mock Database (for testing logic)
    private MockServer localServer = new MockServer();

    public GUITest() {
        super("Reservation App - Multi-Screen Demo");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center on screen

        // Initialize the CardLayout (The Manager)
        cardLayout = new CardLayout();
        mainDeck = new JPanel(cardLayout);

        // --- CREATE THE SCREENS ---
        // We build them as separate Panels and add them to the "Deck"

        JPanel loginScreen = createLoginScreen();
        JPanel dashboardScreen = createDashboardScreen();

        // Add them to the deck with a specific "Name" (ID)
        mainDeck.add(loginScreen, "LOGIN");
        mainDeck.add(dashboardScreen, "DASHBOARD");

        // Add the deck to the Frame
        add(mainDeck);

        // Show the first screen
        cardLayout.show(mainDeck, "LOGIN");

        setVisible(true);
    }

    // ==========================================
    // SCREEN 1: LOGIN
    // ==========================================
    private JPanel createLoginScreen() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout()); // Centers everything
        panel.setBackground(new Color(40, 44, 52)); // Dark Grey

        // Components
        JLabel title = new JLabel("Welcome to Reserve-A-Spot");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 24));

        JTextField userField = new JTextField(15);
        JPasswordField passField = new JPasswordField(15);
        JButton loginBtn = new JButton("bob In");

        // Layout Logic (GridBag constraints)
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(title, gbc);

        gbc.gridy = 1;
        panel.add(new JLabel("<html><font color='white'>Username:</font></html>"), gbc);

        gbc.gridy = 2;
        panel.add(userField, gbc);

        gbc.gridy = 3;
        panel.add(new JLabel("<html><font color='white'>Password:</font></html>"), gbc);

        gbc.gridy = 4;
        panel.add(passField, gbc);

        gbc.gridy = 5;
        panel.add(loginBtn, gbc);

        // --- LOGIN LOGIC ---
        loginBtn.addActionListener(e -> {
            String user = userField.getText();
            if (!user.isEmpty()) {
                // 1. Save the user to our "Session"
                this.currentUser = user;

                // 2. Clear the password field for security
                passField.setText("");

                // 3. FLIP THE CARD to the dashboard
                cardLayout.show(mainDeck, "DASHBOARD");
                System.out.println("Switched to Dashboard for: " + currentUser);
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a username.");
            }
        });

        return panel;
    }

    // ==========================================
    // SCREEN 2: DASHBOARD (The Tables)
    // ==========================================
    private JPanel createDashboardScreen() {
        JPanel panel = new JPanel(new BorderLayout());

        // -- Top Bar --
        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topBar.setBackground(Color.LIGHT_GRAY);
        JButton logoutBtn = new JButton("Log Out");
        topBar.add(new JLabel("Reservation Dashboard"));
        topBar.add(logoutBtn);
        panel.add(topBar, BorderLayout.NORTH);

        // -- Center: Table Grid --
        JPanel tableGrid = new JPanel(new GridLayout(3, 3, 10, 10));
        tableGrid.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        for (int i = 1; i <= 9; i++) {
            String tableName = "Table " + i;
            JButton tblBtn = new JButton(tableName);
            tblBtn.setBackground(new Color(144, 238, 144)); // Light Green

            tblBtn.addActionListener(e -> {
                // Use the shared 'currentUser' variable
                boolean booked = localServer.reserveTable(tableName, currentUser);
                if (booked) {
                    tblBtn.setBackground(new Color(255, 99, 71)); // Tomato Red
                    tblBtn.setText("Booked by " + currentUser);
                } else {
                    JOptionPane.showMessageDialog(this, "Table already taken!");
                }
            });
            tableGrid.add(tblBtn);
        }
        panel.add(tableGrid, BorderLayout.CENTER);

        // --- LOGOUT LOGIC ---
        logoutBtn.addActionListener(e -> {
            // 1. Clear session
            currentUser = "";

            // 2. FLIP CARD back to Login
            cardLayout.show(mainDeck, "LOGIN");
        });

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GUITest::new);
    }

    // Mock Database (Same as before)
    class MockServer {
        private java.util.Map<String, String> database = new java.util.HashMap<>();
        public boolean reserveTable(String table, String user) {
            if (database.containsKey(table)) return false;
            database.put(table, user);
            return true;
        }
    }
}