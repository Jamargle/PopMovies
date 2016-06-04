package data;

import android.net.Uri;
import android.test.AndroidTestCase;

import com.josecognizant.popmovies.data.MovieContract;
import com.josecognizant.popmovies.data.MovieContract.MovieEntry;

/**
 * Tests for MovieContract class
 * Created by Jose on 03/06/2016.
 */
public class TestMovieContract extends AndroidTestCase {

    public void testBuildPopularMoviesUri() {
        Uri popularMoviesUri = MovieEntry.buildPopularMoviesUri();
        assertNotNull("Error: Null Uri returned.  You must fill-in buildPopularMoviesUri in " +
                "MovieContract.", popularMoviesUri);
        assertEquals("Error: Popular movies string not properly appended to the end of the Uri",
                MovieContract.ORDER_BY_POPULAR, popularMoviesUri.getLastPathSegment());
        assertEquals("Error: Popular movies Uri doesn't match our expected result",
                popularMoviesUri.toString(),
                "content://com.josecognizant.popmovies.app/movie/" + MovieContract.ORDER_BY_POPULAR);
    }

    public void testBuildTopRatedMoviesUri() {
        Uri topRatedMoviesUri = MovieEntry.buildTopRatedMoviesUri();
        assertNotNull("Error: Null Uri returned.  You must fill-in buildTopRatedMoviesUri in " +
                "MovieContract.", topRatedMoviesUri);
        assertEquals("Error: Top rated movies string not properly appended to the end of the Uri",
                MovieContract.ORDER_BY_TOPRATED, topRatedMoviesUri.getLastPathSegment());
        assertEquals("Error: Top rated movies Uri doesn't match our expected result",
                topRatedMoviesUri.toString(),
                "content://com.josecognizant.popmovies.app/movie/" + MovieContract.ORDER_BY_TOPRATED);
    }

    public void testBuildFavoriteMoviesUri() {
        Uri favoriteMoviesUri = MovieEntry.buildFavoriteMoviesUri();
        assertNotNull("Error: Null Uri returned.  You must fill-in buildFavoriteMoviesUri in " +
                "MovieContract.", favoriteMoviesUri);
        assertEquals("Error: Favorite movies string not properly appended to the end of the Uri",
                MovieContract.FAVORITE_MOVIES, favoriteMoviesUri.getLastPathSegment());
        assertEquals("Error: Favorite movies Uri doesn't match our expected result",
                favoriteMoviesUri.toString(),
                "content://com.josecognizant.popmovies.app/movie/" + MovieContract.FAVORITE_MOVIES);
    }
}
