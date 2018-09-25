package udacity.popularmoviesstage1;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import udacity.popularmoviesstage1.model.Movie;

public class GridAdapter extends BaseAdapter {
    private Context context;
    private Movie[] movies;


    public GridAdapter(Context context, Movie[] movies){
        this.movies = movies;
        this.context = context;
    }


    @Override
    public int getCount() {
        return movies.length;
    }

    @Override
    public Object getItem(int i) {
        return movies[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageView;

        if(view == null){
            imageView = new ImageView(context);
            imageView.setAdjustViewBounds(true);
        }
        else {
            imageView = (ImageView) view;
        }

        Picasso.with(context)
                .load(Constants.HTTPS_IMAGE_TMDB_ORG_T_P_W185 + movies[i].getPosterPath())
                .resize(185, 278)
                .placeholder(R.drawable.placeholder_error)
                .error(R.drawable.placeholder_error)
                .into(imageView);

        return imageView;
    }
}
