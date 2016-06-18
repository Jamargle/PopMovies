package com.josecognizant.popmovies.app.ui.movies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.josecognizant.popmovies.R;
import com.josecognizant.popmovies.app.dependencies.PresenterFactory;
import com.josecognizant.popmovies.app.ui.movies.adapter.MovieAdapter;
import com.josecognizant.popmovies.app.util.MovieUtilities;
import com.josecognizant.popmovies.data.local.MovieContract;
import com.josecognizant.popmovies.domain.model.Movie;
import com.josecognizant.popmovies.presentation.movies.MoviesPresenter;
import com.josecognizant.popmovies.presentation.movies.MoviesView;
import com.josecognizant.popmovies.service.MoviesDownloadService;

import java.util.List;

/**
 * Fragment that handle the view with the list of movies
 */
public class MainActivityFragment extends Fragment
        implements MoviesView, MovieAdapter.OnRecyclerViewClickListener,
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final int MOVIE_LOADER = 0;
    private MovieAdapter mAdapter;
    private MoviesPresenter mPresenter;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        setHasOptionsMenu(true);
        initAdapter();
        initRecyclerView(rootView);
        mPresenter = PresenterFactory.makeMoviesPresenter(this, getActivity());
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(MOVIE_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        refreshMovies();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.onDetach();
    }

    private void initRecyclerView(View rootView) {
        RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_movie_list);
        mRecyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        mRecyclerView.setAdapter(mAdapter);
    }

    private void initAdapter() {
        mAdapter = new MovieAdapter(getActivity(), null, this);
    }

    private void refreshMovies() {
        mPresenter.onRefreshMovies();
//        startRefreshMoviesFromInternetService();
    }

    private void startRefreshMoviesFromInternetService() {
        Intent intent = new Intent(getActivity(), MoviesDownloadService.class);
        intent.putExtra(MoviesDownloadService.MOVIE_SELECTED_ORDER, getMoviesToShow());
        getActivity().startService(intent);
    }

    private String getMoviesToShow() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String wayToOrder = prefs.getString(
                getString(R.string.pref_sorting_model_key),
                getString(R.string.pref_sort_by_popular));
        if (wayToOrder.equals(getString(R.string.pref_sort_by_popular))) {
            return MovieContract.ORDER_BY_POPULAR;
        } else if (wayToOrder.equals(getString(R.string.pref_sort_by_rating))) {
            return MovieContract.ORDER_BY_TOPRATED;
        } else {
            return "";
        }
    }

    @Override
    public void onClick(View view, int position) {
        Cursor cursor = mAdapter.getCursor();

        if (cursor != null && cursor.moveToPosition(position)) {
            ((Callback) getActivity()).onItemSelected(
                    MovieContract.MovieEntry.buildMovieUri(cursor.getLong(cursor.getColumnIndex(MovieContract.MovieEntry._ID))));
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri uri = MovieUtilities.getMovieUriToQuery(getActivity());
        return new CursorLoader(getActivity(),
                uri,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showLoadMoviesError() {

    }

    @Override
    public void updateMoviesToShow(List<Movie> movies) {

    }

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callback {
        void onItemSelected(Uri dateUri);
    }
}
