package udacity.popularmoviesstage1;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import udacity.popularmoviesstage1.model.ReviewList;
import udacity.popularmoviesstage1.model.TrailerList;

public interface Api {
    String BASE_URL = "http://api.themoviedb.org/3/";

    @GET("movie/{movie_id}/videos")
    Call<TrailerList> getMovieTrailers(@Path("movie_id") int id, @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/reviews")
    Call<ReviewList> getMovieReviews(@Path("movie_id") int id, @Query("api_key") String apiKey);
}
