package com.josecognizant.popmovies.data.network;

import com.josecognizant.popmovies.BuildConfig;
import com.josecognizant.popmovies.domain.model.Movie;
import com.josecognizant.popmovies.domain.model.MoviePage;
import com.josecognizant.popmovies.domain.model.NetworkMovieGateway;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of a NetworkGateway for download resources from the Moviedb API
 * Created by 552702 on 24/05/2016.
 */
public class NetworkMovieGatewayImp implements NetworkMovieGateway {


    private final MovieDbClient mApiService;

    public NetworkMovieGatewayImp(MovieDbClient apiService) {
        mApiService = apiService;
    }

    @Override
    public List<Movie> refresh() {
        List<Movie> movies = new ArrayList<>();

        MoviePage page;
        try {
            page = mApiService.getListOfPopularMovies(BuildConfig.APPLICATION_ID).execute().body();
            if (page != null && page.getMovies() != null) {
                for (Movie movie : page.getMovies()) {
                    movies.add(movie);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movies;
    }




    /*private final MovieJsonParserApiClient mApiClient;

    public NetworkMovieGatewayImp(MovieJsonParserApiClient apiClient) {
        mApiClient = apiClient;
    }

    @Override
    public List<Movie> refresh() {
        return refreshMoviesFromInternet();
    }

    private List<Movie> refreshMoviesFromInternet() {
        if (mApiClient != null) {
            try {
                return mApiClient.getMoviesDataFromJson();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<>();
    }*/
}
