package com.josecognizant.popmovies.app.ui.movies;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.josecognizant.popmovies.R;
import com.josecognizant.popmovies.app.PopMoviesApp;
import com.josecognizant.popmovies.app.dependencies.PresenterFactory;
import com.josecognizant.popmovies.app.ui.movies.adapter.MovieListAdapter;
import com.josecognizant.popmovies.app.util.MovieUtilities;
import com.josecognizant.popmovies.data.local.MovieContract;
import com.josecognizant.popmovies.domain.model.Movie;
import com.josecognizant.popmovies.presentation.movies.MoviesPresenter;
import com.josecognizant.popmovies.presentation.movies.MoviesView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Fragment that handle the view with the list of movies
 */
public class MovieListFragment extends Fragment
        implements MoviesView, MovieListAdapter.OnRecyclerViewClickListener {

    @BindView(R.id.movies_loading)
    ProgressBar mLoading;
    @BindView(R.id.empty_list)
    TextView mErrorsText;

    private MovieListAdapter mAdapter;
    private MoviesPresenter mPresenter;

    private List<Movie> mMovieList;

    public MovieListFragment() {
        mMovieList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        setHasOptionsMenu(true);
        initAdapter();
        initRecyclerView(rootView);
        mPresenter = PresenterFactory.makeMoviesPresenter(this, getActivity());
        initializeInjector((PopMoviesApp) getActivity().getApplication());
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.refreshMovies();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mPresenter.onDetach();
    }

    @Override
    public void onClick(View view, int position) {
        if (mMovieList != null && mMovieList.size() > position) {
            Movie movie = mMovieList.get(position);
            startMovieDetailsActivity(movie);
        }
    }

    @Override
    public void showLoading() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mLoading.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void hideLoading() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mLoading.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void showLoadMoviesError() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mErrorsText.setText(getString(R.string.error_load_movie_list));
                mErrorsText.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void hideMoviesError() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mErrorsText.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void updateMoviesToShow(List<Movie> movies) {
        mMovieList = MovieUtilities.filterMoviesToShow(movies, getActivity());
        mAdapter.changeDataSet(mMovieList);
    }

    private void initAdapter() {
        mAdapter = new MovieListAdapter(mMovieList, getActivity(), this);
    }

    private void initRecyclerView(View rootView) {
        RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.movies_grid_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        mRecyclerView.setAdapter(mAdapter);
    }

    private void startMovieDetailsActivity(Movie movie) {
        ((Callback) getActivity()).onItemSelected(MovieContract.MovieEntry.buildMovieUri(
                movie.getMovieDbId()));
    }

    private void initializeInjector(PopMoviesApp application) {
        DaggerMovieListFragmentComponent.builder()
                .applicationComponent(application.getApplicationComponent())
                .build().inject(this);
    }

    interface Callback {
        void onItemSelected(Uri movieUri);
    }
}
