package com.josecognizant.popmovies.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of the Content Provider
 * Created by Jose on 03/06/2016.
 */
public class MovieProvider extends ContentProvider {

    public static final int MOVIES = 100;
    public static final int MOVIES_ORDER_BY_POPULAR = 101;
    public static final int MOVIES_ORDER_BY_RATING = 102;
    public static final int MOVIES_FAVORITES = 103;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private MovieDbHelper mOpenHelper;

    public static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MovieContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, MovieContract.PATH_MOVIES, MOVIES);
        matcher.addURI(authority, MovieContract.PATH_MOVIES + "/" + MovieContract.ORDER_BY_POPULAR,
                MOVIES_ORDER_BY_POPULAR);
        matcher.addURI(authority, MovieContract.PATH_MOVIES + "/" + MovieContract.ORDER_BY_TOPRATED,
                MOVIES_ORDER_BY_RATING);
        matcher.addURI(authority, MovieContract.PATH_MOVIES + "/" + MovieContract.FAVORITE_MOVIES,
                MOVIES_FAVORITES);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new MovieDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursorToRet;
        switch (sUriMatcher.match(uri)) {
            case MOVIES: {
                cursorToRet = mOpenHelper.getReadableDatabase().query(
                        MovieContract.MovieEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            case MOVIES_ORDER_BY_POPULAR: {
                cursorToRet = getMoviesOrderedByPopular(projection, selection, selectionArgs, sortOrder);
                break;
            }

            case MOVIES_ORDER_BY_RATING: {
                cursorToRet = getMoviesOrderedByRating(projection, selection, selectionArgs, sortOrder);
                break;
            }

            case MOVIES_FAVORITES: {
                cursorToRet = getFavoriteMovies(projection, selection, selectionArgs, sortOrder);
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (cursorToRet != null && getContext() != null) {
            cursorToRet.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursorToRet;
    }

    private Cursor getMoviesOrderedByPopular(String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return getMoviesWithOrder(projection, selection, selectionArgs, sortOrder, MovieContract.ORDER_BY_POPULAR);
    }

    private Cursor getMoviesOrderedByRating(String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return getMoviesWithOrder(projection, selection, selectionArgs, sortOrder, MovieContract.ORDER_BY_TOPRATED);
    }

    private Cursor getFavoriteMovies(String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        String newSelection = selection + " AND " +
                MovieContract.MovieEntry.COLUMN_FAVORITE + " = 1";

        return mOpenHelper.getReadableDatabase().query(
                MovieContract.MovieEntry.TABLE_NAME,
                projection,
                newSelection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getMoviesWithOrder(String[] projection, String selection, String[] selectionArgs, String sortOrder, String typeOfOrder) {
        String newSelection = selection + " AND " +
                MovieContract.MovieEntry.COLUMN_ORDER_TYPE + " = ?";

        List<String> allArguments = new ArrayList<>();
        Collections.addAll(allArguments, selectionArgs);
        allArguments.add(typeOfOrder);
        String[] newSelectionArgs = (String[]) allArguments.toArray();

        return mOpenHelper.getReadableDatabase().query(
                MovieContract.MovieEntry.TABLE_NAME,
                projection,
                newSelection,
                newSelectionArgs,
                null,
                null,
                sortOrder
        );
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case MOVIES_ORDER_BY_POPULAR:
                return MovieContract.MovieEntry.CONTENT_TYPE;
            case MOVIES_ORDER_BY_RATING:
                return MovieContract.MovieEntry.CONTENT_TYPE;
            case MOVIES_FAVORITES:
                return MovieContract.MovieEntry.CONTENT_TYPE;
            case MOVIES:
                return MovieContract.MovieEntry.CONTENT_TYPE;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case MOVIES: {
                long movieId = db.insert(MovieContract.MovieEntry.TABLE_NAME, null, values);
                if (movieId > 0) {
                    returnUri = MovieContract.MovieEntry.buildMovieUri(movieId);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (getContext() != null) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return returnUri;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case MOVIES:
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long movieId = db.insert(MovieContract.MovieEntry.TABLE_NAME, null, value);
                        if (movieId != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                if (getContext() != null) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return returnCount;

            default:
                return super.bulkInsert(uri, values);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;

        if (null == selection) {
            selection = "1";
        }
        switch (match) {
            case MOVIES:
                rowsDeleted = db.delete(MovieContract.MovieEntry.TABLE_NAME, selection, selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (rowsDeleted != 0 && getContext() != null) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case MOVIES:
                rowsUpdated = db.update(MovieContract.MovieEntry.TABLE_NAME, values, selection, selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0 && getContext() != null) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }
}
