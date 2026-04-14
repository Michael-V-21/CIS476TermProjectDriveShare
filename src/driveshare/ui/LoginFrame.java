package driveshare.ui;

import javax.swing.*;
import java.awt.*;
import driveshare.data.UserStore;
import driveshare.model.User;
import javax.swing.JOptionPane;
import driveshare.patterns.singleton.SessionManager;

// Login Screen for DriveShare
// Log in, register, or recover password

public class LoginFrame extends JFrame
{

    public LoginFrame()
    {
        setTitle("DriveShare - Login");
        setSize(550, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));

        // Input fields
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();

        // Buttons
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");
        JButton recoverButton = new JButton("Forgot Password");


        //Login button action
        loginButton.addActionListener(e -> {
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both email and password.");
                return;
            }

            // Check if user exists
            User user = UserStore.findUser(email, password);

            if (user != null) {
                SessionManager.getInstance().login(user);
                // Stores the user login using the session manager - SINGLETON PATTER
                JOptionPane.showMessageDialog(this, "Login successful!");

                DashboardFrame dashboardFrame = new DashboardFrame();
                dashboardFrame.setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials.");
            }
        });

        // Register window
        registerButton.addActionListener(e -> {
            RegisterFrame registerFrame = new RegisterFrame();
            registerFrame.setVisible(true);
        });

        // Password recovery window -- CHAIN OF RESPONSIBILITY
        recoverButton.addActionListener(e -> {
            RecoverPasswordFrame recoverPasswordFrame = new RecoverPasswordFrame();
            recoverPasswordFrame.setVisible(true);
        });


        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(registerButton);
        panel.add(new JLabel());
        panel.add(recoverButton);

        add(panel);
    }
}