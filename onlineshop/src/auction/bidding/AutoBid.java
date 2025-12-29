package auction.bidding;
import auction.users.Buyer;

public class AutoBid {
    private Buyer bidder;
    private double maxLimit;
    public AutoBid(Buyer b, double m) { bidder = b; maxLimit = m; }
    public double compete(double curr, double inc) { return (curr + inc <= maxLimit) ? curr + inc : -1; }
    public Buyer getBidder() { return bidder; }
}