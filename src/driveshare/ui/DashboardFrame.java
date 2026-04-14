

package driveshare.ui;

import driveshare.patterns.mediator.DriveShareMediator;
import driveshare.patterns.mediator.UIMediator;
import driveshare.patterns.singleton.SessionManager;

import javax.swing.*;
import java.awt.*;

// Dashboard for DriveShare - displayed after login
// MEDIATOR PATTERN - handle communication between UI components

public class DashboardFrame extends JFrame
{

    public DashboardFrame()
    {

        if (!SessionManager.getInstance().isLoggedIn()) {
            JOptionPane.showMessageDialog(this, "You must log in first.");
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
            dispose();
            return;
        }

        setTitle("DriveShare - Dashboard");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Mediator handles the transitions between UI
        UIMediator mediator = new DriveShareMediator(this);

        JPanel mainPanel = new JPanel(new BorderLayout());

        String email = "Unknown User";
        if (SessionManager.getInstance().getCurrentUser() != null) {
            email = SessionManager.getInstance().getCurrentUser().getEmail();
        }


        JLabel welcomeLabel = new JLabel("Welcome, " + email + "!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 22));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(9, 1, 10, 10));

        // All the buttons for the features
        JButton createCarButton = new JButton("Create Car Listing");
        JButton searchCarsButton = new JButton("Search Cars");
        JButton bookCarButton = new JButton("Book Car");
        JButton watchCarButton = new JButton("Watch Car");
        JButton manageCarButton = new JButton("Manage Car");
        JButton paymentButton = new JButton("Make Payment");
        JButton sendMessageButton = new JButton("Send Message");
        JButton inboxButton = new JButton("Inbox");
        JButton logoutButton = new JButton("Logout");

        // All the button actions are taken care of by the mediator
        createCarButton.addActionListener(e -> mediator.openCreateCar());
        searchCarsButton.addActionListener(e -> mediator.openSearchCars());
        bookCarButton.addActionListener(e -> mediator.openBookCar());
        watchCarButton.addActionListener(e -> mediator.openWatchCar());
        manageCarButton.addActionListener(e -> mediator.openManageCar());
        paymentButton.addActionListener(e -> mediator.openPayment());
        sendMessageButton.addActionListener(e -> mediator.openSendMessage());
        inboxButton.addActionListener(e -> mediator.openInbox());
        logoutButton.addActionListener(e -> mediator.logout());

        buttonPanel.add(createCarButton);
        buttonPanel.add(searchCarsButton);
        buttonPanel.add(bookCarButton);
        buttonPanel.add(watchCarButton);
        buttonPanel.add(manageCarButton);
        buttonPanel.add(paymentButton);
        buttonPanel.add(sendMessageButton);
        buttonPanel.add(inboxButton);
        buttonPanel.add(logoutButton);

        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JPanel headerPanel = new JPanel(new GridLayout(2, 1));
        headerPanel.add(welcomeLabel);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        add(mainPanel);
    }
}