package com.josecognizant.popmovies.app.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.josecognizant.popmovies.R;

/**
 * Class with some helper methods
 * Created by Jose on 03/06/2016.
 */
public class MovieUtilities {

    /**
     * Returns the kind of order selected by the user to show movies
     *
     * @param context Context to access SharedPreferences
     * @return String with the value of the setting established by the user to get movies in a
     * specific order.
     */
    public static String getMovieOrderSetting(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(context.getString(R.string.pref_sorting_model_key),
                context.getString(R.string.pref_sort_by_popular));
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
}
