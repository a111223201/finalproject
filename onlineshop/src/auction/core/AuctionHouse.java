// AuctionHouse.java
package auction.core;

import auction.interfaces.Biddable;
import auction.listings.StandardAuction;

import java.util.ArrayList;
import java.util.List;

public class AuctionHouse {
    private List<Biddable> activeAuctions = new ArrayList<>();
    private List<Transaction> completedTransactions = new ArrayList<>();

    public void listAuction(Biddable auction) {
        activeAuctions.add(auction);
        System.out.println("[平台] 新商品上架: " + ((StandardAuction)auction).getTitle());
    }

    public List<Biddable> getActiveAuctions() {
        return activeAuctions;
    }

    public void recordTransaction(Transaction tx) {
        completedTransactions.add(tx);
    }
}
