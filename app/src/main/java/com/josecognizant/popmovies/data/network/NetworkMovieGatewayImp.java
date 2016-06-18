package com.josecognizant.popmovies.data.network;

import com.josecognizant.popmovies.BuildConfig;
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

    public NetworkMovieGatewayImp(MovieDbClient apiClient) {
        mMovieDbClient = apiClient;
    }

    @Override
    public List<Movie> refresh() {
        return refreshMoviesFromInternet();
    }

    private List<Movie> refreshMoviesFromInternet() {
        List<Movie> movies = new ArrayList<>();
        List<Movie> popularMovies, topRatedMovies;

        popularMovies = performMovieCall(mMovieDbClient
                .getListOfPopularMovies(BuildConfig.MOVIES_API_KEY));
        topRatedMovies = performMovieCall(mMovieDbClient
                .getListOfTopRatedMovies(BuildConfig.MOVIES_API_KEY));

        movies.addAll(popularMovies);
        movies.addAll(topRatedMovies);

        return movies;
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

                }

                @Override
                public void onFailure(Call<MoviePage> call, Throwable t) {
                }
            });
        }
        return movies;
    }

}
