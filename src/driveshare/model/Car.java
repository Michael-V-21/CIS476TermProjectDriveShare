package driveshare.model;

import driveshare.patterns.observer.CarObserver;
import driveshare.patterns.observer.CarSubject;
import driveshare.patterns.observer.RenterObserver;

import java.time.LocalDate;

// Class representing car listings
// BUILDER PATTERN
// Subject in the OBSERVER PATTERN

public class Car
{
    private String carId;
    private String ownerEmail;
    private String model;
    private int year;
    private int mileage;
    private String location;
    private double pricePerDay;

    private String color;
    private String transmission;
    private String description;

    private LocalDate availableFrom;
    private LocalDate availableTo;

    private CarSubject subject;

    public Car(String carId, String ownerEmail, String model, int year, int mileage, String location, double pricePerDay,
               String color, String transmission, String description,
               LocalDate availableFrom, LocalDate availableTo) {
        this.carId = carId;
        this.ownerEmail = ownerEmail;
        this.model = model;
        this.year = year;
        this.mileage = mileage;
        this.location = location;
        this.pricePerDay = pricePerDay;
        this.color = color;
        this.transmission = transmission;
        this.description = description;
        this.availableFrom = availableFrom;
        this.availableTo = availableTo;
        this.subject = new CarSubject();
    }

    public String getCarId() {
        return carId;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public int getMileage() {
        return mileage;
    }

    public String getLocation() {
        return location;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public String getColor() {
        return color;
    }

    public String getTransmission() {
        return transmission;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getAvailableFrom() {
        return availableFrom;
    }

    public LocalDate getAvailableTo() {
        return availableTo;
    }

    // Update car price and tells observer if condition is met
    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;

        // Notify observers
        for (CarObserver observer : subject.getObservers())
        {
            // Notify observers IF conditions met
            if (observer instanceof RenterObserver renterObserver) {
                if (pricePerDay <= renterObserver.getTargetPrice()) {
                    observer.update("The price for " + carId + " (" + model + ") has dropped to $" + pricePerDay +
                            ", which meets your target price.");
                }
            } else {
                observer.update("The price for " + carId + " (" + model + ") has changed to $" + pricePerDay);
            }
        }
    }

    public void setAvailability(LocalDate availableFrom, LocalDate availableTo) {
        this.availableFrom = availableFrom;
        this.availableTo = availableTo;
    }

    public void addObserver(CarObserver observer) {
        subject.addObserver(observer);
    }

    public void removeObserver(CarObserver observer) {
        subject.removeObserver(observer);
    }

    @Override
    public String toString() {
        return carId + " - " + model + " (" + year + ")";
    }
}