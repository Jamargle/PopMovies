package com.josecognizant.popmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment
        implements MovieAdapter.OnItemClickListener {

    private List<Movie> mMovieList;
    private MovieAdapter mAdapter;
    private RecyclerView mRecyclerView;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        setHasOptionsMenu(true);
        initRecyclerView(rootView);
        return rootView;
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
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_movie_list);
        mRecyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        mRecyclerView.setLayoutManager(gridLayoutManager);
    }

    private void refreshMovies() {
        FetchMoviesTask fetchMoviesTask = new FetchMoviesTask(this);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String wayToSort =
                prefs.getString(getString(R.string.pref_sorting_model_key),
                        getString(R.string.pref_sort_by_popular));
        if (wayToSort.equals(getString(R.string.pref_sort_by_popular))) {
            fetchMoviesTask.execute(FetchMoviesTask.POPULAR_MOVIES_PARAMETER);
        } else if (wayToSort.equals(getString(R.string.pref_sort_by_rating))) {
            fetchMoviesTask.execute(FetchMoviesTask.TOPRATED_MOVIES_PARAMETER);
        }
    }

    public void updateAdapterData(List<Movie> newMovies) {
        if (mMovieList == null) {
            mMovieList = new ArrayList<>();
        }
        mMovieList.clear();
        mMovieList.addAll(newMovies);
        if (mAdapter == null) {
            mAdapter = new MovieAdapter(this, mMovieList, getActivity());
        }
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(View view, int position) {
        if (mMovieList != null && mMovieList.size() > position) {
            Movie movie = mMovieList.get(position);
            startMovieDetailsActivity(movie);
        }
        Toast.makeText(getActivity(), "TOUCHED " + mMovieList.get(position).getOriginalTitle(), Toast.LENGTH_SHORT).show();
    }

    private void startMovieDetailsActivity(Movie movie) {
        //TODO: to implement
    }
}
