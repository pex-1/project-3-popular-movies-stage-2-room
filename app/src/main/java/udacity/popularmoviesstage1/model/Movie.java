package udacity.popularmoviesstage1.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * implementation of parcelable: https://www.techjini.com/blog/passing-objects-via-intent-in-android/
 * serialized - slowest approach
 */

@Entity(tableName = "movie_table")
public class Movie implements Parcelable {

    @PrimaryKey(autoGenerate = true)

    private int id;
    private String title;
    private String posterPath;
    private Double voteAverage;
    private String overview;
    private String releaseDate;
    //trailer
    private String trailerType;
    private String trailerLink;
    //review
    private String author;
    private String content;



    public String getTrailerType() {
        return trailerType;
    }

    public void setTrailerType(String trailerType) {
        this.trailerType = trailerType;
    }

    public String getTrailerLink() {
        return trailerLink;
    }

    public void setTrailerLink(String trailerLink) {
        this.trailerLink = trailerLink;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Ignore
    public Movie(){ }

    public Movie(String title, String posterPath, Double voteAverage, String overview, String releaseDate, int id){
        this.title = title;
        this.overview = overview;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
        this.id = id;
    }

    @Ignore
    // In constructor you will read the variables from Parcel. Make sure to read them in the same sequence in which you have written them in Parcel.
    protected Movie(Parcel in) {
        title = in.readString();
        overview = in.readString();
        posterPath = in.readString();
        releaseDate = in.readString();
        if (in.readByte() == 0) {
            voteAverage = null;
        } else {
            voteAverage = in.readDouble();
        }
        id = in.readInt();

    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(overview);
        parcel.writeString(posterPath);
        parcel.writeString(releaseDate);
        parcel.writeValue(voteAverage);
        parcel.writeInt(id);
    }
}
