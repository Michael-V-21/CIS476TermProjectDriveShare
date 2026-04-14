package driveshare.patterns.builder;

import driveshare.model.Car;

import java.time.LocalDate;

// Builder class for Car Objects -- BUILDER PATTERN

public class CarBuilder
{
    // Auto
    private String carId;
    private String ownerEmail;

    // Required
    private String model;
    private int year;
    private int mileage;
    private String location;
    private double pricePerDay;

    // Optional
    private String color = "";
    private String transmission = "";
    private String description = "";

    private LocalDate availableFrom;
    private LocalDate availableTo;



    public CarBuilder setCarId(String carId) {
        this.carId = carId;
        return this;
    }

    public CarBuilder setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
        return this;
    }

    public CarBuilder setModel(String model) {
        this.model = model;
        return this;
    }

    public CarBuilder setYear(int year) {
        this.year = year;
        return this;
    }

    public CarBuilder setMileage(int mileage) {
        this.mileage = mileage;
        return this;
    }

    public CarBuilder setLocation(String location) {
        this.location = location;
        return this;
    }

    public CarBuilder setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
        return this;
    }

    public CarBuilder setColor(String color) {
        this.color = color;
        return this;
    }

    public CarBuilder setTransmission(String transmission) {
        this.transmission = transmission;
        return this;
    }

    public CarBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public CarBuilder setAvailableFrom(LocalDate availableFrom) {
        this.availableFrom = availableFrom;
        return this;
    }

    public CarBuilder setAvailableTo(LocalDate availableTo) {
        this.availableTo = availableTo;
        return this;
    }

    // Builds and returns car object
    public Car build() {
        return new Car(
                carId,
                ownerEmail,
                model,
                year,
                mileage,
                location,
                pricePerDay,
                color,
                transmission,
                description,
                availableFrom,
                availableTo
        );
    }
}