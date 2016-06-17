package com.josecognizant.popmovies.app.ui.details;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.josecognizant.popmovies.BuildConfig;
import com.josecognizant.popmovies.R;
import com.josecognizant.popmovies.data.local.MovieContract.MovieEntry;
import com.josecognizant.popmovies.model.MovieVideos;
import com.josecognizant.popmovies.model.Video;
import com.josecognizant.popmovies.data.network.MovieDbClient;
import com.josecognizant.popmovies.app.util.ServiceGenerator;
import com.josecognizant.popmovies.app.util.VideosAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Fragment for showing details of movies
 * Created by Jose on 26/05/2016.
 */
public class MovieDetailsFragment extends Fragment
        implements VideosAdapter.OnRecyclerViewClickListener,
        LoaderManager.LoaderCallbacks<Cursor> {

    public static final String DETAIL_URI = "URI";
    private static final int DETAIL_LOADER = 0;
    private static final String VIDEO_SITE_YOUTUBE = "YouTube";

    @BindView(R.id.original_movie_title)
    TextView mTitleView;
    @BindView(R.id.overview)
    TextView mOverView;
    @BindView(R.id.release_year)
    TextView mReleaseYear;
    @BindView(R.id.vote_average)
    TextView mVoteAverage;
    @BindView(R.id.movie_image)
    ImageView mPoster;
    @BindView(R.id.rv_trailer_container)
    RecyclerView mVideoRecyclerView;
    @BindView(R.id.mark_as_favorite_button)
    Button mFavoriteButton;

    @OnClick(R.id.mark_as_favorite_button)
    void changeFavoriteState() {
        mFavoriteState = (mFavoriteState == 1) ? 0 : 1;
    }

    private List<Video> mVideoList;
    private VideosAdapter mVideosAdapter;
    private Uri mUri;
    private String mTitle, mOrderType;
    private int mFavoriteState = -1, mApiMovieId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        getUriFromArguments();
        View rootView = inflater.inflate(R.layout.fragment_movie_details, container, false);
        ButterKnife.bind(this, rootView);
        initVideosAdapter();
        initRecyclerView();
        return rootView;
    }

    private void initVideosAdapter() {
        mVideoList = new ArrayList<>();
        mVideosAdapter = new VideosAdapter(mVideoList, this);
    }

    private void initRecyclerView() {
        mVideoRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mVideoRecyclerView.setLayoutManager(layoutManager);
        mVideoRecyclerView.setAdapter(mVideosAdapter);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(DETAIL_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onPause() {
        super.onPause();
        storeCurrentMovieState();
    }

    private void getUriFromArguments() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            mUri = arguments.getParcelable(DETAIL_URI);
        }
    }

    private void setTitle(String title) {
        mTitleView.setText(title);
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

    private void setFavoriteState(int value) {
        mFavoriteState = value;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (mUri != null) {
            // Now create and return a CursorLoader that will take care of
            // creating a Cursor for the data being displayed.
            return new CursorLoader(
                    getActivity(),
                    mUri,
                    null,
                    null,
                    null,
                    null
            );
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data == null || !data.moveToFirst()) {
            return;
        }
        setUIValues(data);
        mTitle = data.getString(data.getColumnIndex(MovieEntry.COLUMN_TITLE));
        mOrderType = data.getString(data.getColumnIndex(MovieEntry.COLUMN_ORDER_TYPE));
        mApiMovieId = data.getInt(data.getColumnIndex(MovieEntry.COLUMN_MOVIE_ID));
        getVideosForTheMovie();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private void setUIValues(Cursor data) {
        if (data != null && data.moveToFirst()) {
            setTitle(data.getString(data.getColumnIndex(MovieEntry.COLUMN_TITLE)));
            setOverview(data.getString(data.getColumnIndex(MovieEntry.COLUMN_OVERVIEW)));
            setReleaseYear(data.getString(data.getColumnIndex(MovieEntry.COLUMN_RELEASE_DATE)));
            setVoteAverage(data.getString(data.getColumnIndex(MovieEntry.COLUMN_VOTE_AVERAGE)));
            setMovieImage(data.getString(data.getColumnIndex(MovieEntry.COLUMN_POSTER)));
            setFavoriteState(data.getInt(data.getColumnIndex(MovieEntry.COLUMN_FAVORITE)));
        }
    }

    private void storeCurrentMovieState() {
        ContentValues currentFavoriteState = new ContentValues();
        currentFavoriteState.put(MovieEntry.COLUMN_FAVORITE, mFavoriteState);
        getActivity().getContentResolver().update(
                MovieEntry.CONTENT_URI,
                currentFavoriteState,
                MovieEntry.COLUMN_TITLE + " = ? AND " + MovieEntry.COLUMN_ORDER_TYPE + " = ?",
                new String[]{mTitle, mOrderType});
    }

    private void getVideosForTheMovie() {
        MovieDbClient client = ServiceGenerator.createService(MovieDbClient.class);
        Call<MovieVideos> call = client.getListOfVideos(mApiMovieId, BuildConfig.MOVIES_API_KEY);

        call.enqueue(new Callback<MovieVideos>() {
            @Override
            public void onResponse(Call<MovieVideos> call, Response<MovieVideos> response) {
                MovieVideos videos = response.body();
                List<Video> videoList = videos.getResults();
                if (videoList != null) {
                    mVideoList = videoList;
                } else {
                    mVideoList = new ArrayList<>();
                }
                mVideosAdapter.changeDataSet(mVideoList);
            }

            @Override
            public void onFailure(Call<MovieVideos> call, Throwable t) {
            }
        });
    }

    @Override
    public void onClick(View view, int position) {
        showThisVideo(mVideoList.get(position));
    }

    private void showThisVideo(Video video) {
        String url = null;
        if (video.getSite().equals(VIDEO_SITE_YOUTUBE)) {
            url = String.format(getString(R.string.base_url_youtube), video.getUrlKey());
        }
        if (url != null && !url.isEmpty()) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            getActivity().startActivity(intent);
        }
    }
}
