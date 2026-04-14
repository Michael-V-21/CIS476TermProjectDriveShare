package driveshare.ui;

import javax.swing.*;
import java.awt.*;
import driveshare.model.User;
import driveshare.data.UserStore;

// Register screen for new users
// Email, password, 3 security questions + answers

public class RegisterFrame extends JFrame
{

    public RegisterFrame() {
        setTitle("DriveShare - Register");
        setSize(600, 500);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 2, 10, 10));

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();

        JLabel question1Label = new JLabel("Security Question 1:");
        JTextField question1Field = new JTextField();

        JLabel answer1Label = new JLabel("Answer 1:");
        JTextField answer1Field = new JTextField();

        JLabel question2Label = new JLabel("Security Question 2:");
        JTextField question2Field = new JTextField();

        JLabel answer2Label = new JLabel("Answer 2:");
        JTextField answer2Field = new JTextField();

        JLabel question3Label = new JLabel("Security Question 3:");
        JTextField question3Field = new JTextField();

        JLabel answer3Label = new JLabel("Answer 3:");
        JTextField answer3Field = new JTextField();

        JButton submitButton = new JButton("Register");

        // Handle registration here
        submitButton.addActionListener(e -> {
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            String question1 = question1Field.getText().trim();
            String answer1 = answer1Field.getText().trim();
            String question2 = question2Field.getText().trim();
            String answer2 = answer2Field.getText().trim();
            String question3 = question3Field.getText().trim();
            String answer3 = answer3Field.getText().trim();

            if (email.isEmpty() || password.isEmpty() ||
                    question1.isEmpty() || answer1.isEmpty() ||
                    question2.isEmpty() || answer2.isEmpty() ||
                    question3.isEmpty() || answer3.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.");
                return;
            }

            if (UserStore.emailExists(email)) {
                JOptionPane.showMessageDialog(this, "An account with that email already exists.");
                return;
            }

            User user = new User(email, password,
                    question1, answer1,
                    question2, answer2,
                    question3, answer3);

            UserStore.addUser(user);

            JOptionPane.showMessageDialog(this, "User registered!");
        });

        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(question1Label);
        panel.add(question1Field);
        panel.add(answer1Label);
        panel.add(answer1Field);
        panel.add(question2Label);
        panel.add(question2Field);
        panel.add(answer2Label);
        panel.add(answer2Field);
        panel.add(question3Label);
        panel.add(question3Field);
        panel.add(answer3Label);
        panel.add(answer3Field);

        add(panel, BorderLayout.CENTER);
        add(submitButton, BorderLayout.SOUTH);
    }
}