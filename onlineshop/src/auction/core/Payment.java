// Payment.java
package auction.core;

public class Payment {
    private String method; // e.g., "Credit Card", "Bank Transfer"
    private double amount;
    private boolean isConfirmed;

    public Payment(String method, double amount) {
        this.method = method;
        this.amount = amount;
        this.isConfirmed = false;
    }
    public void confirm() { this.isConfirmed = true; }
}