package ui;

import auth.UserManager;

import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends JFrame {
    public AdminDashboard(String username) {
        setTitle("Admin Dashboard");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Welcome, Admin", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        add(title, BorderLayout.NORTH);

        JPanel controls = new JPanel(new GridLayout(2, 1, 10, 10));
        JButton addBtn = new JButton("Add New Student");
        JButton viewBtn = new JButton("View All Students");

        addBtn.addActionListener(e -> {
            String newUser = JOptionPane.showInputDialog(this, "Enter new student username:");
            String newPass = JOptionPane.showInputDialog(this, "Enter password:");
            if (newUser != null && newPass != null) {
                boolean added = UserManager.addStudent(newUser, newPass);
                JOptionPane.showMessageDialog(this, added ? "Student added." : "User already exists.");
            }
        });

        viewBtn.addActionListener(e -> {
            StringBuilder list = new StringBuilder("Registered Students:\n");
            for (String user : UserManager.getAllStudents()) {
                list.append("- ").append(user).append("\n");
            }
            JOptionPane.showMessageDialog(this, list.toString());
        });

        controls.add(addBtn);
        controls.add(viewBtn);
        add(controls, BorderLayout.CENTER);

        setVisible(true);
    }
}
