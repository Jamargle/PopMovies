package com.josecognizant.popmovies.data.network;

import com.josecognizant.popmovies.BuildConfig;
import com.josecognizant.popmovies.data.local.MovieContract;
import com.josecognizant.popmovies.domain.exception.NetworkConnectionException;
import com.josecognizant.popmovies.domain.model.Movie;
import com.josecognizant.popmovies.domain.model.MoviePage;
import com.josecognizant.popmovies.domain.model.NetworkMovieGateway;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * Implementation of a NetworkGateway for download resources from the Moviedb API
 * Created by Jose on 24/05/2016.
 */
public class NetworkMovieGatewayImp implements NetworkMovieGateway {
    private static final String NETWORK_GATEWAY_ERROR_MESSAGE = "It was no possible to get a " +
            "response from the server to download movies";
    private final MovieDbClient mApiService;
    private final List<Movie> mMovies;

    public NetworkMovieGatewayImp(MovieDbClient apiService) {
        mApiService = apiService;
        mMovies = new ArrayList<>();
    }

    @Override
    public List<Movie> obtainMovies() throws NetworkConnectionException {
        Call<MoviePage> call = mApiService.getListOfPopularMovies(BuildConfig.MOVIES_API_KEY);
        addMoviesToList(call, MovieContract.ORDER_BY_POPULAR);

        call = mApiService.getListOfTopRatedMovies(BuildConfig.MOVIES_API_KEY);
        addMoviesToList(call, MovieContract.ORDER_BY_TOPRATED);

        return mMovies;
    }

    private void addMoviesToList(Call<MoviePage> call, String wayToOrder)
            throws NetworkConnectionException {
        MoviePage page;
        try {
            page = call.execute().body();
            if (page != null && page.getMovies() != null) {
                for (Movie movie : page.getMovies()) {
                    movie.setOrderType(wayToOrder);
                    mMovies.add(movie);
                }
            }
        } catch (IOException e) {
            throw new NetworkConnectionException(NETWORK_GATEWAY_ERROR_MESSAGE);
        }
    }
}
