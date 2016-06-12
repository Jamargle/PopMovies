package com.josecognizant.popmovies.util;

import android.content.ContentValues;

import com.josecognizant.popmovies.data.local.MovieContract.MovieEntry;
import com.josecognizant.popmovies.domain.model.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper class to translate a Movie to a ContentValues object and viceversa
 * Created by Jose on 03/06/2016.
 */
public class MovieMapper {
    public static ContentValues mapToCV(Movie movie) {
        ContentValues movieContentValues = new ContentValues();
        if (movie != null) {
            movieContentValues.put(MovieEntry.COLUMN_MOVIE_ID, movie.getMovieApiId());
            movieContentValues.put(MovieEntry.COLUMN_TITLE, movie.getOriginalTitle());
            movieContentValues.put(MovieEntry.COLUMN_POSTER, movie.getThumbnailPosterPath());
            movieContentValues.put(MovieEntry.COLUMN_OVERVIEW, movie.getOverview());
            movieContentValues.put(MovieEntry.COLUMN_VOTE_AVERAGE, movie.getVoteAverage());
            movieContentValues.put(MovieEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate());
            movieContentValues.put(MovieEntry.COLUMN_ORDER_TYPE, movie.getOrderType());
            movieContentValues.put(MovieEntry.COLUMN_FAVORITE, movie.getFavorite());
        }
        return movieContentValues;
    }

    public static List<ContentValues> mapToCV(List<Movie> movieList) {
        List<ContentValues> contentValuesList = new ArrayList<>();
        if (movieList != null) {
            for (Movie movie : movieList) {
                contentValuesList.add(mapToCV(movie));
            }
        }
        return contentValuesList;
    }

    public static Movie mapToMovie(ContentValues movieValues) {
        if (movieValues != null) {
            return new Movie.Builder()
                    .movieApiId(movieValues.getAsInteger(MovieEntry.COLUMN_MOVIE_ID))
                    .originalTitle(movieValues.getAsString(MovieEntry.COLUMN_TITLE))
                    .thumbnailPosterPath(movieValues.getAsString(MovieEntry.COLUMN_POSTER))
                    .overview(movieValues.getAsString(MovieEntry.COLUMN_OVERVIEW))
                    .voteAverage(movieValues.getAsFloat(MovieEntry.COLUMN_VOTE_AVERAGE))
                    .releaseDate(movieValues.getAsString(MovieEntry.COLUMN_RELEASE_DATE))
                    .orderType(movieValues.getAsString(MovieEntry.COLUMN_ORDER_TYPE))
                    .favorite(movieValues.getAsInteger(MovieEntry.COLUMN_FAVORITE))
                    .build();
        }
        return null;
    }

    public static List<Movie> mapToMovie(List<ContentValues> movieValuesList) {
        List<Movie> movieList = new ArrayList<>();
        if (movieValuesList != null) {
            for (ContentValues movieValues : movieValuesList) {
                movieList.add(mapToMovie(movieValues));
            }
        }
        return movieList;
    }
}
