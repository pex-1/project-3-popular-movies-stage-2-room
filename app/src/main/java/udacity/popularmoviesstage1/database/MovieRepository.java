package udacity.popularmoviesstage1.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import udacity.popularmoviesstage1.model.Movie;

public class MovieRepository {
    private MovieDao movieDao;
    private LiveData<List<Movie>> movieList;

    public MovieRepository(Application application){
        MovieDatabase movieDatabase = MovieDatabase.getInstance(application);
        movieDao = movieDatabase.movieDao();
        movieList = movieDao.getAllMovies();
    }

    public LiveData<List<Movie>> getMovieList() {
        return movieList;
    }


    public void insert(Movie movie){
        new InsertMovieAsyncTask(movieDao).execute(movie);
    }

    public void delete(Movie movie){
        new DeleteMovieAsyncTask(movieDao).execute(movie);
    }



    private static class InsertMovieAsyncTask extends AsyncTask<Movie, Void, Void>{
        private MovieDao movieDao;

        //class is static so we can't access noteDao from our repository directly, so we have to pass it through a constructor
        private InsertMovieAsyncTask(MovieDao movieDao){
            this.movieDao = movieDao;
        }

        @Override
        protected Void doInBackground(Movie... movies) {
            movieDao.insert(movies[0]);
            return null;
        }
    }

    private static class DeleteMovieAsyncTask extends AsyncTask<Movie, Void, Void>{
        private MovieDao movieDao;

        //class is static so we can't access noteDao from our repository directly, so we have to pass it through a constructor
        private DeleteMovieAsyncTask(MovieDao movieDao){
            this.movieDao = movieDao;
        }

        @Override
        protected Void doInBackground(Movie... movies) {
            movieDao.delete(movies[0]);
            Log.e("DELETED" , " MOVIE HAS BEN REMOVED");
            return null;
        }
    }

}
