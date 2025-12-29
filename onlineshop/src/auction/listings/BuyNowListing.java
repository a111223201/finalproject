package auction.listings;

import auction.users.*;
import auction.bidding.*;
import auction.interfaces.*;
import java.time.LocalDateTime;
import java.time.Duration;
import java.util.ArrayList;

public class BuyNowListing extends Listing {
    private double fixedPrice;
    private Bid winningBid;
    private boolean isSold = false;

    public BuyNowListing(String id, Seller seller, String title, double fixedPrice, LocalDateTime endTime) {
        super(id, seller, title, endTime);
        this.fixedPrice = fixedPrice;
    }

    @Override
    public BidResult placeBid(Buyer buyer, double amount) {
        if (isSold || isExpired()) throw new IllegalStateException("Listing is no longer available");

        if (amount < fixedPrice) {
            throw new IllegalArgumentException("Price must be " + fixedPrice);
        }

        // 修正處：改用具體的 ManualBid 實例化
        this.winningBid = new ManualBid(buyer, fixedPrice, LocalDateTime.now());
        this.isSold = true;
        this.history.add(winningBid);

        return new BidResult(true, "Successfully purchased!", false);
    }

    @Override public boolean isExpired() { return isSold || LocalDateTime.now().isAfter(endTime); }
    @Override public LocalDateTime getEndTime() { return endTime; }
    @Override public Duration getRemainingTime() { return Duration.between(LocalDateTime.now(), endTime); }
    @Override public void extendTime(Duration d) {}
    @Override public Bid getCurrentHighestBid() { return winningBid; }
    @Override public ArrayList<Bid> getBidHistory() { return history; }
}