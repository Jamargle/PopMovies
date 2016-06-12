package com.josecognizant.popmovies.util;


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
    private static final String USER_RATING_PARAMETER = "vote_average";
    private static final String RELEASE_DATE_PARAMETER = "release_date";
    private static final String MOVIE_ID_PARAMETER = "id";
    private static final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/";
    private static final String IMAGE_MEDIUM_SIZE = "/w185";

    private static JSONArray sMovieData;

    /**
     * Take a movie string in JSON Format and extract the data in a List of Movies
     *
     * @param wayToOrder String with the way to order to add it in ContentValues in order to
     *                   separate movies when we will do queries in the database
     * @return List of Movies with the data from the API
     * @throws JSONException
     */
    public static List<Movie> getMoviesDataFromJson(String jsonData, String wayToOrder)
            throws JSONException {
        JSONObject movies = new JSONObject(jsonData);
        sMovieData = movies.getJSONArray(RESULTS_PARAMETER);

        List<Movie> moviesItems = new ArrayList<>();
        for (int i = 0; i < sMovieData.length(); i++) {
            Movie movie = extractMovie(i);
            movie.setOrderType(wayToOrder);
            moviesItems.add(movie);
        }

        return moviesItems;
    }

    private static Movie extractMovie(int index) throws JSONException {
        JSONObject jsonMovie = sMovieData.getJSONObject(index);
        String title = "", overview = "", posterPath = "", releaseDate = "";
        int movieApiId = 0;
        float voteAverage = 0.0f;

        if (jsonMovie.has(MOVIE_ID_PARAMETER)) {
            movieApiId = jsonMovie.getInt(MOVIE_ID_PARAMETER);
        }
        if (jsonMovie.has(ORIGINAL_TITLE_PARAMETER)) {
            title = jsonMovie.getString(ORIGINAL_TITLE_PARAMETER);
        }
        if (jsonMovie.has(OVERVIEW_PARAMETER)) {
            overview = jsonMovie.getString(OVERVIEW_PARAMETER);
        }
        if (jsonMovie.has(POSTER_PATH_PARAMETER)) {
            posterPath = jsonMovie.getString(POSTER_PATH_PARAMETER);
        }
        if (jsonMovie.has(RELEASE_DATE_PARAMETER)) {
            releaseDate = jsonMovie.getString(RELEASE_DATE_PARAMETER);
        }
        if (jsonMovie.has(USER_RATING_PARAMETER)) {
            voteAverage = Float.parseFloat(jsonMovie.getString(USER_RATING_PARAMETER));
        }
        return new Movie.Builder()
                .movieApiId(movieApiId)
                .originalTitle(title)
                .overview(overview)
                .thumbnailPosterPath(BASE_IMAGE_URL + IMAGE_MEDIUM_SIZE + posterPath)
                .releaseDate(releaseDate)
                .voteAverage(voteAverage).build();
    }
}
