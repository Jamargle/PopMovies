package com.josecognizant.popmovies.data.local;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.josecognizant.popmovies.app.util.MovieMapper;
import com.josecognizant.popmovies.data.local.MovieContract.MovieEntry;
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
        final Cursor cursor = contentResolver.query(MovieEntry.CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            movieList = MovieMapper.mapToListOfMovies(cursor);
            cursor.close();
        }
        return movieList;
    }

    @Override
    public void persist(List<Movie> movies) {
        if (movies != null && movies.size() > 0) {

            for (ContentValues movie : MovieMapper.mapToCV(movies)) {
                if (movieIDInDB(movie) > 0) {
                    // Remove the favorite field to keep the existing value
                    movie.remove(MovieEntry.COLUMN_FAVORITE);
                    contentResolver.update(
                            MovieEntry.CONTENT_URI,
                            movie,
                            MovieEntry.COLUMN_TITLE + "= ?",
                            new String[]{movie.getAsString(MovieEntry.COLUMN_TITLE)});
                } else {
                    contentResolver.insert(MovieEntry.CONTENT_URI, movie);
                }
            }
        }
    }

    @Override
    public long update(Movie movieToUpdate) {
        ContentValues movieCV = MovieMapper.mapToCV(movieToUpdate);
        long movieId = movieIDInDB(movieCV);
        if (movieId > 0) {
            return contentResolver.update(
                    MovieEntry.CONTENT_URI,
                    movieCV,
                    MovieEntry.COLUMN_TITLE + "= ?",
                    new String[]{movieCV.getAsString(MovieEntry.COLUMN_TITLE)});
        }
        return movieId;
    }

    @Override
    public void delete(Movie movieToDelete) {
        ContentValues movieCV = MovieMapper.mapToCV(movieToDelete);
        long movieId = movieIDInDB(movieCV);
        if (movieId > 0) {
            contentResolver.delete(
                    MovieEntry.CONTENT_URI,
                    MovieEntry.COLUMN_TITLE + "= ?",
                    new String[]{movieCV.getAsString(MovieEntry.COLUMN_TITLE)});
        }
    }

    private long movieIDInDB(ContentValues movie) {
        long id = -1;
        String[] projection = new String[]{
                MovieEntry._ID,
                MovieEntry.COLUMN_TITLE,
                MovieEntry.COLUMN_ORDER_TYPE};
        String selection = MovieEntry.COLUMN_TITLE + " = ? AND " +
                MovieEntry.COLUMN_ORDER_TYPE + " = ?";
        String[] selectionArgs = new String[]{
                movie.getAsString(MovieEntry.COLUMN_TITLE),
                movie.getAsString(MovieEntry.COLUMN_ORDER_TYPE)};

        final Cursor cursor = contentResolver.query(MovieEntry.CONTENT_URI,
                projection, selection, selectionArgs, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                id = cursor.getLong(cursor.getColumnIndex(MovieEntry._ID));
            }
            cursor.close();
        }
        return id;
    }
}
