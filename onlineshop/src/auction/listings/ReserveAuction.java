package auction.listings;

import auction.users.*;
import auction.bidding.*;
import java.time.LocalDateTime;

public class ReserveAuction extends StandardAuction {
    private double reservePrice;

    public ReserveAuction(String id, Seller s, String t, double startP, double inc, double reserve, LocalDateTime et) {
        super(id, s, t, startP, inc, et);
        this.reservePrice = reserve;
    }

    /**
     * 修正邏輯：
     * 1. 使用 getCurrentHighestBid() 獲取最新出價。
     * 2. 判斷金額是否大於等於底價。
     */
    public boolean isSuccessfullySold() {
        Bid highest = getCurrentHighestBid(); // 從父類獲取最高出價
        if (highest == null) return false;

        System.out.println("[偵錯] 目前最高價: " + highest.getAmount() + " / 底價: " + reservePrice);
        return highest.getAmount() >= reservePrice;
    }

    public double getReservePrice() {
        return reservePrice;
    }
}