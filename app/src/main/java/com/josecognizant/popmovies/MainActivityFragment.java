package com.josecognizant.popmovies;

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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.josecognizant.popmovies.data.MovieContract;
import com.josecognizant.popmovies.data.MovieContract.MovieEntry;
import com.josecognizant.popmovies.service.MoviesDownloadService;
import com.josecognizant.popmovies.util.MovieAdapter;

/**
 * Fragment that handle the view with the list of movies
 */
public class MainActivityFragment extends Fragment
        implements MovieAdapter.OnRecyclerViewClickListener,
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final int MOVIE_LOADER = 0;
    private MovieAdapter mAdapter;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        setHasOptionsMenu(true);
        initAdapter();
        initRecyclerView(rootView);
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.movie_list_fragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                startSettingsActivity();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void startSettingsActivity() {
        Intent intent = new Intent(getActivity(), SettingsActivity.class);
        startActivity(intent);
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
        startRefreshMoviesFromInternetService();
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
                    MovieEntry.buildMovieUri(cursor.getLong(cursor.getColumnIndex(MovieEntry._ID))));
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri uri = getMovieUriToQuery();
        return new CursorLoader(getActivity(),
                uri,
                null,
                null,
                null,
                null);
    }

    private Uri getMovieUriToQuery() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String wayToOrder = prefs.getString(
                getString(R.string.pref_sorting_model_key),
                getString(R.string.pref_sort_by_popular));
        Uri uri = null;
        if (wayToOrder.equals(getString(R.string.pref_sort_by_popular))) {
            uri = MovieEntry.buildPopularMoviesUri();
        } else if (wayToOrder.equals(getString(R.string.pref_sort_by_rating))) {
            uri = MovieEntry.buildTopRatedMoviesUri();
        } else if (wayToOrder.equals(getString(R.string.pref_show_favorite_movies))) {
            uri = MovieEntry.buildFavoriteMoviesUri();
        }
        return uri;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
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
