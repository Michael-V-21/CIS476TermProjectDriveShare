package driveshare.patterns.mediator;

// Mediator interface for UI
// Actions that UI can trigger

public interface UIMediator
{
    void openCreateCar();
    void openSearchCars();
    void openBookCar();
    void openWatchCar();
    void openManageCar();
    void openPayment();
    void openSendMessage();
    void openInbox();
    void logout();
}