package driveshare.patterns.proxy;

// Define operation for payment process
// Subject interface -- PROXY PATTERN

public interface PaymentService
{
    // Process payment and return status
    boolean processPayment(double amount);
}