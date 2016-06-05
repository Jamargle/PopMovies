package com.josecognizant.popmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity
        implements MainActivityFragment.Callback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onItemSelected(Uri dateUri) {
        showMovieDetails(dateUri);
    }

    private void showMovieDetails(Uri uri) {
        Intent intent = new Intent(this, MovieDetailActivity.class).setData(uri);
        startActivity(intent);
    }
}
