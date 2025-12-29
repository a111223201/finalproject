package auction.interfaces;
import auction.users.User;
import auction.bidding.AuctionEvent;

public interface Watchable {
    void addWatcher(User user);
    void removeWatcher(User user);
    void notifyWatchers(AuctionEvent event);
}