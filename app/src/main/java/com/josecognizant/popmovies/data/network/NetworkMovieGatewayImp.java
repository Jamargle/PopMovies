package com.josecognizant.popmovies.data.network;

import android.util.Log;

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
        final List<Movie> movies = new ArrayList<>();
        if (mMovieDbClient != null) {
            Call<MoviePage> call =
                    mMovieDbClient.getListOfPopularMovies(BuildConfig.MOVIES_API_KEY);

            call.enqueue(new Callback<MoviePage>() {
                @Override
                public void onResponse(Call<MoviePage> call, Response<MoviePage> response) {
                    MoviePage page = response.body();
                    List<Movie> movieList = page.getMovies();
                    if (movieList != null) {
                        for (Movie movie : movieList) {
                            movies.add(movie);
                            Log.d("NetworkGW", movie.getOriginalTitle() + " (" + movie.getOrderType() + ")");
                        }
                    }

                }

                @Override
                public void onFailure(Call<MoviePage> call, Throwable t) {
                    Log.e("NetworkGW", "Error during download of movies");
                }
            });
        }
        return movies;
    }
}
