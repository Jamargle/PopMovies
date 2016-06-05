package com.josecognizant.popmovies;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.josecognizant.popmovies.data.MovieContract.MovieEntry;

/**
 * Fragment for showing details of movies
 * Created by Jose on 26/05/2016.
 */
public class MovieDetailsFragment extends Fragment {

    public static final String DETAIL_URI = "URI";
    private TextView mTitle, mOverView, mReleaseYear, mVoteAverage;
    private ImageView mPoster;
    private Uri mUri;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        getUriFromArguments();
        View rootView = inflater.inflate(R.layout.fragment_movie_details, container, false);
        initializeUIViews(rootView);
        return rootView;
    }

    private void initializeUIViews(View rootView) {
        mTitle = (TextView) rootView.findViewById(R.id.original_movie_title);
        mOverView = (TextView) rootView.findViewById(R.id.overview);
        mReleaseYear = (TextView) rootView.findViewById(R.id.release_year);
        mVoteAverage = (TextView) rootView.findViewById(R.id.vote_average);
        mPoster = (ImageView) rootView.findViewById(R.id.movie_image);
    }

    @Override
    public void onResume() {
        super.onResume();
        Cursor cursor = getActivity().getContentResolver().query(mUri, null, null, null, null);
        if (cursor != null) {
            setUIValues(cursor);
            cursor.close();
        }
    }

    private void getUriFromArguments() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            mUri = arguments.getParcelable(DETAIL_URI);
        }
    }

    private void setUIValues(Cursor data) {
        if (data != null && data.moveToFirst()) {
            setTitle(data.getString(data.getColumnIndex(MovieEntry.COLUMN_TITLE)));
            setOverview(data.getString(data.getColumnIndex(MovieEntry.COLUMN_OVERVIEW)));
            setReleaseYear(data.getString(data.getColumnIndex(MovieEntry.COLUMN_RELEASE_DATE)));
            setVoteAverage(data.getString(data.getColumnIndex(MovieEntry.COLUMN_VOTE_AVERAGE)));
            setMovieImage(data.getString(data.getColumnIndex(MovieEntry.COLUMN_POSTER)));
        }
    }

    private void setTitle(String title) {
        mTitle.setText(title);
    }

    private void setOverview(String overview) {
        mOverView.setText(overview);
    }

    private void setReleaseYear(String releaseDate) {
        mReleaseYear.setText(releaseDate.substring(0, 4));
    }

    private void setVoteAverage(String voteAverage) {
        mVoteAverage.setText(String.valueOf(voteAverage));
    }

    private void setMovieImage(String movieImagePath) {
        Glide.with(getActivity())
                .load(movieImagePath)
                .into(mPoster);
    }
}
