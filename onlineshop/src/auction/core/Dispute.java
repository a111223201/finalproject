package auction.core;

import auction.users.Administrator;

public class Dispute {
    private String reason;
    private boolean isResolved = false;
    private String resolution;
    private Administrator handler;

    public Dispute(String reason) {
        this.reason = reason;
    }

    // 提供給 Administrator 呼叫的方法
    public void resolve(Administrator admin, String resolution) {
        this.handler = admin;
        this.resolution = resolution;
        this.isResolved = true;
    }

    public boolean isResolved() { return isResolved; }
}