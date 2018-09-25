package udacity.popularmoviesstage1.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import udacity.popularmoviesstage1.R;
import udacity.popularmoviesstage1.model.Review;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {
    private ArrayList<Review> reviews;

    public static class ReviewViewHolder extends RecyclerView.ViewHolder{
        public TextView author;
        public TextView content;

        public ReviewViewHolder(View itemView){
            super(itemView);
            author = itemView.findViewById(R.id.author);
            content = itemView.findViewById(R.id.content);
        }
    }

    public ReviewAdapter(ArrayList<Review> reviews){
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review, parent, false);
        ReviewViewHolder reviewViewHolder = new ReviewViewHolder(view);
        return reviewViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review currentItem = reviews.get(position);

        holder.author.setText(currentItem.getAuthor());
        holder.content.setText(currentItem.getContent());
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }
}
