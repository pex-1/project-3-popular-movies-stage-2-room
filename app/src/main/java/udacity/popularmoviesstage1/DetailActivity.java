package udacity.popularmoviesstage1;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import udacity.popularmoviesstage1.adapters.ReviewAdapter;
import udacity.popularmoviesstage1.adapters.TrailerAdapter;
import udacity.popularmoviesstage1.database.MovieDatabase;
import udacity.popularmoviesstage1.database.MovieRepository;
import udacity.popularmoviesstage1.model.MainViewModel;
import udacity.popularmoviesstage1.model.Movie;
import udacity.popularmoviesstage1.model.Review;
import udacity.popularmoviesstage1.model.ReviewList;
import udacity.popularmoviesstage1.model.Trailer;
import udacity.popularmoviesstage1.model.TrailerItem;
import udacity.popularmoviesstage1.model.TrailerList;
import udacity.popularmoviesstage1.utils.RetrofitUtils;


//TODO: use ButterKnife for binding
public class DetailActivity extends AppCompatActivity {
    //movie trailers
    private RecyclerView recyclerViewTrailer;
    private TrailerAdapter adapterTrailers;
    private RecyclerView.LayoutManager layoutManagerTrailers;

    //movie reviews
    private RecyclerView recyclerViewReviews;
    private ReviewAdapter adapterReviews;
    private RecyclerView.LayoutManager layoutManagerReviews;

    public static final String MOVIE_OBJECT = "movieObject";
    private ImageView posterImageView;
    private TextView title;
    private TextView voteAverage;
    private TextView overview;
    private TextView releaseDate;

    //favorite
    private ImageView imageViewFavorite;
    private List<Movie> favoritesList;
    private HashMap<Integer, Movie> hashMap;

    //Room
    private MovieRepository movieRepository;


