package driveshare.ui;

import driveshare.data.BookingStore;
import driveshare.data.CarStore;
import driveshare.model.Car;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

// UI screen for searching for cars
// Date, location, price

public class SearchCarsFrame extends JFrame
{
    // Filter
    private JTextField locationField;
    private JTextField startDateField;
    private JTextField endDateField;
    private JTextField maxPriceField;

    private JTextArea resultsArea;

    public SearchCarsFrame()
    {
        setTitle("DriveShare - Search Cars");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel filterPanel = new JPanel(new GridLayout(5, 2, 10, 10));

        JLabel locationLabel = new JLabel("Location:");
        locationField = new JTextField();

        JLabel startDateLabel = new JLabel("Start Date (YYYY-MM-DD):");
        startDateField = new JTextField();

        JLabel endDateLabel = new JLabel("End Date (YYYY-MM-DD):");
        endDateField = new JTextField();

        JLabel maxPriceLabel = new JLabel("Max Price Per Day:");
        maxPriceField = new JTextField();

        JButton applyFiltersButton = new JButton("Apply Filters");
        JButton showAllButton = new JButton("Show All Cars");

        // Filter components
        filterPanel.add(locationLabel);
        filterPanel.add(locationField);
        filterPanel.add(startDateLabel);
        filterPanel.add(startDateField);
        filterPanel.add(endDateLabel);
        filterPanel.add(endDateField);
        filterPanel.add(maxPriceLabel);
        filterPanel.add(maxPriceField);
        filterPanel.add(applyFiltersButton);
        filterPanel.add(showAllButton);

        resultsArea = new JTextArea();
        resultsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultsArea);

        applyFiltersButton.addActionListener(e -> applyFilters());

        showAllButton.addActionListener(e -> {
            // Filter reset
            locationField.setText("");
            startDateField.setText("");
            endDateField.setText("");
            maxPriceField.setText("");
            displayCars(CarStore.getCars());
        });

        mainPanel.add(filterPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);

        // Show all cars -- no filters
        displayCars(CarStore.getCars());
    }

    private void applyFilters() {
        try {
            String location = locationField.getText().trim().toLowerCase();
            String startText = startDateField.getText().trim();
            String endText = endDateField.getText().trim();
            String maxPriceText = maxPriceField.getText().trim();

            LocalDate requestedStart = null;
            LocalDate requestedEnd = null;
            Double maxPrice = null;

            if (!startText.isEmpty()) {
                requestedStart = LocalDate.parse(startText);
            }

            if (!endText.isEmpty()) {
                requestedEnd = LocalDate.parse(endText);
            }

            if (!maxPriceText.isEmpty()) {
                maxPrice = Double.parseDouble(maxPriceText);
                if (maxPrice <= 0) {
                    JOptionPane.showMessageDialog(this, "Max price must be greater than 0.");
                    return;
                }
            }

            // Valid filter dates
            if ((requestedStart != null && requestedEnd == null) ||
                    (requestedStart == null && requestedEnd != null)) {
                JOptionPane.showMessageDialog(this, "Please enter both start and end dates.");
                return;
            }

            if (requestedStart != null && requestedEnd != null && requestedEnd.isBefore(requestedStart)) {
                JOptionPane.showMessageDialog(this, "End date cannot be before start date.");
                return;
            }

            StringBuilder sb = new StringBuilder();
            int matchCount = 0;

            // Apply filters to all cars
            for (Car car : CarStore.getCars())
            {
                boolean matches = true;

                if (!location.isEmpty() &&
                        !car.getLocation().toLowerCase().contains(location)) {
                    matches = false;
                }

                if (maxPrice != null && car.getPricePerDay() > maxPrice) {
                    matches = false;
                }

                // Date + availability + double booking filter
                if (requestedStart != null && requestedEnd != null) {
                    boolean withinAvailability =
                            !requestedStart.isBefore(car.getAvailableFrom()) &&
                                    !requestedEnd.isAfter(car.getAvailableTo());

                    boolean alreadyBooked =
                            BookingStore.isCarBooked(car.getCarId(), requestedStart, requestedEnd);

                    if (!withinAvailability || alreadyBooked) {
                        matches = false;
                    }
                }

                if (matches) {
                    appendCar(sb, car);
                    matchCount++;
                }
            }

            if (matchCount == 0) {
                resultsArea.setText("No matching available cars found.");
            } else {
                resultsArea.setText(sb.toString());
            }

        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Please enter dates in YYYY-MM-DD format.");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid max price.");
        }
    }

    private void displayCars(List<Car> cars) {
        if (cars.isEmpty()) {
            resultsArea.setText("No cars available.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (Car car : cars) {
            appendCar(sb, car);
        }

        resultsArea.setText(sb.toString());
    }

    // Helper method for display
    private void appendCar(StringBuilder sb, Car car) {
        sb.append("Car ID: ").append(car.getCarId()).append("\n");
        sb.append("Owner: ").append(car.getOwnerEmail()).append("\n");
        sb.append("Model: ").append(car.getModel()).append("\n");
        sb.append("Year: ").append(car.getYear()).append("\n");
        sb.append("Mileage: ").append(car.getMileage()).append("\n");
        sb.append("Location: ").append(car.getLocation()).append("\n");
        sb.append("Price Per Day: $").append(car.getPricePerDay()).append("\n");
        sb.append("Available From: ").append(car.getAvailableFrom()).append("\n");
        sb.append("Available To: ").append(car.getAvailableTo()).append("\n");
        sb.append("Color: ").append(car.getColor()).append("\n");
        sb.append("Transmission: ").append(car.getTransmission()).append("\n");
        sb.append("Description: ").append(car.getDescription()).append("\n");
        sb.append("--------------------------------------------------\n");
    }
}