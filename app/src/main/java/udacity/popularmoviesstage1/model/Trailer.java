package udacity.popularmoviesstage1.model;

import com.google.gson.annotations.SerializedName;

public class Trailer {
    @SerializedName("type")
    private String type;
    @SerializedName("key")
    private String key;

    public Trailer(String type, String key) {
        this.type = type;
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
