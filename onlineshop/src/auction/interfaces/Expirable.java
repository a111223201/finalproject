// Expirable.java
package auction.interfaces;
import java.time.LocalDateTime;
import java.time.Duration;

public interface Expirable {
    LocalDateTime getEndTime();
    boolean isExpired();
    void extendTime(Duration extension);
    Duration getRemainingTime();
}