// Seller.java
package auction.users;

import auction.core.*;
import auction.interfaces.Rateable;

import java.util.ArrayList;

public class Seller extends User implements Rateable {
    private ArrayList<Rating> ratings = new ArrayList<>();

    public Seller(String userId, String username) {
        super(userId, username);
    }

    @Override
    public void addRating(User rater, int score, String comment) {
        if (score < 1 || score > 5) {
            throw new IllegalArgumentException("Score must be between 1 and 5");
        }
        ratings.add(new Rating(rater, score, comment));
        System.out.println("[評價系統] " + rater.username + " 給予賣家 " + this.username + " " + score + " 顆星");
    }

    @Override
    public double getAverageRating() {
        if (ratings.isEmpty()) return 0.0;
        double sum = 0;
        for (Rating r : ratings) {
            sum += r.getScore();
        }
        return sum / ratings.size();
    }

    @Override
    public int getRatingCount() {
        return ratings.size();
    }
}