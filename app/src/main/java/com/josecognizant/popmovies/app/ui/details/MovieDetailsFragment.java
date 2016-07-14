package com.josecognizant.popmovies.app.ui.details;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.josecognizant.popmovies.app.dependencies.PresenterFactory;
import com.josecognizant.popmovies.app.ui.details.adapter.VideosAdapter;
import com.josecognizant.popmovies.app.util.MovieUtilities;
import com.josecognizant.popmovies.app.util.ServiceGenerator;
import com.josecognizant.popmovies.data.local.MovieContract.MovieEntry;
import com.josecognizant.popmovies.data.network.MovieDbClient;
import com.josecognizant.popmovies.domain.model.Movie;
import com.josecognizant.popmovies.domain.model.MovieVideos;
import com.josecognizant.popmovies.domain.model.Video;
import com.josecognizant.popmovies.presentation.details.DetailPresenter;
import com.josecognizant.popmovies.presentation.details.DetailView;

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
        implements DetailView, VideosAdapter.OnRecyclerViewClickListener {

    public static final String DETAIL_URI = "URI";
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

    private Movie mMovie;
    private String mTitle, mOrderType;
    private int mFavoriteState = -1;
    private List<Video> mVideoList;

    private VideosAdapter mVideosAdapter;
    private DetailPresenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_movie_details, container, false);
        ButterKnife.bind(this, rootView);
        initVideosAdapter();
        initRecyclerView();
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setMovieFromArguments();
        mPresenter = PresenterFactory.makeDetailPresenter(this, mMovie);
        mPresenter.loadMovie();
        getVideosForTheMovie();
    }

    @Override
    public void onPause() {
        super.onPause();
        storeCurrentMovieState();
    }

    @Override
    public void setTitle(String title) {
        mTitleView.setText(title);
    }

    @Override
    public void setOverView(String overview) {
        mOverView.setText(overview);
    }

    @Override
    public void setReleaseYear(String releaseDate) {
        mReleaseYear.setText(releaseDate.substring(0, 4));
    }

    @Override
    public void setVoteAverage(float voteAverage) {
        mVoteAverage.setText(String.valueOf(voteAverage));
    }

    @Override
    public void setMovieImage(String posterPath) {
        Glide.with(getActivity())
                .load(posterPath)
                .into(mPoster);
    }

    @Override
    public void setFavorite(int favorite) {
        mFavoriteState = favorite;
    }

    @OnClick(R.id.mark_as_favorite_button)
    void changeFavoriteState() {
        mFavoriteState = (mFavoriteState == 1) ? 0 : 1;
    }

    private void setMovieFromArguments() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            Uri uri = arguments.getParcelable(DETAIL_URI);
            mMovie = MovieUtilities.getMovieFromUri(getActivity().getContentResolver(), uri);
        }
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
        Call<MovieVideos> call = client.getListOfVideos(mMovie.getMovieApiId(), BuildConfig.MOVIES_API_KEY);

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
