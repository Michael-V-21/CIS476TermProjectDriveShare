package driveshare.model;

import java.time.LocalDate;

// Represents a booking
// Dates, cost, payment status

public class Booking
{
    private String carId;
    private String renterEmail;
    private LocalDate startDate;
    private LocalDate endDate;

    private double totalCost;

    private boolean paid;

    public Booking(String carId, String renterEmail, LocalDate startDate, LocalDate endDate, double totalCost) {
        this.carId = carId;
        this.renterEmail = renterEmail;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalCost = totalCost;
        this.paid = false;
    }

    public String getCarId() {
        return carId;
    }

    public String getRenterEmail() {
        return renterEmail;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public boolean isPaid() {
        return paid;
    }

    public void markPaid() {
        this.paid = true;
    }

    @Override
    public String toString() {
        return carId + " | " + startDate + " to " + endDate + " | Due $" + totalCost;
    }

}