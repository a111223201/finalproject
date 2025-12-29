// Biddable.java
package auction.interfaces;
import auction.bidding.Bid;
import auction.bidding.BidResult;
import auction.users.Buyer;
import java.util.ArrayList;

public interface Biddable {
    BidResult placeBid(Buyer buyer, double amount);
    Bid getCurrentHighestBid();
    ArrayList<Bid> getBidHistory();
}
