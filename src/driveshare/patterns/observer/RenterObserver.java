package driveshare.patterns.observer;

import driveshare.data.MessageStore;
import driveshare.model.Message;

// Concrete observer - user watching car

public class RenterObserver implements CarObserver
{
    private String renterEmail;
    private double targetPrice;

    public RenterObserver(String renterEmail, double targetPrice) {
        this.renterEmail = renterEmail;
        this.targetPrice = targetPrice;
    }

    // Called when observed car sends notification
    @Override
    public void update(String message)
    {
        // output to console (need now before i implement the inbox)
        System.out.println("Notification sent to " + renterEmail + ": " + message);

        // Create notification and store in inbox
        Message notification = new Message(
                "system@driveshare.com",
                renterEmail,
                "Watched Car Update",
                message,
                "SYSTEM_NOTIFICATION"
        );

        MessageStore.addMessage(notification);
    }

    public String getRenterEmail() {
        return renterEmail;
    }

    public double getTargetPrice() {
        return targetPrice;
    }
}