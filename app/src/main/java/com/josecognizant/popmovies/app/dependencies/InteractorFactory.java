package com.josecognizant.popmovies.app.dependencies;

import android.content.Context;

import com.josecognizant.popmovies.app.util.MovieUtilities;
import com.josecognizant.popmovies.domain.interactor.LoadMoviesInteractor;

import static com.josecognizant.popmovies.app.dependencies.GatewayFactory.makeLocalGateway;
import static com.josecognizant.popmovies.app.dependencies.GatewayFactory.makeNetworkGateway;

/**
 * Helper class to perform Interactor creations
 * Created by Jose on 17/06/2016.
 */
public class InteractorFactory {
    public static LoadMoviesInteractor makeLoadMoviesInteractor(Context context) {
        return new LoadMoviesInteractor(makeLocalGateway(context),
                makeNetworkGateway(),
                MovieUtilities.getMovieOrderSetting(context));
    }
}
