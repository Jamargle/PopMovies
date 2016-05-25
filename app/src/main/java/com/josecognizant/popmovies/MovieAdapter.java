package com.josecognizant.popmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Custom Adapter for RecyclerView in MainActivityFragment
 * Created by 552702 on 24/05/2016.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private static final String BASE_URL = "http://image.tmdb.org/t/p/";
    private static final String MEDIUM_SIZE = "/w185";
    private static final String BIG_SIZE = "/w342";

    private Context mContext;
    private OnItemClickListener mClickListener;
    private List<Movie> mList;

    public MovieAdapter(OnItemClickListener listener, List<Movie> movies, Context context) {
        mList = movies;
        mClickListener = listener;
        mContext = context;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_movie, parent, false);

        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        String posterPath = mList.get(position).getThumbnailPosterPath();
        Glide.with(mContext)
                .load(BASE_URL + MEDIUM_SIZE + posterPath)
                .into(holder.mMoviePoster);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected ImageView mMoviePoster;

        public MovieViewHolder(View itemView) {
            super(itemView);
            mMoviePoster = (ImageView) itemView.findViewById(R.id.image_container);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mClickListener.onItemClick(v, getAdapterPosition());
        }
    }
}
