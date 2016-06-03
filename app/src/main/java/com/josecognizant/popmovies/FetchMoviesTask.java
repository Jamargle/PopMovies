package com.josecognizant.popmovies;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.josecognizant.popmovies.model.Movie;
import com.josecognizant.popmovies.util.MovieJsonParserApiClient;

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
 * AsyncTask for downloading information from Moviedb API in background
 * Created by Jose on 25/05/2016.
 */
public class FetchMoviesTask extends AsyncTask<String, Void, List<Movie>> {
    public static final String POPULAR_MOVIES_PARAMETER = "popular";
    public static final String TOPRATED_MOVIES_PARAMETER = "top_rated";
    private static final String BASE_URL = "https://api.themoviedb.org/3/movie";
    private static final String API_KEY_PARAMETER = "api_key";

    private static final String LOG_TAG = FetchMoviesTask.class.getSimpleName();
    private final MainActivityFragment mParent;

    private HttpURLConnection mUrlConnection = null;
    private BufferedReader mReader = null;

    public FetchMoviesTask(MainActivityFragment parent) {
        mParent = parent;
    }

    @Override
    protected List<Movie> doInBackground(String... params) {
        if (params.length > 0) {
            URL url = null;
            try {
                if (params[0].equals(POPULAR_MOVIES_PARAMETER)) {
                    url = createPopularMovieURL();
                } else if (params[0].equals(TOPRATED_MOVIES_PARAMETER)) {
                    url = createTopRatedMovieURL();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.e(LOG_TAG, "Imposible to create an URL for fetching weather data", e);
            }
            if (url != null) {
                return fetchMovieData(url);
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        if (movies != null) {
            super.onPostExecute(movies);
            mParent.updateAdapterData(movies);
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
                .appendPath(TOPRATED_MOVIES_PARAMETER)
                .appendQueryParameter(API_KEY_PARAMETER, BuildConfig.MOVIES_API_KEY)
                .build();
        return new URL(builtUri.toString());

    }

    private List<Movie> fetchMovieData(URL url) {
        List<Movie> movies = new ArrayList<>();
        String fetchedData;
        try {
            openHttpConection(url);

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
            closeHttpConection();
            closeReader();
        }
        return movies;
    }

    private void openHttpConection(URL url) throws IOException {
        mUrlConnection = (HttpURLConnection) url.openConnection();
        mUrlConnection.setRequestMethod("GET");
        mUrlConnection.connect();
    }

    private void closeHttpConection() {
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

    private List<Movie> parseFetchedData(String fetchedData) throws JSONException {
        return MovieJsonParserApiClient.getMoviesDataFromJson(fetchedData);
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
