package udacity.popularmoviesstage1.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrailerList {
    @SerializedName("id")
    private int movieId;
    @SerializedName("results")
    private List<Trailer> trailers;

    public TrailerList(int movieId, List<Trailer> trailers) {
        this.movieId = movieId;
        this.trailers = trailers;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public List<Trailer> getTrailers() {
        return trailers;
    }

    public void setTrailers(List<Trailer> trailers) {
        this.trailers = trailers;
    }
}
