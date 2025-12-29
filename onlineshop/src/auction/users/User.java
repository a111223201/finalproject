// User.java
package auction.users;

public abstract class User {
    public String userId;
    public String username;
    public User(String id, String name) { this.userId = id; this.username = name; }
}
