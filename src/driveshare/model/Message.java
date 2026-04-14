package driveshare.model;

import java.time.LocalDateTime;

// Represents a message

public class Message
{
    private String senderEmail;
    private String recipientEmail;

    private String subject;
    private String body;

    private LocalDateTime timestamp;

    private String type;

    public Message(String senderEmail, String recipientEmail, String subject, String body, String type) {
        this.senderEmail = senderEmail;
        this.recipientEmail = recipientEmail;
        this.subject = subject;
        this.body = body;
        this.type = type;
        this.timestamp = LocalDateTime.now();
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getType() {
        return type;
    }
}