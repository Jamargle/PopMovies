package data;

import android.content.UriMatcher;
import android.net.Uri;
import android.test.AndroidTestCase;

import com.josecognizant.popmovies.data.MovieContract;
import com.josecognizant.popmovies.data.MovieProvider;

/**
 * Tests for the UriMather class
 * Created by Jose on 03/06/2016.
 */
public class TestUriMatcher extends AndroidTestCase {
    private static final Uri TEST_MOVIE_DIR = MovieContract.MovieEntry.CONTENT_URI;
    private static final Uri TEST_MOVIE_ORDER_BY_POPULAR = MovieContract.MovieEntry.buildPopularMoviesUri();
    private static final Uri TEST_MOVIE_ORDER_BY_RATING = MovieContract.MovieEntry.buildTopRatedMoviesUri();
    private static final Uri TEST_MOVIE_FAVORITES = MovieContract.MovieEntry.buildFavoriteMoviesUri();

    public void testUriMatcher() {
        UriMatcher testMatcher = MovieProvider.buildUriMatcher();

        assertEquals("Error: The MOVIE URI was matched incorrectly.",
                testMatcher.match(TEST_MOVIE_DIR), MovieProvider.MOVIES);
        assertEquals("Error: MOVIES ORDERED BY POPULARITY URI was matched incorrectly.",
                testMatcher.match(TEST_MOVIE_ORDER_BY_POPULAR), MovieProvider.MOVIES_ORDER_BY_POPULAR);
        assertEquals("Error: MOVIES ORDERED BY RATING URI was matched incorrectly.",
                testMatcher.match(TEST_MOVIE_ORDER_BY_RATING), MovieProvider.MOVIES_ORDER_BY_RATING);
        assertEquals("Error: FAVORITE MOVIES URI was matched incorrectly.",
                testMatcher.match(TEST_MOVIE_FAVORITES), MovieProvider.MOVIES_FAVORITES);
    }
}
