package udacity.popularmoviesstage1.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import udacity.popularmoviesstage1.R;
import udacity.popularmoviesstage1.model.TrailerItem;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {
    private ArrayList<TrailerItem> trailerItems;
    private OnItemClickListener listener;

    //interface for click events
    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public static class TrailerViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView textView;


        public TrailerViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewTrailers);
            textView = itemView.findViewById(R.id.textViewTrailers);

            //on click listener
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public TrailerAdapter(ArrayList<TrailerItem> trailerItems){
        this.trailerItems = trailerItems;
    }


    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailers, parent, false);
        TrailerViewHolder trailerViewHolder = new TrailerViewHolder(view, listener);
        return trailerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {
        TrailerItem currentTrailer = trailerItems.get(position);

        holder.imageView.setImageResource(currentTrailer.getImageResource());
        holder.textView.setText(currentTrailer.getTrailerType());
    }

    @Override
    public int getItemCount() {
        return trailerItems.size();
    }
}
