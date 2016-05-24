package com.josecognizant.popmovies.data.network;

import com.josecognizant.popmovies.domain.model.Movie;

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

    private JSONArray mMovieData;

    public MovieJsonParserApiClient(String jsonData) throws JSONException {
        JSONObject movies = new JSONObject(jsonData);
        mMovieData = movies.getJSONArray(RESULTS_PARAMETER);
    }

    /**
     * Take the complete movie stream in JSON Format and extract the data in a List of Movies
     *
     * @return List of Movies with the data from the API
     * @throws JSONException
     */
    public List<Movie> getMoviesDataFromJson() throws JSONException {

        List<Movie> moviesItems = new ArrayList<>();
        for (int i = 0; i < mMovieData.length(); i++) {
            Movie movie = extractMovie(i);
            moviesItems.add(movie);
        }

        return moviesItems;
    }

    private Movie extractMovie(int index) throws JSONException {
        JSONObject jsonMovie = mMovieData.getJSONObject(index);
        return new Movie.Builder()
                .originalTitle(jsonMovie.getString(ORIGINAL_TITLE_PARAMETER))
                .overview(OVERVIEW_PARAMETER)
                .thumbnailPosterPath(POSTER_PATH_PARAMETER)
                .releaseDate(RELEASE_DATE_PARAMETER)
                .voteAverage(USER_RATING_PARAMETER).build();
    }
}