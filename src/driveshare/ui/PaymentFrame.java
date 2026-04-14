package driveshare.ui;

import driveshare.data.BookingStore;
import driveshare.data.CarStore;
import driveshare.data.MessageStore;
import driveshare.model.Booking;
import driveshare.model.Car;
import driveshare.model.Message;
import driveshare.patterns.proxy.PaymentProxy;
import driveshare.patterns.singleton.SessionManager;

import javax.swing.*;
import java.awt.*;
import java.util.List;

// UI for payment
// PROXY PATTERN - process payment securely

public class PaymentFrame extends JFrame {

    public PaymentFrame()
    {
        setTitle("DriveShare - Payment");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setResizable(false);

        // Get user + booking(s)
        String currentUserEmail = SessionManager.getInstance().getCurrentUser().getEmail();
        List<Booking> unpaidBookings = BookingStore.getUnpaidBookingsForUser(currentUserEmail);

        if (unpaidBookings.isEmpty()) {
            JOptionPane.showMessageDialog(this, "You have no unpaid bookings.");
            dispose();
            return;
        }

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Choosing booking to pay for
        JLabel bookingLabel = new JLabel("Select Booking:");
        JComboBox<Booking> bookingComboBox = new JComboBox<>(unpaidBookings.toArray(new Booking[0]));

        // Booking payment info display
        JLabel carInfoLabel = new JLabel("Car Info:");
        JTextField carInfoField = new JTextField();
        carInfoField.setEditable(false);

        JLabel datesLabel = new JLabel("Dates:");
        JTextField datesField = new JTextField();
        datesField.setEditable(false);

        JLabel totalDueLabel = new JLabel("Total Due:");
        JTextField totalDueField = new JTextField();
        totalDueField.setEditable(false);

        // Pay input
        JLabel amountLabel = new JLabel("Enter Amount:");
        JTextField amountField = new JTextField();

        JButton payButton = new JButton("Pay Now");

        // Update display when booking changes
        bookingComboBox.addActionListener(e -> {
            Booking selectedBooking = (Booking) bookingComboBox.getSelectedItem();
            if (selectedBooking != null) {
                Car car = CarStore.findCarById(selectedBooking.getCarId());
                if (car != null) {
                    carInfoField.setText(car.getCarId() + " - " + car.getModel());
                } else {
                    carInfoField.setText(selectedBooking.getCarId());
                }

                datesField.setText(selectedBooking.getStartDate() + " to " + selectedBooking.getEndDate());
                totalDueField.setText("$" + selectedBooking.getTotalCost());
            }
        });

        // Set fields with first booking
        if (bookingComboBox.getItemCount() > 0) {
            Booking selectedBooking = (Booking) bookingComboBox.getSelectedItem();
            if (selectedBooking != null) {
                Car car = CarStore.findCarById(selectedBooking.getCarId());
                if (car != null) {
                    carInfoField.setText(car.getCarId() + " - " + car.getModel());
                } else {
                    carInfoField.setText(selectedBooking.getCarId());
                }

                datesField.setText(selectedBooking.getStartDate() + " to " + selectedBooking.getEndDate());
                totalDueField.setText("$" + selectedBooking.getTotalCost());
            }
        }

        // Payment logic
        payButton.addActionListener(e -> {
            Booking selectedBooking = (Booking) bookingComboBox.getSelectedItem();

            if (selectedBooking == null) {
                JOptionPane.showMessageDialog(this, "No booking selected.");
                return;
            }

            try {
                double amount = Double.parseDouble(amountField.getText().trim());

                if (amount != selectedBooking.getTotalCost()) {
                    JOptionPane.showMessageDialog(this, "Payment amount must exactly match the total due.");
                    return;
                }

                // PROXY PATTERN - used to process payment
                PaymentProxy paymentProxy = new PaymentProxy();
                boolean success = paymentProxy.processPayment(amount);

                if (success)
                {
                    selectedBooking.markPaid();

                    Car car = CarStore.findCarById(selectedBooking.getCarId());
                    String ownerEmail = (car != null) ? car.getOwnerEmail() : "unknown@driveshare.com";

                    Message renterNotification = new Message(
                            "system@driveshare.com",
                            currentUserEmail,
                            "Payment Receipt",
                            "Your payment of $" + amount + " for booking " + selectedBooking.getCarId() + " was processed successfully.",
                            "SYSTEM_NOTIFICATION"
                    );

                    Message ownerNotification = new Message(
                            "system@driveshare.com",
                            ownerEmail,
                            "Booking Payment Received",
                            "Payment of $" + amount + " was received for your car " + selectedBooking.getCarId() +
                                    " from renter " + currentUserEmail + ".",
                            "SYSTEM_NOTIFICATION"
                    );

                    MessageStore.addMessage(renterNotification);
                    MessageStore.addMessage(ownerNotification);

                    JOptionPane.showMessageDialog(this, "Payment successful!");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Payment failed.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid payment amount.");
            }
        });

        panel.add(bookingLabel);
        panel.add(bookingComboBox);
        panel.add(carInfoLabel);
        panel.add(carInfoField);
        panel.add(datesLabel);
        panel.add(datesField);
        panel.add(totalDueLabel);
        panel.add(totalDueField);
        panel.add(amountLabel);
        panel.add(amountField);

        add(panel, BorderLayout.CENTER);
        add(payButton, BorderLayout.SOUTH);
    }
}