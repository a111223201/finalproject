package auction.listings;
import auction.interfaces.*;
import auction.users.Seller;
import auction.bidding.Bid;
import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class Listing implements Biddable, Expirable {
    protected String id;
    protected Seller seller;
    protected String title;
    protected LocalDateTime endTime;
    protected ArrayList<Bid> history = new ArrayList<>();
    public Listing(String id, Seller s, String t, LocalDateTime et) {
        this.id = id; this.seller = s; this.title = t; this.endTime = et;
    }
    public String getTitle() { return title; }
}