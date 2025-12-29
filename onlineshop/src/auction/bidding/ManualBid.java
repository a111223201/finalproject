// ManualBid.java (手動出價)
package auction.bidding;
import auction.users.Buyer;
import java.time.LocalDateTime;

public class ManualBid extends Bid {
    public ManualBid(Buyer b, double a, LocalDateTime t) { super(b, a, t); }
    @Override public String getBidType() { return "Manual"; }
}