import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Client extends JFrame implements ClientGuide {

    // --- THEME COLORS (Italian Flag) ---
    private final Color ITALIAN_GREEN = new Color(0, 140, 69);
    private final Color ITALIAN_RED = new Color(205, 33, 42);
    private final Color OFF_WHITE = new Color(236, 204, 162);
    private final Color DARK_TEXT = new Color(50, 50, 50);

    // --- NAVIGATION ---
    private JPanel mainDeck;
    private CardLayout cardLayout;

    // --- STATE & DATA ---
    private String currentUser = null;
    private Database db;

    // --- COMPONENT REFERENCES (For dynamic updates) ---
    private JComboBox<String> dateDropdown;
    private JComboBox<String> timeDropdown;
    private JComboBox<String> partySizeDropdown;
    private JComboBox<String> tableDropdown; // The specific table selector
    private JPanel tableGridPanel;
    private List<JButton> tableIndicators = new ArrayList<>(); // Read-only visual map

    private JLabel welcomeLabel;

    public Client() {
        super("Aiden's Pizzeria - Reservation System");
        setSize(850, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize Local Database
        db = new Database();

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

    // --- ClientGuide Implementation ---
    @Override
    public void loginGUI() {
        if (cardLayout != null && mainDeck != null) {
            cardLayout.show(mainDeck, "LOGIN");
        }
    }

    @Override
    public void setupGUI() {
        if (cardLayout != null && mainDeck != null) {
            cardLayout.show(mainDeck, "CREATE_ACCOUNT");
        }
    }

    @Override
    public String sendCommand(String command, String... params) {
        // Kept for compatibility, but logic is now local
        return "Command received (Local Mode)";
    }
    // ----------------------------------

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

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(title, gbc);

        gbc.gridy = 1;
        panel.add(subtitle, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(40, 10, 10, 10);
        panel.add(loginBtn, gbc);

        gbc.gridy = 3;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(createBtn, gbc);

        loginBtn.addActionListener(e -> loginGUI());
        createBtn.addActionListener(e -> setupGUI());

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
        header.setForeground(DARK_TEXT);

        JTextField userField = new JTextField(15);
        JPasswordField passField = new JPasswordField(15);
        JButton submitBtn = createStyledButton("Enter", ITALIAN_GREEN);
        JButton backBtn = createStyledButton("Back", Color.GRAY);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(header, gbc);

        gbc.gridy = 1;
        panel.add(new JLabel("Username:"), gbc);
        gbc.gridy = 2;
        panel.add(userField, gbc);
        gbc.gridy = 3;
        panel.add(new JLabel("Password:"), gbc);
        gbc.gridy = 4;
        panel.add(passField, gbc);
        gbc.gridy = 5;
        panel.add(submitBtn, gbc);
        gbc.gridy = 6;
        panel.add(backBtn, gbc);

        submitBtn.addActionListener(e -> {
            String u = userField.getText();
            String p = new String(passField.getPassword());
            if (validateUser(u, p)) {
                currentUser = u;
                passField.setText("");
                refreshDashboard();
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
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(header, gbc);

        gbc.gridy = 1;
        panel.add(new JLabel("New Username:"), gbc);
        gbc.gridy = 2;
        panel.add(userField, gbc);
        gbc.gridy = 3;
        panel.add(new JLabel("New Password:"), gbc);
        gbc.gridy = 4;
        panel.add(passField, gbc);
        gbc.gridy = 5;
        panel.add(createBtn, gbc);
        gbc.gridy = 6;
        panel.add(backBtn, gbc);

        createBtn.addActionListener(e -> {
            String u = userField.getText();
            String p = new String(passField.getPassword());

            if (u.isEmpty() || p.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Fields cannot be empty.");
                return;
            }

            if (userExists(u)) {
                JOptionPane.showMessageDialog(this, "Username taken! Try another.");
            } else {
                db.makeNewUser(u, p);
                JOptionPane.showMessageDialog(this, "Account Created! Please Login.");
                userField.setText("");
                passField.setText("");
                loginGUI();
            }
        });

        backBtn.addActionListener(e -> cardLayout.show(mainDeck, "WELCOME"));
        return panel;
    }

    // ==========================================
    // 4. DASHBOARD
    // ==========================================

    private JPanel createDashboardScreen() {
        JPanel dashboardPanel = new JPanel(new GridBagLayout());
        dashboardPanel.setBackground(OFF_WHITE);

        welcomeLabel = new JLabel("Ciao, User!");
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 30));
        welcomeLabel.setForeground(DARK_TEXT);

        JButton makeResBtn = createStyledButton("Make Reservation", ITALIAN_GREEN);
        JButton cancelResBtn = createStyledButton("Cancel Reservation", Color.ORANGE);
        JButton deleteAccBtn = createStyledButton("Delete Account", ITALIAN_RED);
        JButton logoutBtn = createStyledButton("Logout", Color.BLACK);

        Dimension bigBtnSize = new Dimension(250, 50);
        makeResBtn.setPreferredSize(bigBtnSize);
        cancelResBtn.setPreferredSize(bigBtnSize);
        deleteAccBtn.setPreferredSize(bigBtnSize);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.gridx = 0;
        gbc.gridy = 0;
        dashboardPanel.add(welcomeLabel, gbc);

        gbc.gridy = 1;
        dashboardPanel.add(makeResBtn, gbc);
        gbc.gridy = 2;
        dashboardPanel.add(cancelResBtn, gbc);
        gbc.gridy = 3;
        dashboardPanel.add(deleteAccBtn, gbc);
        gbc.gridy = 4;
        dashboardPanel.add(logoutBtn, gbc);

        makeResBtn.addActionListener(e -> {
            updateAvailabilityMap(); // Refresh map when entering
            cardLayout.show(mainDeck, "MAKE_RESERVATION");
        });

        cancelResBtn.addActionListener(e -> handleCancellation());

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

        logoutBtn.addActionListener(e -> {
            currentUser = null;
            cardLayout.show(mainDeck, "WELCOME");
        });

        return dashboardPanel;
    }

    private void refreshDashboard() {
        welcomeLabel.setText("Ciao, " + currentUser + "!");
    }

    private void handleCancellation() {
        List<String> myTables = getUserReservations(currentUser);

        if (myTables.isEmpty()) {
            JOptionPane.showMessageDialog(this, "You have no reservations to cancel.");
            return;
        }

        String[] choices = myTables.toArray(new String[0]);
        String input = (String) JOptionPane.showInputDialog(this,
                "Which reservation would you like to cancel?",
                "Cancel Reservation",
                JOptionPane.QUESTION_MESSAGE, null,
                choices, choices[0]);

        if (input != null) {
            cancelReservationHelper(input);
            JOptionPane.showMessageDialog(this, "Reservation cancelled.");
        }
    }

    // ==========================================
    // 5. MAKE RESERVATION (DROPDOWN + VISUAL MAP)
    // ==========================================
    private JPanel createReservationScreen() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(OFF_WHITE);

        // --- NORTH PANEL: Dropdowns ---
        // Using GridLayout to organize the menus cleanly
        JPanel selectionPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        selectionPanel.setBackground(OFF_WHITE);
        selectionPanel.setBorder(BorderFactory.createTitledBorder("Reservation Details"));

        // 1. Date Dropdown (Next 7 days)
        String[] dates = new String[7];
        LocalDate today = LocalDate.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEE, MMM dd");
        for (int i = 0; i < 7; i++) {
            dates[i] = today.plusDays(i).format(dtf);
        }
        dateDropdown = new JComboBox<>(dates);

        // 2. Time Dropdown (5 PM to 10 PM)
        String[] times = { "5:00 PM", "6:00 PM", "7:00 PM", "8:00 PM", "9:00 PM", "10:00 PM" };
        timeDropdown = new JComboBox<>(times);

        // 3. Party Size Dropdown
        String[] sizes = { "1 Person", "2 People", "3 People", "4 People", "5 People", "6+ People" };
        partySizeDropdown = new JComboBox<>(sizes);

        // 4. Table Selection Dropdown
        String[] tables = { "Table 1", "Table 2", "Table 3", "Table 4", "Table 5", "Table 6", "Table 7", "Table 8",
                "Table 9" };
        tableDropdown = new JComboBox<>(tables);

        JButton checkBtn = createStyledButton("Check Availability", Color.GRAY);
        JButton bookBtn = createStyledButton("Confirm Booking", ITALIAN_GREEN);

        // Row 1: Labels
        selectionPanel.add(new JLabel("Date:"));
        selectionPanel.add(new JLabel("Time:"));
        selectionPanel.add(new JLabel("Party Size:"));
        selectionPanel.add(new JLabel("Select Table:"));

        // Row 2: Components
        selectionPanel.add(dateDropdown);
        selectionPanel.add(timeDropdown);
        selectionPanel.add(partySizeDropdown);
        selectionPanel.add(tableDropdown);

        panel.add(selectionPanel, BorderLayout.NORTH);

        // --- CENTER PANEL: Visual Map (Read Only) ---
        tableGridPanel = new JPanel(new GridLayout(3, 3, 15, 15));
        tableGridPanel.setBackground(OFF_WHITE);
        tableGridPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        // Create 9 "Read Only" indicators (Buttons that don't do anything when clicked)
        for (int i = 1; i <= 9; i++) {
            String tableName = "Table " + i;
            JButton indicator = new JButton(tableName);
            indicator.setFont(new Font("SansSerif", Font.BOLD, 14));
            indicator.setOpaque(true);
            indicator.setBorderPainted(false);
            indicator.setEnabled(false); // Make them un-clickable (Display Only)
            indicator.setBackground(Color.GRAY);

            tableIndicators.add(indicator);
            tableGridPanel.add(indicator);
        }

        panel.add(tableGridPanel, BorderLayout.CENTER);

        // --- SOUTH PANEL: Actions & Back Button ---
        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.setBackground(OFF_WHITE);

        JButton backBtn = createStyledButton("Back to Dashboard", Color.BLACK);

        bottomPanel.add(checkBtn);
        bottomPanel.add(bookBtn);
        bottomPanel.add(backBtn);

        panel.add(bottomPanel, BorderLayout.SOUTH);

        // --- ACTION LISTENERS ---

        // 1. Back Button
        backBtn.addActionListener(e -> cardLayout.show(mainDeck, "DASHBOARD"));

        // 2. Check Availability (Updates the colors of the map)
        checkBtn.addActionListener(e -> updateAvailabilityMap());

        // 3. Confirm Booking (Uses the dropdown values)
        bookBtn.addActionListener(e -> {
            String selectedDate = (String) dateDropdown.getSelectedItem();
            String selectedTime = (String) timeDropdown.getSelectedItem();
            String selectedParty = (String) partySizeDropdown.getSelectedItem();
            String selectedTable = (String) tableDropdown.getSelectedItem();

            // Construct a unique key for the database: "Date|Time|TableID"
            String dbKey = selectedDate + "|" + selectedTime + "|" + selectedTable;

            // Attempt Booking
            boolean success = makeReservation(dbKey, currentUser, selectedParty);

            if (success) {
                JOptionPane.showMessageDialog(this,
                        "Confirmed! " + selectedTable + "\n" + selectedDate + " at " + selectedTime + "\n"
                                + selectedParty);
                updateAvailabilityMap(); // Refresh colors immediately
            } else {
                JOptionPane.showMessageDialog(this,
                        "Sorry, " + selectedTable
                                + " is already reserved for that time.\nPlease check the map or pick another time.");
                updateAvailabilityMap(); // Refresh colors so they see the red box
            }
        });

        return panel;
    }

    // Helper method to refresh the "Read Only" map
    private void updateAvailabilityMap() {
        String selectedDate = (String) dateDropdown.getSelectedItem();
        String selectedTime = (String) timeDropdown.getSelectedItem();

        for (int i = 0; i < tableIndicators.size(); i++) {
            JButton btn = tableIndicators.get(i);
            String tableName = "Table " + (i + 1);
            String dbKey = selectedDate + "|" + selectedTime + "|" + tableName;

            // Check if this specific slot is taken
            if (isBooked(dbKey)) {
                btn.setBackground(ITALIAN_RED);
                btn.setText("Occupied");
                // Note: We leave it disabled so they can't click it,
                // but we change the color so they can see it's taken.
            } else {
                btn.setBackground(ITALIAN_GREEN);
                btn.setText(tableName);
            }
        }
        tableGridPanel.repaint();
    }

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
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
        }
        SwingUtilities.invokeLater(Client::new);
    }

    // ==========================================
    // LOCAL DATABASE HELPER METHODS
    // ==========================================

    private boolean validateUser(String u, String p) {
        for (User user : db.getUsers()) {
            if (user.getUserName().equals(u) && user.getPassword().equals(p)) {
                return true;
            }
        }
        return false;
    }

    private boolean userExists(String u) {
        for (User user : db.getUsers()) {
            if (user.getUserName().equals(u)) {
                return true;
            }
        }
        return false;
    }

    private boolean isBooked(String key) {
        // key: "Date|Time|TableID"
        String[] keyParts = key.split("\\|");
        if (keyParts.length == 3) {
            String date = keyParts[0];
            String timeStr = keyParts[1];
            String tableStr = keyParts[2];
            double time = convertTimeToDouble(timeStr);
            int tableNum = Integer.parseInt(tableStr.replace("Table ", ""));

            for (Reservation r : db.getReservations()) {
                // Calculate table number from row/col
                int rRow = r.getTable().getTableRow();
                int rCol = r.getTable().getTableColumn();
                int rTableNum = (rRow - 1) * 3 + rCol;

                if (r.getDay().equals(date) &&
                        Math.abs(r.getTime() - time) < 0.01 &&
                        rTableNum == tableNum) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean makeReservation(String key, String user, String partySizeStr) {
        // key: "Date|Time|TableID"
        // partySizeStr: "2 People" or "6+ People"
        int partySize = 1;
        try {
            String num = partySizeStr.split(" ")[0];
            if (num.contains("+"))
                num = num.replace("+", "");
            partySize = Integer.parseInt(num);
        } catch (Exception e) {
        }

        String[] keyParts = key.split("\\|");
        if (keyParts.length == 3) {
            String date = keyParts[0];
            String timeStr = keyParts[1];
            String tableStr = keyParts[2];
            double time = convertTimeToDouble(timeStr);
            int tableNum = Integer.parseInt(tableStr.replace("Table ", ""));

            // Map tableNum to Row/Col
            int row = (tableNum - 1) / 3 + 1;
            int col = (tableNum - 1) % 3 + 1;

            // Find user object
            User userObj = null;
            for (User u : db.getUsers()) {
                if (u.getUserName().equals(user)) {
                    userObj = u;
                    break;
                }
            }

            if (userObj != null) {
                if (!isBooked(key)) {
                    Table tableObj = new Table(row, col, 2, 0); // Capacity 2, Price 0 for now
                    db.createReservation(date, time, userObj, partySize, tableObj);
                    return true;
                }
            }
        }
        return false;
    }

    private void cancelReservationHelper(String prettyString) {
        // prettyString: "Fri, Nov 15 - 6:00 PM - Table 1"
        String[] pParts = prettyString.split(" - ");
        if (pParts.length == 3) {
            String date = pParts[0];
            String timeStr = pParts[1];
            String tableStr = pParts[2];
            double time = convertTimeToDouble(timeStr);
            int tableNum = Integer.parseInt(tableStr.replace("Table ", ""));

            Reservation toDelete = null;
            for (Reservation r : db.getReservations()) {
                int rRow = r.getTable().getTableRow();
                int rCol = r.getTable().getTableColumn();
                int rTableNum = (rRow - 1) * 3 + rCol;

                if (r.getDay().equals(date) &&
                        Math.abs(r.getTime() - time) < 0.01 &&
                        rTableNum == tableNum) {
                    toDelete = r;
                    break;
                }
            }

            if (toDelete != null) {
                db.deleteReservation(toDelete);
            }
        }
    }

    private List<String> getUserReservations(String user) {
        List<String> list = new ArrayList<>();
        for (Reservation r : db.getReservations()) {
            if (r.getUser().getUserName().equals(user)) {
                // Format: "Fri, Nov 15 - 6:00 PM - Table 1"
                int rRow = r.getTable().getTableRow();
                int rCol = r.getTable().getTableColumn();
                int rTableNum = (rRow - 1) * 3 + rCol;

                String timeStr = convertDoubleToTime(r.getTime());
                String s = r.getDay() + " - " + timeStr + " - Table " + rTableNum;
                list.add(s);
            }
        }
        return list;
    }

    private double convertTimeToDouble(String timeStr) {
        try {
            String[] parts = timeStr.split(" "); // ["6:00", "PM"]
            String[] hm = parts[0].split(":"); // ["6", "00"]
            int h = Integer.parseInt(hm[0]);
            if (parts[1].equals("PM") && h != 12)
                h += 12;
            if (parts[1].equals("AM") && h == 12)
                h = 0;
            return h + (Integer.parseInt(hm[1]) / 60.0);
        } catch (Exception e) {
            return 0.0;
        }
    }

    private String convertDoubleToTime(double time) {
        int h = (int) time;
        int m = (int) Math.round((time - h) * 60);
        String suffix = "AM";
        if (h >= 12) {
            suffix = "PM";
            if (h > 12)
                h -= 12;
        }
        if (h == 0)
            h = 12;
        return String.format("%d:%02d %s", h, m, suffix);
    }
}
