package com.josecognizant.popmovies.data.local;

import android.content.ContentResolver;
import android.content.Context;

import com.josecognizant.popmovies.domain.model.LocalMovieGateway;
import com.josecognizant.popmovies.domain.model.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of a LocalGateway for retrieve and store resources from the local database
 * Created by Jose on 18/06/2016.
 */
public class LocalMovieGatewayImp implements LocalMovieGateway {
    private final ContentResolver contentResolver;

    public LocalMovieGatewayImp(Context context) {
        contentResolver = context.getContentResolver();
    }

    @Override
    public List<Movie> obtainMovies() {
        List<Movie> movieList = new ArrayList<>();
        return movieList;
    }

    @Override
    public long persist(Movie movieToPersist) {
        return 0;
    }

    @Override
    public long update(List<Movie> moviesToUpdate) {
        return 0;
    }

    @Override
    public void delete(Movie movieToDelete) {

    }
}
