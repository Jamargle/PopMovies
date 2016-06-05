package com.josecognizant.popmovies;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Activity with details of movies
 * Created by Jose on 26/05/2016.
 */
public class MovieDetailActivity  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        setupActionBar();
        setMovieAsFragmentArgument();
    }

    private void setMovieAsFragmentArgument() {
        Bundle arguments = new Bundle();
        arguments.putParcelable(MovieDetailsFragment.DETAIL_URI, getIntent().getData());

        Fragment fragment = getFragmentManager().findFragmentById(R.id.movie_details_fragment);
        if (fragment != null) {
            fragment.setArguments(arguments);
        }
    }

    private void setupActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}
