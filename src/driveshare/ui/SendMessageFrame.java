package driveshare.ui;

import driveshare.data.MessageStore;
import driveshare.data.UserStore;
import driveshare.model.Message;
import driveshare.model.User;
import driveshare.patterns.singleton.SessionManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

// UI for message system - user to user

public class SendMessageFrame extends JFrame {

    public SendMessageFrame() {
        setTitle("DriveShare - Send Message");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Get sender from current login
        String senderEmail = SessionManager.getInstance().getCurrentUser().getEmail();

        JLabel toLabel = new JLabel("To:");

        // Possible recipients
        List<String> recipientEmails = new ArrayList<>();
        for (User user : UserStore.getUsers()) {
            if (!user.getEmail().equalsIgnoreCase(senderEmail)) {
                recipientEmails.add(user.getEmail());
            }
        }

        JComboBox<String> recipientComboBox = new JComboBox<>(recipientEmails.toArray(new String[0]));

        JLabel subjectLabel = new JLabel("Subject:");
        JTextField subjectField = new JTextField();

        JLabel bodyLabel = new JLabel("Message:");
        JTextArea bodyArea = new JTextArea(6, 20);
        JScrollPane bodyScrollPane = new JScrollPane(bodyArea);

        JButton sendButton = new JButton("Send Message");

        // Send message logic
        sendButton.addActionListener(e -> {
            String recipientEmail = (String) recipientComboBox.getSelectedItem();
            String subject = subjectField.getText().trim();
            String body = bodyArea.getText().trim();

            // Valid inputs
            if (recipientEmail == null || subject.isEmpty() || body.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.");
                return;
            }

            // Create + store message
            Message message = new Message(senderEmail, recipientEmail, subject, body, "USER_MESSAGE");
            MessageStore.addMessage(message);

            JOptionPane.showMessageDialog(this, "Message sent successfully.");

            subjectField.setText("");
            bodyArea.setText("");
        });

        panel.add(toLabel);
        panel.add(recipientComboBox);
        panel.add(subjectLabel);
        panel.add(subjectField);
        panel.add(bodyLabel);
        panel.add(bodyScrollPane);

        add(panel, BorderLayout.CENTER);
        add(sendButton, BorderLayout.SOUTH);
    }
}