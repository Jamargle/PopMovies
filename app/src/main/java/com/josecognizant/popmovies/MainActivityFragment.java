package com.josecognizant.popmovies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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
        initRecyclerView(rootView);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        refreshMovies();
    }

    private void initRecyclerView(View rootView) {
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_movie_list);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);
    }

    private void refreshMovies() {
        FetchMoviesTask fetchMoviesTask = new FetchMoviesTask(this);
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
//        String city_code = prefs.getString(getString(R.string.pref_city_id_key),
//                getString(R.string.pref_default_city_id));
//        String unitType = prefs.getString(getString(R.string.pref_unit_list_key),
//                getString(R.string.pref_units_metric));
//        if (unitType.equals(getString(R.string.pref_units_imperial))) {
//            fetchWeatherTask.execute(ForecastUtilities.IMPERIAL_UNITS, city_code);
//        } else if (unitType.equals(getString(R.string.pref_units_metric))) {
//            fetchWeatherTask.execute(ForecastUtilities.METRIC_UNITS, city_code);
//        }
        fetchMoviesTask.execute();
    }

    public void updateAdapterData(List<Movie> newMovies) {
        if (mMovieList == null) {
            mMovieList = new ArrayList<>();
        }
        mMovieList.clear();
        mMovieList.addAll(newMovies);
        if (mAdapter == null) {
            mAdapter = new MovieAdapter(this, mMovieList);
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
        Toast.makeText(getActivity(), "TOUCHED", Toast.LENGTH_SHORT).show();
    }

    private void startMovieDetailsActivity(Movie movie) {
        //TODO: to implement
    }
}
