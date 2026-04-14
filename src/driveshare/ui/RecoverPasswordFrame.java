package driveshare.ui;

import driveshare.data.UserStore;
import driveshare.model.User;
import driveshare.patterns.chain.Question1Handler;
import driveshare.patterns.chain.Question2Handler;
import driveshare.patterns.chain.Question3Handler;

import javax.swing.*;
import java.awt.*;

// UI for password recovery
// CHAIN OF RESPONSIBILITY - validate user's 3 security questions in sequence

public class RecoverPasswordFrame extends JFrame
{
    // Store current user questions
    private User foundUser;

    public RecoverPasswordFrame() {
        setTitle("DriveShare - Recover Password");
        setSize(600, 450);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(8, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();

        JButton loadQuestionsButton = new JButton("Load Questions");

        JLabel question1Label = new JLabel("Question 1:");
        JTextField question1Field = new JTextField();
        question1Field.setEditable(false);

        JLabel answer1Label = new JLabel("Answer 1:");
        JTextField answer1Field = new JTextField();

        JLabel question2Label = new JLabel("Question 2:");
        JTextField question2Field = new JTextField();
        question2Field.setEditable(false);

        JLabel answer2Label = new JLabel("Answer 2:");
        JTextField answer2Field = new JTextField();

        JLabel question3Label = new JLabel("Question 3:");
        JTextField question3Field = new JTextField();
        question3Field.setEditable(false);

        JLabel answer3Label = new JLabel("Answer 3:");
        JTextField answer3Field = new JTextField();

        JButton recoverButton = new JButton("Recover Password");

        // Load questions based on login
        loadQuestionsButton.addActionListener(e -> {
            String email = emailField.getText();
            foundUser = UserStore.findUserByEmail(email);

            if (foundUser == null) {
                JOptionPane.showMessageDialog(this, "User not found.");
                question1Field.setText("");
                question2Field.setText("");
                question3Field.setText("");
                return;
            }

            question1Field.setText(foundUser.getSecurityQuestion1());
            question2Field.setText(foundUser.getSecurityQuestion2());
            question3Field.setText(foundUser.getSecurityQuestion3());
        });

        // Validate answers using CHAIN OF RESPONSIBILITY
        recoverButton.addActionListener(e -> {
            if (foundUser == null) {
                JOptionPane.showMessageDialog(this, "Please load the security questions first.");
                return;
            }

            Question1Handler q1 = new Question1Handler(foundUser.getSecurityAnswer1());
            Question2Handler q2 = new Question2Handler(foundUser.getSecurityAnswer2());
            Question3Handler q3 = new Question3Handler(foundUser.getSecurityAnswer3());

            q1.setNextHandler(q2);
            q2.setNextHandler(q3);

            // Get answers in order
            String[] answers = {
                    answer1Field.getText(),
                    answer2Field.getText(),
                    answer3Field.getText()
            };

            // Chain start from 1st handler
            boolean success = q1.handle(answers, 0);

            if (success) {
                JOptionPane.showMessageDialog(this, "Password recovered: " + foundUser.getPassword());
            } else {
                JOptionPane.showMessageDialog(this, "Security answers incorrect.");
            }
        });

        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(new JLabel());
        panel.add(loadQuestionsButton);

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
        add(recoverButton, BorderLayout.SOUTH);
    }
}