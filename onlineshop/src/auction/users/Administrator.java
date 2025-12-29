// Administrator.java
package auction.users;

import auction.core.Dispute;
import auction.listings.StandardAuction;

public class Administrator extends User {
    private String employeeId;

    public Administrator(String userId, String username) {
        super(userId, username);
        this.employeeId = employeeId;
    }

    // 規格建議行為：處理爭議
    public void resolveDispute(Dispute dispute, String resolution) {
        dispute.resolve(this, resolution);
        System.out.println("[管理員行動] 爭議已處理：" + resolution);
    }

    // 取消異常拍賣
    public void cancelAuction(StandardAuction auction, String reason) {
        // 實務上這裡會改變 auction 的狀態
        System.out.println("[管理員行動] 拍賣 " + auction.getTitle() + " 已被強制取消。原因：" + reason);
    }
}