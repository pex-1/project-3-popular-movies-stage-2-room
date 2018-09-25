package udacity.popularmoviesstage1.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import udacity.popularmoviesstage1.model.Movie;

@Database(entities = Movie.class, version = 8, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {

    private static MovieDatabase instance;

    public abstract MovieDao movieDao();

    public static synchronized MovieDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), MovieDatabase.class, "movie_database")
                            .fallbackToDestructiveMigration()
                            .build();
        }
        return instance;
    }

}
