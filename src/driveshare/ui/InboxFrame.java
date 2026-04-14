package driveshare.ui;

import driveshare.data.MessageStore;
import driveshare.model.Message;
import driveshare.patterns.singleton.SessionManager;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.time.format.DateTimeFormatter;

// UI for user inbox -- current login

public class InboxFrame extends JFrame {

    public InboxFrame() {
        setTitle("DriveShare - Inbox");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JTextArea inboxArea = new JTextArea();
        inboxArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(inboxArea);

        // Get message for logged-in user
        String currentUserEmail = SessionManager.getInstance().getCurrentUser().getEmail();
        List<Message> messages = MessageStore.getMessagesForUser(currentUserEmail);

        if (messages.isEmpty()) {
            inboxArea.setText("No messages in your inbox.");
        } else {
            StringBuilder sb = new StringBuilder();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm");

            // Text output for messages
            for (Message message : messages) {
                sb.append("Type: ").append(message.getType()).append("\n");
                sb.append("From: ").append(message.getSenderEmail()).append("\n");
                sb.append("Subject: ").append(message.getSubject()).append("\n");
                sb.append("Time: ").append(message.getTimestamp().format(formatter)).append("\n");
                sb.append("Message: ").append(message.getBody()).append("\n");
                sb.append("--------------------------------------------------\n");
            }

            inboxArea.setText(sb.toString());
        }

        mainPanel.add(scrollPane, BorderLayout.CENTER);
        add(mainPanel);
    }
}