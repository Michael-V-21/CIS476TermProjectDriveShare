package driveshare.patterns.mediator;

import driveshare.patterns.singleton.SessionManager;
import driveshare.ui.BookCarFrame;
import driveshare.ui.CreateCarFrame;
import driveshare.ui.LoginFrame;
import driveshare.ui.PaymentFrame;
import driveshare.ui.SearchCarsFrame;
import driveshare.ui.ManageCarFrame;
import driveshare.ui.WatchCarFrame;
import driveshare.ui.SendMessageFrame;
import driveshare.ui.InboxFrame;

import javax.swing.*;

// CONCRETE MEDIATOR -- MEDIATOR PATTER
// Place where UI components communicate from

public class DriveShareMediator implements UIMediator
{

    private JFrame currentFrame;

    public DriveShareMediator(JFrame currentFrame) {
        this.currentFrame = currentFrame;
    }

    // Each method opens a UI screen

    @Override
    public void openCreateCar() {
        CreateCarFrame frame = new CreateCarFrame();
        frame.setVisible(true);
    }

    @Override
    public void openSearchCars() {
        SearchCarsFrame frame = new SearchCarsFrame();
        frame.setVisible(true);
    }

    @Override
    public void openBookCar() {
        BookCarFrame frame = new BookCarFrame();
        frame.setVisible(true);
    }

    @Override
    public void openWatchCar() {
        WatchCarFrame frame = new WatchCarFrame();
        frame.setVisible(true);
    }

    @Override
    public void openManageCar() {
        ManageCarFrame frame = new ManageCarFrame();
        frame.setVisible(true);
    }

    @Override
    public void openPayment() {
        PaymentFrame frame = new PaymentFrame();
        frame.setVisible(true);
    }

    @Override
    public void openSendMessage() {
        SendMessageFrame frame = new SendMessageFrame();
        frame.setVisible(true);
    }

    @Override
    public void openInbox() {
        InboxFrame frame = new InboxFrame();
        frame.setVisible(true);
    }

    @Override
    public void logout() {
        SessionManager.getInstance().logout();
        LoginFrame loginFrame = new LoginFrame();
        loginFrame.setVisible(true);
        currentFrame.dispose();
    }
}