// AuctionEvent.java (簡單化處理)
package auction.bidding;

import java.time.LocalDateTime;

public class AuctionEvent {
    public enum EventType { BID_PLACED, TIME_EXTENDED, OUTBID, AUCTION_ENDED }

    private EventType type;
    private String message;
    private LocalDateTime timestamp;
    private String listingTitle;

    public AuctionEvent(EventType type, String listingTitle, String message) {
        this.type = type;
        this.listingTitle = listingTitle;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return String.format("[%s] 商品 <%s>: %s", type, listingTitle, message);
    }
}