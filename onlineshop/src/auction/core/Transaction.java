package auction.core;

import auction.bidding.Bid;
import auction.users.Buyer;
import java.time.LocalDateTime;

/**
 * Transaction: 成交紀錄類別
 * 負責記錄拍賣結束後的最終成交狀態，包括買家、金額、付款與物流資訊。
 */
public class Transaction {
    private String transactionId;
    private Bid winningBid;
    private Buyer buyer;
    private double finalAmount;
    private LocalDateTime transactionDate;

    // 關聯到付款與運送資訊
    private Payment payment;
    private ShippingInfo shipping;

    /**
     * 建構子：用於建立成交紀錄
     * 修正 Demo 中 "new Transaction(id, winBid)" 的報錯
     */
    public Transaction(String id, Bid winningBid) {
        this.transactionId = id;
        this.winningBid = winningBid;
        this.buyer = winningBid.getBidder();
        this.finalAmount = winningBid.getAmount();
        this.transactionDate = LocalDateTime.now();
    }

    // 設定付款資訊 (用於 Demo 第 131 行)
    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    // 設定運送資訊 (用於 Demo 第 136 行)
    public void setShipping(ShippingInfo shipping) {
        this.shipping = shipping;
    }

    // Getter 方法 (供後續查詢使用)
    public String getTransactionId() { return transactionId; }
    public double getFinalAmount() { return finalAmount; }
    public Buyer getBuyer() { return buyer; }
    public LocalDateTime getTransactionDate() { return transactionDate; }
    public Payment getPayment() { return payment; }
    public ShippingInfo getShipping() { return shipping; }

    @Override
    public String toString() {
        return "交易編號: " + transactionId +
                " | 買家: " + buyer.username +
                " | 成交價: " + finalAmount +
                " | 時間: " + transactionDate;
    }
}