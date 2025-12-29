package auction.listings;

import auction.users.*;
import auction.bidding.*;
import auction.interfaces.*;
import java.time.*;
import java.util.ArrayList;

public class StandardAuction extends Listing implements Watchable {
    private double currentPrice;
    private double minIncrement;
    private Bid highestBid;
    private AutoBid activeAutoBid;
    private ArrayList<User> watchers = new ArrayList<>();

    public StandardAuction(String id, Seller s, String t, double p, double inc, LocalDateTime et) {
        super(id, s, t, et);
        this.currentPrice = p;
        this.minIncrement = inc;
    }

    @Override
    public BidResult placeBid(Buyer buyer, double amount) {
        if (isExpired()) throw new IllegalStateException("拍賣已結束");

        double minReq = (highestBid == null) ? currentPrice : highestBid.getAmount() + minIncrement;
        if (amount < minReq) throw new IllegalArgumentException("出價不足 " + minReq);

        highestBid = new ManualBid(buyer, amount, LocalDateTime.now());
        history.add(highestBid);

        // 使用 AuctionEvent 進行通知
        notifyWatchers(new AuctionEvent(
                AuctionEvent.EventType.BID_PLACED,
                this.title,
                "新出價 $" + amount + " (" + buyer.username + ")"
        ));

        boolean ext = false;
        if (getRemainingTime().toMinutes() < 5) {
            extendTime(Duration.ofMinutes(5));
            ext = true;
            notifyWatchers(new AuctionEvent(AuctionEvent.EventType.TIME_EXTENDED, this.title, "拍賣時間已延長 5 分鐘"));
        }

        if (activeAutoBid != null && !buyer.userId.equals(activeAutoBid.getBidder().userId)) {
            double counter = activeAutoBid.compete(amount, minIncrement);
            if (counter != -1) {
                System.out.println("[自動出價反擊]");
                placeBid(activeAutoBid.getBidder(), counter);
            }
        }
        return new BidResult(true, "出價成功", ext);
    }

    // Watchable 實作
    @Override public void addWatcher(User u) { watchers.add(u); }
    @Override public void removeWatcher(User u) { watchers.remove(u); }
    @Override public void notifyWatchers(AuctionEvent event) {
        watchers.forEach(w -> System.out.println("通知 " + w.username + ": " + event));
    }

    // Listing / Expirable 實作
    @Override public boolean isExpired() { return LocalDateTime.now().isAfter(endTime); }
    @Override public LocalDateTime getEndTime() { return endTime; }
    @Override public Duration getRemainingTime() { return Duration.between(LocalDateTime.now(), endTime); }
    @Override public void extendTime(Duration d) { endTime = endTime.plus(d); }
    @Override public Bid getCurrentHighestBid() { return highestBid; }
    @Override public ArrayList<Bid> getBidHistory() { return history; }
    public void setAutoBid(AutoBid ab) { this.activeAutoBid = ab; }
}