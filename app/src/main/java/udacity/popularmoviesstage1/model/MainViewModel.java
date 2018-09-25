package udacity.popularmoviesstage1.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import udacity.popularmoviesstage1.database.MovieRepository;

public class MainViewModel extends AndroidViewModel {

    private LiveData<List<Movie>> moviesList;

    public MainViewModel(@NonNull Application application) {
        super(application);
        MovieRepository movieRepository = new MovieRepository(getApplication());
        moviesList = movieRepository.getMovieList();
    }

    public LiveData<List<Movie>> getMoviesList() {
        return moviesList;
    }
}
