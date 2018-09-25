package udacity.popularmoviesstage1.model;

import udacity.popularmoviesstage1.R;

public class TrailerItem {
    private int imageResource;
    private String trailerType;
    private String link;

    public TrailerItem(int imageResource, String trailerType, String link) {
        this.imageResource = imageResource;
        this.trailerType = trailerType;
        this.link = link;
    }

    public TrailerItem(String trailerType, String link) {
        this.imageResource = R.drawable.video;
        this.trailerType = trailerType;
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public String getTrailerType() {
        return trailerType;
    }

    public void setTrailerType(String trailerType) {
        this.trailerType = trailerType;
    }
}
