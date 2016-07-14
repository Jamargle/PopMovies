package com.josecognizant.popmovies.app.util;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.preference.PreferenceManager;

import com.josecognizant.popmovies.R;
import com.josecognizant.popmovies.data.local.MovieContract;
import com.josecognizant.popmovies.domain.model.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Class with some helper methods
 * Created by Jose on 03/06/2016.
 */
public abstract class MovieUtilities {

    /**
     * Returns the kind of order selected by the user to show movies
     *
     * @param context Context to access SharedPreferences
     * @return String with the value of the setting established by the user to get movies from a
     * specific classification using strings from MovieContract.
     */
    public static String getMovieOrderSetting(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String wayToOrder = prefs.getString(context.getString(R.string.pref_sorting_model_key),
                context.getString(R.string.pref_sort_by_popular));

        if (wayToOrder.equals(context.getString(R.string.pref_sort_by_popular))) {
            return MovieContract.ORDER_BY_POPULAR;
        } else if (wayToOrder.equals(context.getString(R.string.pref_sort_by_rating))) {
            return MovieContract.ORDER_BY_TOPRATED;
        } else if (wayToOrder.equals(context.getString(R.string.pref_show_favorite_movies))) {
            return MovieContract.FAVORITE_MOVIES;
        } else {
            return "";
        }
    }

    /**
     * Check if the stored value of favourite column is true or false
     *
     * @param value Value of COLUMN_FAVORITE for a movie
     * @return True if the movie is stored as favorite or false otherwise
     */
    public static boolean isFavourite(int value) {
        return value == 1;
    }

    public static List<Movie> filterMoviesToShow(List<Movie> movies, Context context) {
        List<Movie> moviesToShow = new ArrayList<>();
        final String movieOrderSetting = getMovieOrderSetting(context);

        for (Movie movie : movies) {
            if (movieOrderSetting.equals(movie.getOrderType())) {
                moviesToShow.add(movie);
            }
        }
        return moviesToShow;
    }

    public static Movie getMovieFromUri(ContentResolver contentResolver, Uri uri) {
        final Cursor movieQuery = contentResolver.query(uri, null, null, null, null);
        if (movieQuery != null) {
            if (movieQuery.moveToFirst()) {
                return MovieMapper.mapToMovie(movieQuery);
            }
            movieQuery.close();
        }
        return null;
    }
}
