package driveshare.ui;

import driveshare.data.BookingStore;
import driveshare.data.CarStore;
import driveshare.model.Booking;
import driveshare.model.Car;
import driveshare.patterns.singleton.SessionManager;
import driveshare.data.MessageStore;
import driveshare.model.Message;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.time.temporal.ChronoUnit;

// UI for booking cars

public class BookCarFrame extends JFrame {

    public BookCarFrame() {
        if (!SessionManager.getInstance().isLoggedIn()) {
            JOptionPane.showMessageDialog(this, "You must log in first.");
            dispose();
            return;
        }

        setTitle("DriveShare - Book Car");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Autofill possible cars
        JLabel carLabel = new JLabel("Select Car:");
        List<Car> cars = CarStore.getCars();
        JComboBox<Car> carComboBox = new JComboBox<>(cars.toArray(new Car[0]));

        // Autofill email from login
        JLabel renterEmailLabel = new JLabel("Renter Email:");
        JTextField renterEmailField = new JTextField();

        if (SessionManager.getInstance().getCurrentUser() != null) {
            renterEmailField.setText(SessionManager.getInstance().getCurrentUser().getEmail());
            renterEmailField.setEditable(false);
        }

        // Date
        JLabel startDateLabel = new JLabel("Start Date (YYYY-MM-DD):");
        JTextField startDateField = new JTextField();

        JLabel endDateLabel = new JLabel("End Date (YYYY-MM-DD):");
        JTextField endDateField = new JTextField();

        JButton bookButton = new JButton("Book Car");

        // Booking logic
        bookButton.addActionListener(e -> {
            try {
                Car selectedCar = (Car) carComboBox.getSelectedItem();
                String renterEmail = renterEmailField.getText().trim();
                String startText = startDateField.getText().trim();
                String endText = endDateField.getText().trim();

                // Valid inputs
                if (selectedCar == null || renterEmail.isEmpty() || startText.isEmpty() || endText.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill in all fields.");
                    return;
                }

                LocalDate startDate = LocalDate.parse(startText);
                LocalDate endDate = LocalDate.parse(endText);

                if (endDate.isBefore(startDate)) {
                    JOptionPane.showMessageDialog(this, "End date cannot be before start date.");
                    return;
                }

                // Validate that dates are in availability
                boolean withinAvailability =
                        !startDate.isBefore(selectedCar.getAvailableFrom()) &&
                                !endDate.isAfter(selectedCar.getAvailableTo());

                if (!withinAvailability) {
                    JOptionPane.showMessageDialog(this, "Those dates are outside the car's availability window.");
                    return;
                }

                // Validate for overlapping bookings
                if (BookingStore.isCarBooked(selectedCar.getCarId(), startDate, endDate)) {
                    JOptionPane.showMessageDialog(this, "This car is already booked for overlapping dates.");
                } else {

                    // Total cost for rental
                    long numberOfDays = ChronoUnit.DAYS.between(startDate, endDate) + 1;
                    double totalCost = numberOfDays * selectedCar.getPricePerDay();

                    // Create + store booking
                    Booking booking = new Booking(selectedCar.getCarId(), renterEmail, startDate, endDate, totalCost);

                    BookingStore.addBooking(booking);

                    // Booking system notification
                    Message ownerNotification = new Message(
                            "system@driveshare.com",
                            selectedCar.getOwnerEmail(),
                            "New Booking Request",
                            "Your car " + selectedCar.getCarId() + " has been booked by " + renterEmail +
                                    " from " + startDate + " to " + endDate + ".",
                            "SYSTEM_NOTIFICATION"
                    );

                    Message renterNotification = new Message(
                            "system@driveshare.com",
                            renterEmail,
                            "Booking Confirmation",
                            "Your booking for " + selectedCar.getCarId() +
                                    " is confirmed from " + startDate + " to " + endDate +
                                    ". Total due: $" + totalCost + ".",
                            "SYSTEM_NOTIFICATION"
                    );

                    // Store notification
                    MessageStore.addMessage(ownerNotification);
                    MessageStore.addMessage(renterNotification);

                    JOptionPane.showMessageDialog(this, "Booking created for " + selectedCar.getCarId() + ".");

                    // Clear panel
                    startDateField.setText("");
                    endDateField.setText("");
                }
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Please enter dates in YYYY-MM-DD format.");
            }
        });

        panel.add(carLabel);
        panel.add(carComboBox);
        panel.add(renterEmailLabel);
        panel.add(renterEmailField);
        panel.add(startDateLabel);
        panel.add(startDateField);
        panel.add(endDateLabel);
        panel.add(endDateField);

        add(panel, BorderLayout.CENTER);
        add(bookButton, BorderLayout.SOUTH);
    }
}