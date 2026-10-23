package app;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;

    public LoginFrame() {

        setTitle("Online Examination System - Login");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        JLabel title = new JLabel(
                "ONLINE EXAMINATION SYSTEM",
                SwingConstants.CENTER
        );

        emailField = new JTextField();
        passwordField = new JPasswordField();

        JButton loginButton = new JButton("Login");

        panel.add(title);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);

        add(panel, BorderLayout.CENTER);
        add(loginButton, BorderLayout.SOUTH);

        loginButton.addActionListener(e -> login());

        setVisible(true);
    }

    private void login() {

        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Enter email and password."
            );
            return;
        }

        try {

            User user = new LoginService().login(email, password);

            if (user == null) {
                JOptionPane.showMessageDialog(
                        this,
                        "Invalid login details."
                );
                return;
            }

            dispose();

            if (user instanceof Student) {
                new StudentDashboard((Student) user);
            } else {
                new AdminDashboard((Admin) user);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Database error:\n" + ex.getMessage()
            );
        }
    }
}
