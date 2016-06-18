package com.josecognizant.popmovies.data.network;

import com.josecognizant.popmovies.BuildConfig;
import com.josecognizant.popmovies.data.local.MovieContract;
import com.josecognizant.popmovies.domain.model.Movie;
import com.josecognizant.popmovies.domain.model.MoviePage;
import com.josecognizant.popmovies.domain.model.NetworkMovieGateway;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Implementation of a NetworkGateway for download resources from the Moviedb API
 * Created by Jose on 24/05/2016.
 */
public class NetworkMovieGatewayImp implements NetworkMovieGateway {
    private final MovieDbClient mMovieDbClient;
    private NetworkMovieGateway.OnNetWorkGatewayListener listener;

    public NetworkMovieGatewayImp(MovieDbClient apiClient) {
        mMovieDbClient = apiClient;
    }

    @Override
    public void setMovieLoadListener(OnNetWorkGatewayListener listener) {
        this.listener = listener;
    }

    @Override
    public void refresh(String moviesToShow) {
        refreshMoviesFromInternet(moviesToShow);
    }

    private void refreshMoviesFromInternet(String moviesToShow) {
        if (moviesToShow.equals(MovieContract.ORDER_BY_POPULAR)) {
            performMovieCall(mMovieDbClient
                    .getListOfPopularMovies(BuildConfig.MOVIES_API_KEY));
        } else if (moviesToShow.equals(MovieContract.ORDER_BY_TOPRATED)) {
            performMovieCall(mMovieDbClient
                    .getListOfTopRatedMovies(BuildConfig.MOVIES_API_KEY));
        }
    }

    private List<Movie> performMovieCall(Call<MoviePage> call) {
        final List<Movie> movies = new ArrayList<>();

        if (mMovieDbClient != null && call != null) {
            call.enqueue(new Callback<MoviePage>() {
                @Override
                public void onResponse(Call<MoviePage> call, Response<MoviePage> response) {
                    MoviePage page = response.body();
                    List<Movie> movieList = page.getMovies();
                    if (movieList != null) {
                        for (Movie movie : movieList) {
                            movies.add(movie);
                        }
                    }
                    listener.onMoviesDownloaded(movies);
                }

                @Override
                public void onFailure(Call<MoviePage> call, Throwable t) {
                    listener.onErrorMoviesDownloaded();
                }
            });
        }

        return movies;
    }
}
