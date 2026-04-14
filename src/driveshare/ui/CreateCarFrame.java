package driveshare.ui;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeParseException;

import driveshare.model.Car;
import driveshare.data.CarStore;
import driveshare.patterns.builder.CarBuilder;
import driveshare.patterns.singleton.SessionManager;

// UI screen for new car listing
// Construct car objects -- BUILDER PATTERN

public class CreateCarFrame extends JFrame
{

    public CreateCarFrame()

    {
        setTitle("DriveShare - Create Car Listing");
        setSize(650, 550);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridLayout(10, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel modelLabel = new JLabel("Car Model:");
        JTextField modelField = new JTextField();

        JLabel yearLabel = new JLabel("Year:");
        JTextField yearField = new JTextField();

        JLabel mileageLabel = new JLabel("Mileage:");
        JTextField mileageField = new JTextField();

        JLabel locationLabel = new JLabel("Pickup Location:");
        JTextField locationField = new JTextField();

        JLabel priceLabel = new JLabel("Price Per Day:");
        JTextField priceField = new JTextField();

        JLabel availableFromLabel = new JLabel("Available From (YYYY-MM-DD):");
        JTextField availableFromField = new JTextField();

        JLabel availableToLabel = new JLabel("Available To (YYYY-MM-DD):");
        JTextField availableToField = new JTextField();

        JLabel colorLabel = new JLabel("Color:");
        JTextField colorField = new JTextField();

        JLabel transmissionLabel = new JLabel("Transmission:");
        JTextField transmissionField = new JTextField();

        JLabel descriptionLabel = new JLabel("Description:");
        JTextField descriptionField = new JTextField();

        JButton createButton = new JButton("Create Listing");

        // CAR creation logic
        createButton.addActionListener(e -> {
            try {
                String model = modelField.getText().trim();
                String location = locationField.getText().trim();
                String color = colorField.getText().trim();
                String transmission = transmissionField.getText().trim();
                String description = descriptionField.getText().trim();
                String availableFromText = availableFromField.getText().trim();
                String availableToText = availableToField.getText().trim();

                // Make sure required fields are filled
                if (model.isEmpty() || location.isEmpty() || availableFromText.isEmpty() || availableToText.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Model, location, and availability dates are required.");
                    return;
                }

                int year = Integer.parseInt(yearField.getText().trim());
                int mileage = Integer.parseInt(mileageField.getText().trim());
                double pricePerDay = Double.parseDouble(priceField.getText().trim());

                // Valid car year
                int currentYear = Year.now().getValue();
                if (year < 1886 || year > currentYear + 1) {
                    JOptionPane.showMessageDialog(this, "Please enter a realistic year (1886 - " + (currentYear + 1) + ").");
                    return;
                }

                // Valid mileage
                if (mileage < 0) {
                    JOptionPane.showMessageDialog(this, "Mileage cannot be negative.");
                    return;
                }

                // Valid price
                if (pricePerDay <= 0) {
                    JOptionPane.showMessageDialog(this, "Price per day must be greater than 0.");
                    return;
                }

                // Valid dates
                LocalDate availableFrom = LocalDate.parse(availableFromText);
                LocalDate availableTo = LocalDate.parse(availableToText);

                if (availableTo.isBefore(availableFrom)) {
                    JOptionPane.showMessageDialog(this, "Available To cannot be before Available From.");
                    return;
                }

                // Unique carID generated here
                String carId = CarStore.generateCarId();
                String ownerEmail = SessionManager.getInstance().getCurrentUser().getEmail();

                // BUILD CAR -- BUILDER PATTERN
                Car car = new CarBuilder()
                        .setCarId(carId)
                        .setOwnerEmail(ownerEmail)
                        .setModel(model)
                        .setYear(year)
                        .setMileage(mileage)
                        .setLocation(location)
                        .setPricePerDay(pricePerDay)
                        .setAvailableFrom(availableFrom)
                        .setAvailableTo(availableTo)
                        .setColor(color)
                        .setTransmission(transmission)
                        .setDescription(description)
                        .build();

                // Store car
                CarStore.addCar(car);

                JOptionPane.showMessageDialog(this, "Car listing created!");

                // Clear creating form after creating car listing
                modelField.setText("");
                yearField.setText("");
                mileageField.setText("");
                locationField.setText("");
                priceField.setText("");
                availableFromField.setText("");
                availableToField.setText("");
                colorField.setText("");
                transmissionField.setText("");
                descriptionField.setText("");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers for year, mileage, and price.");
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Please enter dates in YYYY-MM-DD format.");
            }
        });

        panel.add(modelLabel);
        panel.add(modelField);
        panel.add(yearLabel);
        panel.add(yearField);
        panel.add(mileageLabel);
        panel.add(mileageField);
        panel.add(locationLabel);
        panel.add(locationField);
        panel.add(priceLabel);
        panel.add(priceField);
        panel.add(availableFromLabel);
        panel.add(availableFromField);
        panel.add(availableToLabel);
        panel.add(availableToField);
        panel.add(colorLabel);
        panel.add(colorField);
        panel.add(transmissionLabel);
        panel.add(transmissionField);
        panel.add(descriptionLabel);
        panel.add(descriptionField);

        add(panel, BorderLayout.CENTER);
        add(createButton, BorderLayout.SOUTH);
    }
}