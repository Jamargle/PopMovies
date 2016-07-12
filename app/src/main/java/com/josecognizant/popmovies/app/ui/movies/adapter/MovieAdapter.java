package com.josecognizant.popmovies.app.ui.movies.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.josecognizant.popmovies.R;
import com.josecognizant.popmovies.data.local.MovieContract;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Custom Adapter for RecyclerView in MovieListFragment
 * Created by Jose on 05/06/2016.
 */
public class MovieAdapter extends CursorRecyclerViewAdapter<MovieAdapter.MovieViewHolder> {
    private static OnRecyclerViewClickListener sItemClickListener;
    private Context mContext;

    public MovieAdapter(Context context, Cursor cursor, OnRecyclerViewClickListener listener) {
        super(cursor);
        sItemClickListener = listener;
        mContext = context;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_movie, parent, false);

        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder viewHolder, Cursor cursor) {
        Glide.with(mContext)
                .load(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER)))
                .into(viewHolder.mMoviePoster);
    }

    public interface OnRecyclerViewClickListener {
        void onClick(View view, int position);
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
