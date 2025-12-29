// BidResult.java
package auction.bidding;

public class BidResult {
    public boolean success;
    public String message;
    public boolean extended;
    public BidResult(boolean s, String m, boolean e) { success = s; message = m; extended = e; }
    @Override public String toString() { return (success ? "[成功] " : "[失敗] ") + message + (extended ? " (時間已延長)" : ""); }
}