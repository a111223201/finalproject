package auction.listings;
import auction.users.*;
import auction.bidding.*;
import java.time.LocalDateTime;
import java.time.Duration;
import java.util.ArrayList;

public class DutchAuction extends Listing {
    private double startPrice;
    private double reservePrice; // 最低降價限制
    private Bid winningBid;

    public DutchAuction(String id, Seller s, String t, double startP, double reserve, LocalDateTime et) {
        super(id, s, t, et);
        this.startPrice = startP;
        this.reservePrice = reserve;
    }

    // 計算當前動態價格
    public double getCurrentPrice() {
        if (isExpired()) return reservePrice;
        long totalSec = Duration.between(LocalDateTime.now().minusSeconds(1), endTime).getSeconds();
        if (totalSec <= 0) return reservePrice;

        // 簡單線性降價邏輯
        double priceRange = startPrice - reservePrice;
        return reservePrice + priceRange * 0.5; // 此處可根據需求改為更複雜的時間比例公式
    }

    @Override
    public BidResult placeBid(Buyer buyer, double amount) {
        if (winningBid != null || isExpired()) throw new IllegalStateException("拍賣已結束或已售出");

        // 荷蘭式拍賣通常是「即刻成交」
        winningBid = new ManualBid(buyer, amount, LocalDateTime.now());
        history.add(winningBid);
        return new BidResult(true, "恭喜！您已成功標得商品", false);
    }

    @Override public boolean isExpired() { return winningBid != null || LocalDateTime.now().isAfter(endTime); }
    @Override public LocalDateTime getEndTime() { return endTime; }
    @Override public Duration getRemainingTime() { return Duration.between(LocalDateTime.now(), endTime); }
    @Override public void extendTime(Duration d) { /* 荷蘭式通常不延長 */ }
    @Override public Bid getCurrentHighestBid() { return winningBid; }
    @Override public ArrayList<Bid> getBidHistory() { return history; }
}