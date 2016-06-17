package com.josecognizant.popmovies.app.dependencies;

import com.josecognizant.popmovies.app.util.ServiceGenerator;
import com.josecognizant.popmovies.data.network.MovieDbClient;
import com.josecognizant.popmovies.data.network.NetworkMovieGatewayImp;
import com.josecognizant.popmovies.domain.model.LocalMovieGateway;
import com.josecognizant.popmovies.domain.model.NetworkMovieGateway;

/**
 * Helper class to perform Gateway creations
 * Created by Jose on 17/06/2016.
 */
public class GatewayFactory {
    public static LocalMovieGateway makeLocalGateway() {
        return null;
    }

    public static NetworkMovieGateway makeNetworkGateway() {
        return new NetworkMovieGatewayImp(ServiceGenerator.createService(MovieDbClient.class));
    }
}
