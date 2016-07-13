package com.josecognizant.popmovies.app.ui.movies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.josecognizant.popmovies.R;
import com.josecognizant.popmovies.domain.model.Movie;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Custom Adapter for RecyclerView in MovieListFragment using a list of Movies
 * Created by Jose on 13/07/2016.
 */
public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieViewHolder> {

    private static OnRecyclerViewClickListener sItemClickListener;
    private Context mContext;
    private List<Movie> mMovieDataSet;

    public MovieListAdapter(List<Movie> movieList, Context context, OnRecyclerViewClickListener listener) {
        mMovieDataSet = movieList;
        mContext = context;
        sItemClickListener = listener;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MovieViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_movie, parent, false));
    }

    @Override
    public void onBindViewHolder(MovieViewHolder viewHolder, int position) {
        if (mMovieDataSet != null) {
            Glide.with(mContext)
                    .load(mMovieDataSet.get(position).getThumbnailPosterPath())
                    .into(viewHolder.mMoviePoster);
        }
    }

    @Override
    public int getItemCount() {
        return mMovieDataSet.size();
    }

    public void changeDataSet(List<Movie> newDataSet) {
        if (newDataSet != null) {
            mMovieDataSet = newDataSet;
            notifyDataSetChanged();
        }
    }

    public interface OnRecyclerViewClickListener {
        void onClick(View view, int position);
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        @BindView(R.id.image_container)
        ImageView mMoviePoster;

        public MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        @OnClick(R.id.image_container)
        public void onClick(View v) {
            sItemClickListener.onClick(v, getAdapterPosition());
        }
    }

}

