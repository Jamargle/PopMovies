package com.josecognizant.popmovies.app.ui.details.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.josecognizant.popmovies.R;
import com.josecognizant.popmovies.model.Video;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Custom adapter to fill the list of videos of a movie
 * Created by Jose on 07/06/2016.
 */
public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.VideoViewHolder> {
    private static OnRecyclerViewClickListener sItemClickListener;
    private List<Video> mDataSet;

    public VideosAdapter(List<Video> videos, OnRecyclerViewClickListener listener) {
        sItemClickListener = listener;
        mDataSet = videos;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_video, parent, false);

        return new VideoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        final Video video = mDataSet.get(position);
        holder.mMovieVideo.setText(video.getName());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public void changeDataSet(List<Video> newDataSet) {
        if (newDataSet != null) {
            mDataSet = newDataSet;
            notifyDataSetChanged();
        }
    }

    public interface OnRecyclerViewClickListener {
        void onClick(View view, int position);
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        @BindView(R.id.list_item_trailer_name)
        protected TextView mMovieVideo;

        public VideoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        @OnClick(R.id.list_item_trailer_name)
        public void onClick(View v) {
            sItemClickListener.onClick(v,getAdapterPosition());
        }
    }
}
