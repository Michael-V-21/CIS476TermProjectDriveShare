package driveshare.data;

import driveshare.model.Message;

import java.util.ArrayList;
import java.util.List;

// List type of database for messages
// Stores user and system messages

public class MessageStore
{

    // List of all messages
    private static List<Message> messages = new ArrayList<>();

    public static void addMessage(Message message) {
        messages.add(message);
    }

    // Retrieve messages for the inbox implementation
    public static List<Message> getMessagesForUser(String email) {
        List<Message> inbox = new ArrayList<>();

        for (Message message : messages) {
            if (message.getRecipientEmail().equalsIgnoreCase(email)) {
                inbox.add(message);
            }
        }

        return inbox;
    }
}