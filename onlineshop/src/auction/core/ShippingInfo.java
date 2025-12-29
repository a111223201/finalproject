// ShippingInfo.java
package auction.core;

public class ShippingInfo {
    private String address;
    private String trackingNumber;
    private String status; // e.g., "Pending", "Shipped", "Delivered"

    public ShippingInfo(String address) {
        this.address = address;
        this.status = "Pending";
    }
    public void updateStatus(String status, String tracking) {
        this.status = status;
        this.trackingNumber = tracking;
    }
}