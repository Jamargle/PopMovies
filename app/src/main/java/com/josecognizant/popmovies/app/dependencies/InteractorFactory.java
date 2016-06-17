package com.josecognizant.popmovies.app.dependencies;

import com.josecognizant.popmovies.domain.interactor.LoadMoviesInteractor;

import static com.josecognizant.popmovies.app.dependencies.GatewayFactory.makeLocalGateway;
import static com.josecognizant.popmovies.app.dependencies.GatewayFactory.makeNetworkGateway;

/**
 * Helper class to perform Interactor creations
 * Created by Jose on 17/06/2016.
 */
public class InteractorFactory {
    public static LoadMoviesInteractor makeLoadMoviesInteractor() {
        return new LoadMoviesInteractor(makeLocalGateway(), makeNetworkGateway());
    }
}
