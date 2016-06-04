package com.josecognizant.popmovies.service;

import android.app.IntentService;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.josecognizant.popmovies.BuildConfig;
import com.josecognizant.popmovies.data.MovieContract;
import com.josecognizant.popmovies.model.Movie;
import com.josecognizant.popmovies.util.MovieJsonParserApiClient;
import com.josecognizant.popmovies.util.MovieMapper;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Intent Service to perform downloads in a background service
 * Created by 552702 on 03/06/2016.
 */
public class MoviesIntentService extends IntentService {
    public static final String MOVIE_SELECTED_ORDER = "movie_selected_order";
    public static final String POPULAR_MOVIES_PARAMETER = "popular";
    public static final String TOP_RATED_MOVIES_PARAMETER = "top_rated";
    private static final String BASE_URL = "https://api.themoviedb.org/3/movie";
    private static final String API_KEY_PARAMETER = "api_key";
    private static final String LOG_TAG = MoviesIntentService.class.getSimpleName();

    private HttpURLConnection mUrlConnection = null;
    private BufferedReader mReader = null;


    @SuppressWarnings("unused")
    public MoviesIntentService() {
        super("PopularMovies");
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    @SuppressWarnings("unused")
    public MoviesIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null && intent.hasExtra(MOVIE_SELECTED_ORDER)) {
            String movieOrder = intent.getStringExtra(MOVIE_SELECTED_ORDER);
            URL url = null;
            try {
                if (movieOrder.equals(POPULAR_MOVIES_PARAMETER)) {
                    url = createPopularMovieURL();
                } else if (movieOrder.equals(TOP_RATED_MOVIES_PARAMETER)) {
                    url = createTopRatedMovieURL();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.e(LOG_TAG, "Impossible to create an URL for fetching weather data", e);
            }
            if (url != null) {
                final List<ContentValues> movies = fetchMovieData(url);
                storeMovies(movies);
            }
        }
    }

    private URL createPopularMovieURL() throws MalformedURLException {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(POPULAR_MOVIES_PARAMETER)
                .appendQueryParameter(API_KEY_PARAMETER, BuildConfig.MOVIES_API_KEY)
                .build();
        return new URL(builtUri.toString());

    }

    private URL createTopRatedMovieURL() throws MalformedURLException {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(TOP_RATED_MOVIES_PARAMETER)
                .appendQueryParameter(API_KEY_PARAMETER, BuildConfig.MOVIES_API_KEY)
                .build();
        return new URL(builtUri.toString());

    }

    private List<ContentValues> fetchMovieData(URL url) {
        List<ContentValues> movies = new ArrayList<>();
        String fetchedData;
        try {
            openHttpConnection(url);

            InputStream inputStream = mUrlConnection.getInputStream();
            fetchedData = readDataFromStream(inputStream);

            if (!fetchedData.isEmpty()) try {
                movies = parseFetchedData(fetchedData);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error during fetching weather data", e);
        } finally {
            closeHttpConnection();
            closeReader();
        }

        return movies;
    }

    private void storeMovies(List<ContentValues> movies) {
        if (movies != null && movies.size() > 0) {
            long movieRowId;
            Uri movieURi;
            for (ContentValues movie : movies) {
                movieURi = getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, movie);
                movieRowId = ContentUris.parseId(movieURi);
                if (movieRowId == -1) {
                    // Remove the favorite field to keep the existing value
                    movie.remove(MovieContract.MovieEntry.COLUMN_FAVORITE);
                    movie.remove(MovieContract.MovieEntry.COLUMN_ORDER_TYPE);
                    getContentResolver().update(
                            MovieContract.MovieEntry.CONTENT_URI,
                            movie,
                            MovieContract.MovieEntry._ID + "= ?",
                            new String[]{Long.toString(movieRowId)});
                }
            }
        }
    }

    private void openHttpConnection(URL url) throws IOException {
        mUrlConnection = (HttpURLConnection) url.openConnection();
        mUrlConnection.setRequestMethod("GET");
        mUrlConnection.connect();
    }

    private void closeHttpConnection() {
        if (mUrlConnection != null) {
            mUrlConnection.disconnect();
        }
    }

    private void closeReader() {
        if (mReader != null) {
            try {
                mReader.close();
            } catch (final IOException e) {
                Log.e("PlaceholderFragment", "Error closing stream", e);
            }
        }
    }

    private List<ContentValues> parseFetchedData(String fetchedData) throws JSONException {
        final List<Movie> moviesDataFromJson = MovieJsonParserApiClient.getMoviesDataFromJson(fetchedData);
        return MovieMapper.mapToCV(moviesDataFromJson);
    }

    private String readDataFromStream(InputStream input) throws IOException {
        if (input != null) {
            String line;
            mReader = new BufferedReader(new InputStreamReader(input));
            StringBuilder buffer = new StringBuilder();

            while ((line = mReader.readLine()) != null) {
                buffer.append(line).append("\n");
            }

            return buffer.toString();
        }
        return "";
    }
}
