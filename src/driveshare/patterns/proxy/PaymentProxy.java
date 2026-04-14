package driveshare.patterns.proxy;

// PROXY CLASS - controls access to real payment
// Supposed to add validation/protection between UI and real payment

public class PaymentProxy implements PaymentService
{

    // References real payment
    private RealPaymentService realPaymentService;

    public PaymentProxy() {
        this.realPaymentService = new RealPaymentService();
    }

    @Override
    public boolean processPayment(double amount)
    {
        // Validate before going to real service
        if (amount <= 0)
        {
            System.out.println("Payment failed: invalid amount.");
            return false;
        }

        System.out.println("Proxy validating payment request...");

        // Send to real service
        return realPaymentService.processPayment(amount);
    }
}