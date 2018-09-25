package udacity.popularmoviesstage1.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import udacity.popularmoviesstage1.model.Movie;

@Dao
public interface MovieDao {

    @Insert
    void insert(Movie movie);

    @Delete
    void delete(Movie movie);

    @Query("SELECT * FROM movie_table")
    LiveData<List<Movie>> getAllMovies();

}
