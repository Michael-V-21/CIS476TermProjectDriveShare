package driveshare.ui;

import driveshare.data.CarStore;
import driveshare.model.Car;
import driveshare.patterns.observer.RenterObserver;
import driveshare.patterns.singleton.SessionManager;

import javax.swing.*;
import java.awt.*;
import java.util.List;

// UI for users to watch car listing
// OBSERVER PATTERN - attaches observer to car

public class WatchCarFrame extends JFrame {

    public WatchCarFrame()
    {
        setTitle("DriveShare - Watch Car");
        setSize(550, 350);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // All cars in system
        JLabel carLabel = new JLabel("Select Car:");
        List<Car> cars = CarStore.getCars();
        JComboBox<Car> carComboBox = new JComboBox<>(cars.toArray(new Car[0]));

        // Observer conditions - target price
        JLabel targetPriceLabel = new JLabel("Target Price:");
        JTextField targetPriceField = new JTextField();

        JButton watchButton = new JButton("Watch Car");

        // Watch logic
        watchButton.addActionListener(e -> {
            Car selectedCar = (Car) carComboBox.getSelectedItem();


            if (selectedCar == null) {
                JOptionPane.showMessageDialog(this, "No car selected.");
                return;
            }


            try {
                double targetPrice = Double.parseDouble(targetPriceField.getText().trim());

                // Valid price
                if (targetPrice <= 0) {
                    JOptionPane.showMessageDialog(this, "Target price must be greater than 0.");
                    return;
                }

                // creat the observer for the logged in user
                String renterEmail = SessionManager.getInstance().getCurrentUser().getEmail();
                RenterObserver observer = new RenterObserver(renterEmail, targetPrice);

                // Attach observer to car
                selectedCar.addObserver(observer);

                JOptionPane.showMessageDialog(this,
                        "You are now watching " + selectedCar.getCarId() +
                                " with a target price of $" + targetPrice + ".");

                targetPriceField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid target price.");
            }
        });

        panel.add(carLabel);
        panel.add(carComboBox);
        panel.add(targetPriceLabel);
        panel.add(targetPriceField);
        panel.add(new JLabel());
        panel.add(watchButton);

        add(panel);
    }
}