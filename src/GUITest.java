import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GUITest extends JFrame {

    // --- THEME COLORS (Italian Flag) ---
    private final Color ITALIAN_GREEN = new Color(0, 140, 69);
    private final Color ITALIAN_RED = new Color(205, 33, 42);
    private final Color OFF_WHITE = new Color(236, 204, 162);

    // --- NAVIGATION ---
    private JPanel mainDeck;
    private CardLayout cardLayout;

    // --- STATE & DATA ---
    private String currentUser = null;
    private MockDatabase db = new MockDatabase();

    public GUITest() {
        super("Aiden's Pizzeria - Reservation System");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Setup CardLayout
        cardLayout = new CardLayout();
        mainDeck = new JPanel(cardLayout);

        // --- BUILD SCREENS ---
        mainDeck.add(createWelcomeScreen(), "WELCOME");
        mainDeck.add(createLoginScreen(), "LOGIN");
        mainDeck.add(createCreateAccountScreen(), "CREATE_ACCOUNT");
        mainDeck.add(createDashboardScreen(), "DASHBOARD");
        mainDeck.add(createReservationScreen(), "MAKE_RESERVATION");

        add(mainDeck);
        setVisible(true);
    }

    // ==========================================
    // 1. WELCOME SCREEN
    // ==========================================
    private JPanel createWelcomeScreen() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(OFF_WHITE);

        JLabel title = new JLabel("Aiden's Pizzeria");
        title.setFont(new Font("Serif", Font.BOLD, 48));
        title.setForeground(ITALIAN_RED);

        JLabel subtitle = new JLabel("Authentic Italian Dining");
        subtitle.setFont(new Font("SansSerif", Font.ITALIC, 18));
        subtitle.setForeground(ITALIAN_GREEN);

        JButton loginBtn = createStyledButton("Login", ITALIAN_GREEN);
        JButton createBtn = createStyledButton("Create an Account", ITALIAN_RED);

        // Layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(title, gbc);

        gbc.gridy = 1;
        panel.add(subtitle, gbc);

        gbc.gridy = 2; gbc.insets = new Insets(40, 10, 10, 10);
        panel.add(loginBtn, gbc);

        gbc.gridy = 3; gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(createBtn, gbc);

        // Actions
        loginBtn.addActionListener(e -> cardLayout.show(mainDeck, "LOGIN"));
        createBtn.addActionListener(e -> cardLayout.show(mainDeck, "CREATE_ACCOUNT"));

        return panel;
    }

    // ==========================================
    // 2. LOGIN SCREEN
    // ==========================================
    private JPanel createLoginScreen() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(OFF_WHITE);

        JLabel header = new JLabel("Benvenuto! Please Login.");
        header.setFont(new Font("Serif", Font.BOLD, 24));

        JTextField userField = new JTextField(15);
        JPasswordField passField = new JPasswordField(15);
        JButton submitBtn = createStyledButton("Enter", ITALIAN_GREEN);
        JButton backBtn = createStyledButton("Back", Color.GRAY);

        // Layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(header, gbc);

        gbc.gridy = 1; panel.add(new JLabel("Username:"), gbc);
        gbc.gridy = 2; panel.add(userField, gbc);
        gbc.gridy = 3; panel.add(new JLabel("Password:"), gbc);
        gbc.gridy = 4; panel.add(passField, gbc);
        gbc.gridy = 5; panel.add(submitBtn, gbc);
        gbc.gridy = 6; panel.add(backBtn, gbc);

        // Actions
        submitBtn.addActionListener(e -> {
            String u = userField.getText();
            String p = new String(passField.getPassword());
            if (db.validateUser(u, p)) {
                currentUser = u;
                passField.setText(""); // Clear password
                refreshDashboard(); // Update dashboard with user name
                cardLayout.show(mainDeck, "DASHBOARD");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Username or Password!");
            }
        });

        backBtn.addActionListener(e -> cardLayout.show(mainDeck, "WELCOME"));

        return panel;
    }

    // ==========================================
    // 3. CREATE ACCOUNT SCREEN
    // ==========================================
    private JPanel createCreateAccountScreen() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(OFF_WHITE);

        JLabel header = new JLabel("Join the Family");
        header.setFont(new Font("Serif", Font.BOLD, 24));
        header.setForeground(ITALIAN_RED);

        JTextField userField = new JTextField(15);
        JPasswordField passField = new JPasswordField(15);
        JButton createBtn = createStyledButton("Create Account", ITALIAN_RED);
        JButton backBtn = createStyledButton("Back", Color.GRAY);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(header, gbc);

        gbc.gridy = 1; panel.add(new JLabel("New Username:"), gbc);
        gbc.gridy = 2; panel.add(userField, gbc);
        gbc.gridy = 3; panel.add(new JLabel("New Password:"), gbc);
        gbc.gridy = 4; panel.add(passField, gbc);
        gbc.gridy = 5; panel.add(createBtn, gbc);
        gbc.gridy = 6; panel.add(backBtn, gbc);

        // Actions
        createBtn.addActionListener(e -> {
            String u = userField.getText();
            String p = new String(passField.getPassword());

            if (u.isEmpty() || p.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Fields cannot be empty.");
                return;
            }

            if (db.userExists(u)) {
                JOptionPane.showMessageDialog(this, "Username taken! Try another.");
            } else {
                db.addUser(u, p);
                JOptionPane.showMessageDialog(this, "Account Created! Please Login.");
                userField.setText("");
                passField.setText("");
                cardLayout.show(mainDeck, "LOGIN");
            }
        });

        backBtn.addActionListener(e -> cardLayout.show(mainDeck, "WELCOME"));

        return panel;
    }

    // ==========================================
    // 4. DASHBOARD (The 3 Options)
    // ==========================================
    private JPanel dashboardPanel; // Keep reference to update text
    private JLabel welcomeLabel;

    private JPanel createDashboardScreen() {
        dashboardPanel = new JPanel(new GridBagLayout());
        dashboardPanel.setBackground(OFF_WHITE);

        welcomeLabel = new JLabel("Ciao, User!");
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 30));

        JButton makeResBtn = createStyledButton("Make Reservation", ITALIAN_GREEN);
        JButton cancelResBtn = createStyledButton("Cancel Reservation", Color.ORANGE);
        JButton deleteAccBtn = createStyledButton("Delete Account", ITALIAN_RED);
        JButton logoutBtn = createStyledButton("Logout", Color.BLACK);

        // Bigger Buttons for the Dashboard
        Dimension bigBtnSize = new Dimension(250, 50);
        makeResBtn.setPreferredSize(bigBtnSize);
        cancelResBtn.setPreferredSize(bigBtnSize);
        deleteAccBtn.setPreferredSize(bigBtnSize);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.gridx = 0; gbc.gridy = 0;
        dashboardPanel.add(welcomeLabel, gbc);

        gbc.gridy = 1; dashboardPanel.add(makeResBtn, gbc);
        gbc.gridy = 2; dashboardPanel.add(cancelResBtn, gbc);
        gbc.gridy = 3; dashboardPanel.add(deleteAccBtn, gbc);
        gbc.gridy = 4; dashboardPanel.add(logoutBtn, gbc);

        // --- DASHBOARD ACTIONS ---

        // 1. Make Reservation -> Go to Table Map
        makeResBtn.addActionListener(e -> cardLayout.show(mainDeck, "MAKE_RESERVATION"));

        // 2. Cancel Reservation -> Pop up a list of user's tables
        cancelResBtn.addActionListener(e -> handleCancellation());

        // 3. Delete Account
        deleteAccBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to leave the family?",
                    "Delete Account", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                db.deleteUser(currentUser);
                currentUser = null;
                cardLayout.show(mainDeck, "WELCOME");
                JOptionPane.showMessageDialog(this, "Account Deleted.");
            }
        });

        // 4. Logout
        logoutBtn.addActionListener(e -> {
            currentUser = null;
            cardLayout.show(mainDeck, "WELCOME");
        });

        return dashboardPanel;
    }

    // Helper to update the welcome text when user logs in
    private void refreshDashboard() {
        welcomeLabel.setText("Ciao, " + currentUser + "!");
    }

    private void handleCancellation() {
        // Get list of tables booked by current user
        List<String> myTables = db.getUserReservations(currentUser);

        if (myTables.isEmpty()) {
            JOptionPane.showMessageDialog(this, "You have no reservations to cancel.");
            return;
        }

        // Show simple dialog to pick one
        String[] choices = myTables.toArray(new String[0]);
        String input = (String) JOptionPane.showInputDialog(this,
                "Which reservation would you like to cancel?",
                "Cancel Reservation",
                JOptionPane.QUESTION_MESSAGE, null,
                choices, choices[0]);

        if (input != null) {
            db.cancelReservation(input);
            JOptionPane.showMessageDialog(this, "Reservation for " + input + " cancelled.");
        }
    }

    // ==========================================
    // 5. MAKE RESERVATION (Table Map)
    // ==========================================
    private JPanel createReservationScreen() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel header = new JLabel("Select Your Table", SwingConstants.CENTER);
        header.setFont(new Font("Serif", Font.BOLD, 24));
        header.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panel.add(header, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(3, 3, 10, 10));
        grid.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create 9 tables
        for (int i = 1; i <= 9; i++) {
            String tableName = "Table " + i;
            JButton tblBtn = new JButton(tableName);
            tblBtn.setBackground(ITALIAN_GREEN);
            tblBtn.setForeground(Color.WHITE);
            tblBtn.setOpaque(true);
            tblBtn.setBorderPainted(false);

            tblBtn.addActionListener(e -> {
                boolean success = db.makeReservation(tableName, currentUser);
                if (success) {
                    tblBtn.setBackground(ITALIAN_RED);
                    tblBtn.setText("Booked!");
                    JOptionPane.showMessageDialog(this, "You booked " + tableName);
                } else {
                    JOptionPane.showMessageDialog(this, "Already booked!");
                }
            });
            grid.add(tblBtn);
        }

        panel.add(grid, BorderLayout.CENTER);

        JButton backBtn = createStyledButton("Back to Dashboard", Color.BLACK);
        backBtn.addActionListener(e -> cardLayout.show(mainDeck, "DASHBOARD"));
        panel.add(backBtn, BorderLayout.SOUTH);

        return panel;
    }

    // Helper for pretty buttons
    private JButton createStyledButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        return btn;
    }

    public static void main(String[] args) {
        try {
            // Make it look like the native OS (smoother on Mac/Windows)
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) { }
        SwingUtilities.invokeLater(GUITest::new);
    }

    // ==========================================
    // MOCK DATABASE (The "Backend")
    // ==========================================
    class MockDatabase {
        // Username -> Password
        private Map<String, String> users = new HashMap<>();
        // TableName -> Username
        private Map<String, String> reservations = new HashMap<>();

        public MockDatabase() {
            // Add a dummy admin user
            users.put("admin", "admin");
        }

        public boolean validateUser(String u, String p) {
            return users.containsKey(u) && users.get(u).equals(p);
        }

        public boolean userExists(String u) {
            return users.containsKey(u);
        }

        public void addUser(String u, String p) {
            users.put(u, p);
        }

        public void deleteUser(String u) {
            users.remove(u);
            // Also cancel all their reservations
            reservations.values().removeIf(val -> val.equals(u));
        }

        public boolean makeReservation(String table, String user) {
            if (reservations.containsKey(table)) return false;
            reservations.put(table, user);
            return true;
        }

        public void cancelReservation(String table) {
            reservations.remove(table);
        }

        public List<String> getUserReservations(String user) {
            List<String> myRes = new ArrayList<>();
            for (Map.Entry<String, String> entry : reservations.entrySet()) {
                if (entry.getValue().equals(user)) {
                    myRes.add(entry.getKey());
                }
            }
            return myRes;
        }
    }
}