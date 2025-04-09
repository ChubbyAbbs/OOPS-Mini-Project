package ui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import auth.UserManager; // 👈 Make sure this import exists

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginFrame() {
        setUndecorated(true);
        setSize(420, 340);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(30, 30, 30));
        setLayout(new BorderLayout());

        // Title Bar
        JPanel titleBar = new JPanel(new BorderLayout());
        titleBar.setBackground(new Color(20, 20, 20));
        titleBar.setPreferredSize(new Dimension(420, 35));

        JLabel title = new JLabel("  Student Course Management");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 13));

        JButton closeBtn = new JButton("<html><b>&times;</b></html>");
        closeBtn.setForeground(Color.WHITE);
        closeBtn.setBackground(new Color(178, 34, 34));
        closeBtn.setFocusPainted(false);
        closeBtn.setBorderPainted(false);
        closeBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        closeBtn.setPreferredSize(new Dimension(40, 35));
        closeBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        closeBtn.addActionListener(e -> System.exit(0));

        titleBar.add(title, BorderLayout.WEST);
        titleBar.add(closeBtn, BorderLayout.EAST);
        add(titleBar, BorderLayout.NORTH);

        // Content Panel
        JPanel panel = new JPanel();
        panel.setBackground(new Color(30, 30, 30));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JLabel header = new JLabel("Welcome to Student Course Management");
        header.setFont(new Font("Segoe UI", Font.BOLD, 16));
        header.setForeground(Color.WHITE);
        header.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(header);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Username
        JPanel userRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        userRow.setBackground(new Color(30, 30, 30));
        JLabel userLabel = new JLabel("Username:");
        userLabel.setForeground(Color.WHITE);
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        usernameField = createUnderlineTextField();
        userRow.add(userLabel);
        userRow.add(usernameField);
        panel.add(userRow);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Password
        JPanel passRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        passRow.setBackground(new Color(30, 30, 30));
        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(Color.WHITE);
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        passwordField = createUnderlinePasswordField();
        passRow.add(passLabel);
        passRow.add(passwordField);
        panel.add(passRow);
        panel.add(Box.createRigidArea(new Dimension(0, 25)));

        // Login Button
        JButton loginBtn = new JButton("Login");
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginBtn.setBackground(new Color(46, 204, 113));
        loginBtn.setForeground(Color.BLACK);
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        loginBtn.setFocusPainted(false);
        loginBtn.setPreferredSize(new Dimension(120, 40));
        loginBtn.setBorder(new RoundedBorder(20));
        panel.add(loginBtn);

        add(panel, BorderLayout.CENTER);

        // 🔐 LOGIN LOGIC (Simulated)
        loginBtn.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            if (UserManager.isValidUser(username, password)) {
                if (UserManager.isAdmin(username)) {
                    new AdminDashboard(username);
                } else {
                    new StudentDashboard(username);
                }
                dispose(); // Close login frame
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password.");
            }
        });

        setVisible(true);
    }

    private JTextField createUnderlineTextField() {
        JTextField field = new JTextField(18);
        field.setBackground(new Color(30, 30, 30));
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.GRAY));
        return field;
    }

    private JPasswordField createUnderlinePasswordField() {
        JPasswordField field = new JPasswordField(18);
        field.setBackground(new Color(30, 30, 30));
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.GRAY));
        return field;
    }

    // Rounded border for login button
    class RoundedBorder implements Border {
        private int radius;

        RoundedBorder(int radius) {
            this.radius = radius;
        }

        public Insets getBorderInsets(Component c) {
            return new Insets(radius + 1, radius + 1, radius + 2, radius);
        }

        public boolean isBorderOpaque() {
            return false;
        }

        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.setColor(c.getBackground());
            g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }
}
