// Bid.java (基礎類別)
package auction.bidding;
import auction.users.Buyer;
import java.time.LocalDateTime;

public abstract class Bid {
    protected Buyer bidder;
    protected double amount;
    protected LocalDateTime bidTime;
    public Bid(Buyer b, double a, LocalDateTime t) { bidder = b; amount = a; bidTime = t; }
    public double getAmount() { return amount; }
    public Buyer getBidder() { return bidder; }
    public abstract String getBidType();
}