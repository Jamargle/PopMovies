package com.josecognizant.popmovies;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.josecognizant.popmovies.model.Movie;

/**
 * Fragment for showing details of movies
 * Created by Jose on 26/05/2016.
 */
public class MovieDetailsFragment extends Fragment {

    public static final String MOVIE_TO_SHOW = "movie_to_show";

    private Movie mMovie = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_details, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        getMovieFromIntent();
        updateMovieToShow();
    }

    private void getMovieFromIntent() {
        if (getActivity().getIntent().hasExtra(MOVIE_TO_SHOW)) {
            mMovie = getActivity().getIntent().getParcelableExtra(MOVIE_TO_SHOW);
        }
    }

    private void updateMovieToShow() {
        if (mMovie == null) {
            return;
        }
        Activity activity = getActivity();
        setTitle((TextView) activity.findViewById(R.id.original_movie_title));
        setOverview((TextView) activity.findViewById(R.id.overview));
        setReleaseYear((TextView) activity.findViewById(R.id.release_year));
        setVoteAverage((TextView) activity.findViewById(R.id.vote_average));
        setMovieImage((ImageView) activity.findViewById(R.id.movie_image));
    }

    private void setTitle(TextView titleTextView) {
        if (titleTextView != null) {
            titleTextView.setText(mMovie.getOriginalTitle());
        }
    }

    private void setOverview(TextView overviewTextView) {
        if (overviewTextView != null) {
            overviewTextView.setText(mMovie.getOverview());
        }
    }

    private void setReleaseYear(TextView releaseYearTextView) {
        if (releaseYearTextView != null) {
            releaseYearTextView.setText(mMovie.getReleaseDate().substring(0, 4));
        }
    }

    private void setVoteAverage(TextView voteAverageTextView) {
        if (voteAverageTextView != null) {
            voteAverageTextView.setText(String.valueOf(mMovie.getVoteAverage()));
        }
    }

    private void setMovieImage(ImageView movieImageView) {
        if (movieImageView != null) {
            Glide.with(getActivity())
                    .load(mMovie.getThumbnailPosterPath())
                    .into(movieImageView);
        }
    }
}