    //back functionality
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //adding back arrow
        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        movieRepository = new MovieRepository(getApplication());

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        databaseToHashMap();



    }

    private void setUp(){
        final Movie movie = getIntent().getParcelableExtra(MOVIE_OBJECT);

        setupUI(movie);
        //Toast.makeText(this, "Video key: " + movie.getId(), Toast.LENGTH_SHORT).show();

        int video_key = movie.getId();

        if(hashMap != null){
            if(hashMap.get(movie.getId()) != null){
                ArrayList<Review> reviews = new ArrayList<>();
                ArrayList<TrailerItem> trailers = new ArrayList<>();

                jsonToReviews(reviews, movie);
                jsonToTrailers(trailers, movie);

                setTrailerRecyclerView(trailers);
                setReviewsRecyclerView(reviews);
            }else {
                //trailers
                getTrailersJson(video_key, movie);

                //reviews
                getReviewsJson(video_key, movie);
            }
        }else {
            //trailers
            getTrailersJson(video_key, movie);

            //reviews
            getReviewsJson(video_key, movie);
        }




        //favorite
        imageViewFavorite = findViewById(R.id.imageViewFavorite);
        if (hashMap != null) {
            if (hashMap.get(movie.getId()) != null) {
                imageViewFavorite.setTag(R.drawable.favorite);
                imageViewFavorite.setBackgroundResource(R.drawable.favorite);
            } else {
                imageViewFavorite.setTag(R.drawable.not_favorite);
            }
        }

        imageViewFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageViewFavorite.getTag() == null) {
                    imageViewFavorite.setTag(R.drawable.not_favorite);
                }

                if ((Integer) imageViewFavorite.getTag() == R.drawable.not_favorite) {
                    imageViewFavorite.setBackgroundResource(R.drawable.favorite);
                    imageViewFavorite.setTag(R.drawable.favorite);
                    saveFavorite();
                    hashMap.put(movie.getId(), movie);
                    finish();
                } else {
                    imageViewFavorite.setBackgroundResource(R.drawable.not_favorite);
                    imageViewFavorite.setTag(R.drawable.not_favorite);
                    removeFavorite(movie);
                    hashMap.remove(movie.getId());
                    finish();
                }

            }
        });
    }
    private void jsonToReviews(ArrayList<Review> reviews, Movie movie) {
        try {
            JSONObject auth = new JSONObject(hashMap.get(movie.getId()).getAuthor());
            JSONObject cont = new JSONObject(hashMap.get(movie.getId()).getContent());

            JSONArray authors = auth.getJSONArray("author");
            JSONArray content = cont.getJSONArray("content");
            Log.d("Authors", authors.toString());
            for(int i = 0; i<authors.length(); i++){
                Review review = new Review(authors.get(i).toString(), content.get(i).toString());
                reviews.add(review);
                Log.d("Review: ", i + review.getAuthor());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void jsonToTrailers(ArrayList<TrailerItem> trailers, Movie movie) {
        try {
            JSONObject type = new JSONObject(hashMap.get(movie.getId()).getTrailerType());
            JSONObject link = new JSONObject(hashMap.get(movie.getId()).getTrailerLink());

            JSONArray trailerType = type.getJSONArray("trailerType");
            JSONArray trailerLink = link.getJSONArray("trailerLink");

            for(int i = 0; i<type.length(); i++){
                TrailerItem trailer = new TrailerItem(trailerType.get(i).toString(), trailerLink.get(i).toString());
                trailers.add(trailer);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getTrailersJson(int video_key, final Movie movie) {
        Api api = RetrofitUtils.getClient().create(Api.class);

        Call<TrailerList> call = api.getMovieTrailers(video_key, BuildConfig.ApiKey);


        call.enqueue(new Callback<TrailerList>() {
            @Override
            public void onResponse(Call<TrailerList> call, Response<TrailerList> response) {
                List<Trailer> trailers = response.body().getTrailers();
                final ArrayList<TrailerItem> trailerItems = new ArrayList<>();

                ArrayList<String> trailerType = new ArrayList<>();
                ArrayList<String> trailerLink = new ArrayList<>();
                for (int i = 0; i < trailers.size(); i++) {
                    trailerItems.add(new TrailerItem(R.drawable.video, trailers.get(i).getType(), trailers.get(i).getKey()));
                    trailerType.add(trailers.get(i).getType());
                    trailerLink.add(trailers.get(i).getKey());
                }

                //trailers to json
                trailersToJson(trailerLink, trailerType, movie);

                //movie trailer list
                setTrailerRecyclerView(trailerItems);


                adapterTrailers.setOnItemClickListener(new TrailerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        //play video on youtube or select another app to play it
                        String videoLink = Constants.YOUTUBE_BASE_URL + trailerItems.get(position).getLink();

                        Uri uri = Uri.parse(videoLink);
                        uri = Uri.parse("vnd.youtube:" + uri.getQueryParameter("v"));

                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);

                    }
                });

            }

            @Override
            public void onFailure(Call<TrailerList> call, Throwable t) {
                Toast.makeText(DetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void setTrailerRecyclerView(final ArrayList<TrailerItem> trailerItems){
        recyclerViewTrailer = findViewById(R.id.recyclerViewTrailers);
        recyclerViewTrailer.setHasFixedSize(true);     //optimization, size doesn't change
        layoutManagerTrailers = new LinearLayoutManager(DetailActivity.this);
        adapterTrailers = new TrailerAdapter(trailerItems);

        recyclerViewTrailer.setLayoutManager(layoutManagerTrailers);
        recyclerViewTrailer.setAdapter(adapterTrailers);

        adapterTrailers.setOnItemClickListener(new TrailerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //play video on youtube or select another app to play it
                String videoLink = Constants.YOUTUBE_BASE_URL + trailerItems.get(position).getLink();

                Uri uri = Uri.parse(videoLink);
                uri = Uri.parse("vnd.youtube:" + uri.getQueryParameter("v"));

                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });
    }

    private void getReviewsJson(int video_key, final Movie movie) {
        Api apiReviews = RetrofitUtils.getClient().create(Api.class);

        Call<ReviewList> callReviews = apiReviews.getMovieReviews(video_key, BuildConfig.ApiKey);

        callReviews.enqueue(new Callback<ReviewList>() {
            @Override
            public void onResponse(Call<ReviewList> call, Response<ReviewList> response) {
                ArrayList<Review> reviews = response.body().getReviews();

                ArrayList<String> author = new ArrayList<>();
                ArrayList<String> content = new ArrayList<>();
                for (int i = 0; i < reviews.size(); i++) {
                    author.add(reviews.get(i).getAuthor());
                    content.add(reviews.get(i).getContent());
                }

                reviewsToJson(author, content, movie);

                setReviewsRecyclerView(reviews);

            }

            @Override
            public void onFailure(Call<ReviewList> call, Throwable t) {
                Toast.makeText(DetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setReviewsRecyclerView(ArrayList<Review> reviews) {
        recyclerViewReviews = findViewById(R.id.recyclerViewReviews);
        recyclerViewReviews.setHasFixedSize(true);     //optimization, size doesn't change
        layoutManagerReviews = new LinearLayoutManager(DetailActivity.this);
        adapterReviews = new ReviewAdapter(reviews);

        recyclerViewReviews.setLayoutManager(layoutManagerReviews);
        recyclerViewReviews.setAdapter(adapterReviews);
    }

    private void trailersToJson(ArrayList<String> trailerLink, ArrayList<String> trailerType, Movie movie) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("trailerType", new JSONArray(trailerType));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        movie.setTrailerType(jsonObject.toString());

        jsonObject = new JSONObject();
        try {
            jsonObject.put("trailerLink", new JSONArray(trailerLink));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        movie.setTrailerLink(jsonObject.toString());

        //Log.d("Trailer type: ", movie.getTrailerType());
        //Log.d("Trailer link: ", movie.getTrailerLink());
    }

    private void reviewsToJson (ArrayList<String> author, ArrayList<String> content, Movie movie) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("author", new JSONArray(author));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        movie.setAuthor(jsonObject.toString());

        jsonObject = new JSONObject();
        try {
            jsonObject.put("content", new JSONArray(content));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        movie.setContent(jsonObject.toString());

        //Log.d("Trailer type: ", movie.getAuthor());
        //Log.d("Trailer link: ", movie.getContent());
    }

    @SuppressLint("UseSparseArrays")
    private void databaseToHashMap() {
        MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getMoviesList().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                hashMap = new HashMap<>();
                favoritesList = movies;
                if(movies != null){
                    for (int i = 0; i < favoritesList.size(); i++) {
                        hashMap.put(favoritesList.get(i).getId(), favoritesList.get(i));
                    }
                }

                setUp();
            }
        });
    }


    public void setupUI(Movie movie) {
        posterImageView = findViewById(R.id.poster);
        title = findViewById(R.id.title);
        voteAverage = findViewById(R.id.vote_average);
        overview = findViewById(R.id.overview);
        releaseDate = findViewById(R.id.releaseDate);

        Picasso.with(this)
                .load(Constants.HTTPS_IMAGE_TMDB_ORG_T_P_W185 + movie.getPosterPath())
                .resize(185, 278)
                .into(posterImageView);


        title.setText(movie.getTitle());
        voteAverage.setText(movie.getVoteAverage().toString() + "/10");
        releaseDate.setText(movie.getReleaseDate().substring(0, 4));
        overview.setText(movie.getOverview());
    }

    public void saveFavorite() {
        Movie movie = getIntent().getParcelableExtra(MOVIE_OBJECT);

        movieRepository.insert(movie);
        Toast.makeText(this, "added to favorites!", Toast.LENGTH_SHORT).show();
    }

    public void removeFavorite(Movie movie) {
        movieRepository.delete(movie);
        Toast.makeText(this, "removed from favorites!", Toast.LENGTH_SHORT).show();
    }


}
