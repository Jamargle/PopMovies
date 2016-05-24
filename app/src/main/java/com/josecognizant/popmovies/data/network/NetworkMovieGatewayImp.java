package com.josecognizant.popmovies.data.network;

import com.josecognizant.popmovies.domain.model.Movie;
import com.josecognizant.popmovies.domain.model.NetworkMovieGateway;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of a NetworkGateway for download resources from the Moviedb API
 * Created by 552702 on 24/05/2016.
 */
public class NetworkMovieGatewayImp implements NetworkMovieGateway {

    private final MovieJsonParserApiClient mApiClient;

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
    }
}
