package auction.core;

import auction.users.User;

public class Rating {
    private User rater;
    private int score;
    private String comment;

    public Rating(User rater, int score, String comment) {
        this.rater = rater;
        this.score = score;
        this.comment = comment;
    }

    public int getScore() { return score; }
}