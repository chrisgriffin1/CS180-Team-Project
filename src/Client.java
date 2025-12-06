import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Client extends JFrame implements ClientGuide {

    private JPanel cards;
    private CardLayout cl;

    private String user = null;

    // Client-side socket fields
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    private JComboBox<String> dateBox;
    private JComboBox<String> timeBox;
    private JComboBox<String> partyBox;
    private JComboBox<String> tableBox;
    private JPanel grid;
    private List<JButton> buttons = new ArrayList<>();

    private JLabel welcomeText;

    public Client() {
        super("Boiler Bistro - Reservation System");
        setSize(850, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        try {
            socket = new Socket("localhost", 47906);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error connecting to server: " + e.getMessage());
            dispose();
            return;
        }

        cl = new CardLayout();
        cards = new JPanel(cl);

        cards.add(welcome(), "WELCOME");
        cards.add(login(), "LOGIN");
        cards.add(createAccount(), "CREATE_ACCOUNT");
        cards.add(dashboard(), "DASHBOARD");
        cards.add(reservation(), "MAKE_RESERVATION");

        add(cards);
        setVisible(true);
    }

    @Override
    public void loginGUI() {
        if (cl != null && cards != null) {
            cl.show(cards, "LOGIN");
        }
    }

    @Override
    public void setupGUI() {
        if (cl != null && cards != null) {
            cl.show(cards, "CREATE_ACCOUNT");
        }
    }

    @Override
    public String sendCommand(String command, String... params) {
        if (writer != null && reader != null) {
            String full = command;
            if (params.length > 0) {
                full += ";" + String.join(";", params);
            }
            writer.println(full);
            try {
                return reader.readLine();
            } catch (IOException e) {
                return "Error";
            }
        }
        return "Error: No Connection";
    }

    private JPanel welcome() {
        JPanel p = new JPanel(new GridBagLayout());

        JLabel t = new JLabel("Boiler Bistro");
        JLabel s = new JLabel("Authentic Italian Dining");

        JButton b1 = new JButton("Login");
        JButton b2 = new JButton("Create an Account");

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);
        c.gridx = 0;
        c.gridy = 0;
        p.add(t, c);

        c.gridy = 1;
        p.add(s, c);

        c.gridy = 2;
        p.add(b1, c);

        c.gridy = 3;
        p.add(b2, c);

        b1.addActionListener(e -> loginGUI());
        b2.addActionListener(e -> setupGUI());

        return p;
    }

    private JPanel login() {
        JPanel p = new JPanel(new GridBagLayout());

        JLabel h = new JLabel("Please Login");

        JTextField uField = new JTextField(15);
        JPasswordField pField = new JPasswordField(15);
        JButton b1 = new JButton("Enter");
        JButton b2 = new JButton("Back");

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.gridx = 0;
        c.gridy = 0;
        p.add(h, c);

        c.gridy = 1;
        p.add(new JLabel("Username:"), c);
        c.gridy = 2;
        p.add(uField, c);
        c.gridy = 3;
        p.add(new JLabel("Password:"), c);
        c.gridy = 4;
        p.add(pField, c);
        c.gridy = 5;
        p.add(b1, c);
        c.gridy = 6;
        p.add(b2, c);

        b1.addActionListener(e -> {
            String u = uField.getText();
            String pass = new String(pField.getPassword());
            if (checkUser(u, pass)) {
                user = u;
                pField.setText("");
                refresh();
                cl.show(cards, "DASHBOARD");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Username or Password!");
            }
        });

        b2.addActionListener(e -> cl.show(cards, "WELCOME"));
        return p;
    }

    private JPanel createAccount() {
        JPanel p = new JPanel(new GridBagLayout());

        JLabel h = new JLabel("Create Account");

        JTextField uField = new JTextField(15);
        JPasswordField pField = new JPasswordField(15);
        JButton b1 = new JButton("Create");
        JButton b2 = new JButton("Back");

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.gridx = 0;
        c.gridy = 0;
        p.add(h, c);

        c.gridy = 1;
        p.add(new JLabel("New Username:"), c);
        c.gridy = 2;
        p.add(uField, c);
        c.gridy = 3;
        p.add(new JLabel("New Password:"), c);
        c.gridy = 4;
        p.add(pField, c);
        c.gridy = 5;
        p.add(b1, c);
        c.gridy = 6;
        p.add(b2, c);

        b1.addActionListener(e -> {
            String u = uField.getText();
            String pass = new String(pField.getPassword());

            if (u.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Fields cannot be empty.");
                return;
            }

            if (checkUsername(u)) {
                JOptionPane.showMessageDialog(this, "Username taken! Try another.");
            } else {
                if (makeNewUser(u, pass)) {
                    JOptionPane.showMessageDialog(this, "Account Created! Please Login.");
                    uField.setText("");
                    pField.setText("");
                    loginGUI();
                } else {
                    JOptionPane.showMessageDialog(this, "Error creating account.");
                }
            }
        });

        b2.addActionListener(e -> cl.show(cards, "WELCOME"));
        return p;
    }

    private JPanel dashboard() {
        JPanel p = new JPanel(new GridBagLayout());

        welcomeText = new JLabel("Hello User!");

        JButton b1 = new JButton("Make Reservation");
        JButton b2 = new JButton("Cancel Reservation");
        JButton b3 = new JButton("Delete Account");
        JButton b4 = new JButton("Logout");

        Dimension d = new Dimension(200, 40);
        b1.setPreferredSize(d);
        b2.setPreferredSize(d);
        b3.setPreferredSize(d);
        b4.setPreferredSize(d);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);
        c.gridx = 0;
        c.gridy = 0;
        p.add(welcomeText, c);

        c.gridy = 1;
        p.add(b1, c);
        c.gridy = 2;
        p.add(b2, c);
        c.gridy = 3;
        p.add(b3, c);
        c.gridy = 4;
        p.add(b4, c);

        b1.addActionListener(e -> {
            updateMap();
            cl.show(cards, "MAKE_RESERVATION");
        });

        b2.addActionListener(e -> cancel());

        b3.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete your account?",
                    "Delete Account", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                deleteUser(user);
                user = null;
                cl.show(cards, "WELCOME");
                JOptionPane.showMessageDialog(this, "Account Deleted.");
            }
        });

        b4.addActionListener(e -> {
            user = null;
            cl.show(cards, "WELCOME");
        });

        return p;
    }

    private void refresh() {
        welcomeText.setText("Hello, " + user + "!");
    }

    private void cancel() {
        List<String> list = getReservations(user);

        if (list.isEmpty()) {
            JOptionPane.showMessageDialog(this, "You have no reservations to cancel.");
            return;
        }

        String[] choices = list.toArray(new String[0]);
        String input = (String) JOptionPane.showInputDialog(this,
                "Which reservation would you like to cancel?",
                "Cancel Reservation",
                JOptionPane.QUESTION_MESSAGE, null,
                choices, choices[0]);

        if (input != null) {
            cancel(input);
            JOptionPane.showMessageDialog(this, "Reservation cancelled.");
        }
    }

    private JPanel reservation() {
        JPanel p = new JPanel(new BorderLayout());

        JPanel top = new JPanel(new GridLayout(2, 4, 5, 5));
        top.setBorder(BorderFactory.createTitledBorder(""));

        String[] dates = new String[7];
        LocalDate today = LocalDate.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEE, MMM dd");
        for (int i = 0; i < 7; i++) {
            dates[i] = today.plusDays(i).format(dtf);
        }
        dateBox = new JComboBox<>(dates);

        String[] times = { "5:00 PM", "6:00 PM", "7:00 PM", "8:00 PM", "9:00 PM", "10:00 PM" };
        timeBox = new JComboBox<>(times);

        String[] sizes = { "1 Person", "2 People", "3 People" };
        partyBox = new JComboBox<>(sizes);

        String[] tables = { "Table 1", "Table 2", "Table 3", "Table 4", "Table 5", "Table 6", "Table 7", "Table 8",
                "Table 9" };
        tableBox = new JComboBox<>(tables);

        // Removed Check Availability button
        JButton b2 = new JButton("Confirm Booking");

        top.add(new JLabel("Date:"));
        top.add(new JLabel("Time:"));
        top.add(new JLabel("Party Size:"));
        top.add(new JLabel("Select Table:"));

        top.add(dateBox);
        top.add(timeBox);
        top.add(partyBox);
        top.add(tableBox);

        p.add(top, BorderLayout.NORTH);

        grid = new JPanel(new GridLayout(3, 3, 10, 10));
        grid.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        for (int i = 1; i <= 9; i++) {
            String name = "Table " + i;
            JButton b = new JButton(name);
            b.setEnabled(false);

            buttons.add(b);
            grid.add(b);
        }

        p.add(grid, BorderLayout.CENTER);

        JPanel bot = new JPanel(new FlowLayout());

        JButton b3 = new JButton("Back to Dashboard");

        bot.add(b2);
        bot.add(b3);

        p.add(bot, BorderLayout.SOUTH);

        b3.addActionListener(e -> cl.show(cards, "DASHBOARD"));

        // Auto-update map when date or time changes
        dateBox.addActionListener(e -> updateMap());
        timeBox.addActionListener(e -> updateMap());

        b2.addActionListener(e -> {
            String d = (String) dateBox.getSelectedItem();
            String t = (String) timeBox.getSelectedItem();
            String s = (String) partyBox.getSelectedItem();
            String tb = (String) tableBox.getSelectedItem();

            String key = d + "|" + t + "|" + tb;

            boolean success = book(key, user, s);

            if (success) {
                JOptionPane.showMessageDialog(this,
                        "Confirmed! " + tb + "\n" + d + " at " + t + "\n"
                                + s);
                updateMap();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Sorry, " + tb
                                + " is already reserved for that time.\nPlease check the map or pick another time.");
                updateMap();
            }
        });

        return p;
    }

    private void updateMap() {
        String d = (String) dateBox.getSelectedItem();
        String t = (String) timeBox.getSelectedItem();

        for (int i = 0; i < buttons.size(); i++) {
            JButton b = buttons.get(i);
            String name = "Table " + (i + 1);
            String key = d + "|" + t + "|" + name;

            if (checkBooked(key)) {
                b.setBackground(Color.RED);
                b.setText("Occupied");
            } else {
                b.setBackground(Color.GREEN);
                b.setText(name);
            }
        }
        grid.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Client::new);
    }

    // --- NETWORK HELPER METHODS ---

    private boolean checkUser(String u, String p) {
        String resp = sendCommand("LOGIN", u, p);
        return "true".equals(resp);
    }

    private boolean checkUsername(String u) {
        String resp = sendCommand("CHECK_USER", u);
        return "true".equals(resp);
    }

    private boolean makeNewUser(String u, String p) {
        String resp = sendCommand("CREATE_USER", u, p);
        return "true".equals(resp);
    }

    private void deleteUser(String u) {
        sendCommand("DELETE_USER", u);
    }

    private boolean checkBooked(String key) {
        String resp = sendCommand("IS_BOOKED", key);
        return "true".equals(resp);
    }

    private boolean book(String key, String user, String size) {
        // size: "2 People" -> "2"
        String num = size.split(" ")[0];
        String resp = sendCommand("MAKE_RESERVATION", key, user, num);
        return "true".equals(resp);
    }

    private void cancel(String prettyString) {
        sendCommand("CANCEL_RESERVATION", prettyString);
    }

    private List<String> getReservations(String user) {
        // We need to parse the raw data from server and convert to pretty strings
        // Server returns: day,time,username,partySize,tableRow,tableCol;...
        String resp = sendCommand("GET_RESERVATIONS");
        List<String> list = new ArrayList<>();
        if (resp != null && !resp.isEmpty() && !resp.equals("false")) {
            String[] parts = resp.split(";");
            for (String p : parts) {
                String[] rParts = p.split("\\|");
                if (rParts.length == 6) {
                    String uname = rParts[2];
                    if (uname.equals(user)) {
                        String day = rParts[0];
                        double time = Double.parseDouble(rParts[1]);
                        int row = Integer.parseInt(rParts[4]);
                        int col = Integer.parseInt(rParts[5]);
                        int tableNum = (row - 1) * 3 + col;

                        String t = toTime(time);
                        String s = day + " - " + t + " - Table " + tableNum;
                        list.add(s);
                    }
                }
            }
        }
        return list;
    }

    private String toTime(double d) {
        int h = (int) d;
        int m = (int) Math.round((d - h) * 60);
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
