package com.josecognizant.popmovies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * JSON parser for parsing movie data fetched from Moviedb API
 * Created by Jose on 24/05/2016.
 */
public class MovieJsonParserApiClient {
    private static final String RESULTS_PARAMETER = "results";
    private static final String POSTER_PATH_PARAMETER = "poster_path";
    private static final String ORIGINAL_TITLE_PARAMETER = "original_title";
    private static final String OVERVIEW_PARAMETER = "overview";
    private static final String USER_RATING_PARAMETER = "vote_count";
    private static final String RELEASE_DATE_PARAMETER = "release_date";

    private static JSONArray sMovieData;

    /**
     * Take a movie string in JSON Format and extract the data in a List of Movies
     *
     * @return List of Movies with the data from the API
     * @throws JSONException
     */
    public static List<Movie> getMoviesDataFromJson(String jsonData) throws JSONException {
        JSONObject movies = new JSONObject(jsonData);
        sMovieData = movies.getJSONArray(RESULTS_PARAMETER);

        List<Movie> moviesItems = new ArrayList<>();
        for (int i = 0; i < sMovieData.length(); i++) {
            Movie movie = extractMovie(i);
            moviesItems.add(movie);
        }

        return moviesItems;
    }

    private static Movie extractMovie(int index) throws JSONException {
        JSONObject jsonMovie = sMovieData.getJSONObject(index);
        return new Movie.Builder()
                .originalTitle(jsonMovie.getString(ORIGINAL_TITLE_PARAMETER))
                .overview(jsonMovie.getString(OVERVIEW_PARAMETER))
                .thumbnailPosterPath(jsonMovie.getString(POSTER_PATH_PARAMETER))
                .releaseDate(jsonMovie.getString(RELEASE_DATE_PARAMETER))
                .voteAverage(jsonMovie.getString(USER_RATING_PARAMETER)).build();
    }
}
