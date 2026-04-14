package driveshare.ui;

import driveshare.data.CarStore;
import driveshare.model.Car;
import driveshare.patterns.singleton.SessionManager;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

// UI screen for owners to manage car listings
// Update price and availability dates
// Update price = observer notification to watchers subscribed

public class ManageCarFrame extends JFrame {

    public ManageCarFrame() {
        setTitle("DriveShare - Manage Car");
        setSize(650, 400);
        setLocationRelativeTo(null);
        setResizable(false);

        // Get users email
        String currentUserEmail = SessionManager.getInstance().getCurrentUser().getEmail();

        // Filter only cars that belong to user
        List<Car> ownedCars = new ArrayList<>();
        for (Car car : CarStore.getCars()) {
            if (car.getOwnerEmail().equalsIgnoreCase(currentUserEmail)) {
                ownedCars.add(car);
            }
        }

        if (ownedCars.isEmpty()) {
            JOptionPane.showMessageDialog(this, "You do not have any car listings to manage.");
            dispose();
            return;
        }

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel carLabel = new JLabel("Select Car:");
        JComboBox<Car> carComboBox = new JComboBox<>(ownedCars.toArray(new Car[0]));

        JLabel newPriceLabel = new JLabel("New Price:");
        JTextField newPriceField = new JTextField();

        JLabel availableFromLabel = new JLabel("Available From (YYYY-MM-DD):");
        JTextField availableFromField = new JTextField();

        JLabel availableToLabel = new JLabel("Available To (YYYY-MM-DD):");
        JTextField availableToField = new JTextField();

        JButton updateButton = new JButton("Update Listing");

        // When different car selected, fill in fields with new values
        carComboBox.addActionListener(e -> {
            Car selectedCar = (Car) carComboBox.getSelectedItem();
            if (selectedCar != null) {
                newPriceField.setText(String.valueOf(selectedCar.getPricePerDay()));
                availableFromField.setText(String.valueOf(selectedCar.getAvailableFrom()));
                availableToField.setText(String.valueOf(selectedCar.getAvailableTo()));
            }
        });

        // Set fields with first car values
        if (carComboBox.getItemCount() > 0) {
            Car selectedCar = (Car) carComboBox.getSelectedItem();
            if (selectedCar != null) {
                newPriceField.setText(String.valueOf(selectedCar.getPricePerDay()));
                availableFromField.setText(String.valueOf(selectedCar.getAvailableFrom()));
                availableToField.setText(String.valueOf(selectedCar.getAvailableTo()));
            }
        }

        // Update logic
        updateButton.addActionListener(e -> {
            Car selectedCar = (Car) carComboBox.getSelectedItem();

            if (selectedCar == null) {
                JOptionPane.showMessageDialog(this, "No car selected.");
                return;
            }

            try {
                double newPrice = Double.parseDouble(newPriceField.getText().trim());
                LocalDate availableFrom = LocalDate.parse(availableFromField.getText().trim());
                LocalDate availableTo = LocalDate.parse(availableToField.getText().trim());


                // Valid price
                if (newPrice <= 0) {
                    JOptionPane.showMessageDialog(this, "Price must be greater than 0.");
                    return;
                }

                // Valid dates
                if (availableTo.isBefore(availableFrom)) {
                    JOptionPane.showMessageDialog(this, "Available To cannot be before Available From.");
                    return;
                }

                // Update car
                // GTrigger observer notifications
                selectedCar.setPricePerDay(newPrice);
                selectedCar.setAvailability(availableFrom, availableTo);

                JOptionPane.showMessageDialog(this, "Car listing updated successfully.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid price.");
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Please enter dates in YYYY-MM-DD format.");
            }
        });

        panel.add(carLabel);
        panel.add(carComboBox);
        panel.add(newPriceLabel);
        panel.add(newPriceField);
        panel.add(availableFromLabel);
        panel.add(availableFromField);
        panel.add(availableToLabel);
        panel.add(availableToField);

        add(panel, BorderLayout.CENTER);
        add(updateButton, BorderLayout.SOUTH);
    }
}