package driveshare.patterns.proxy;

// Actual payment process
// Real subject -- PROXY PATTERN

public class RealPaymentService implements PaymentService
{

    @Override
    public boolean processPayment(double amount) {
        System.out.println("Real payment service processed payment of $" + amount);
        return true;
    }
}