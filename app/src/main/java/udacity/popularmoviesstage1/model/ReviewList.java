package udacity.popularmoviesstage1.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ReviewList {
    @SerializedName("id")
    private int id;
    @SerializedName("results")
    private ArrayList<Review> reviews;

    public ReviewList(int id, ArrayList<Review> reviews) {
        this.id = id;
        this.reviews = reviews;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }
}
