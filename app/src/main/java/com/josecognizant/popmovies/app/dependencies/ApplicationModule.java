package com.josecognizant.popmovies.app.dependencies;

import android.content.Context;

import com.josecognizant.popmovies.app.PopMoviesApp;
import com.josecognizant.popmovies.app.util.ServiceGenerator;
import com.josecognizant.popmovies.data.local.LocalMovieGatewayImp;
import com.josecognizant.popmovies.data.network.MovieDbClient;
import com.josecognizant.popmovies.data.network.NetworkMovieGatewayImp;
import com.josecognizant.popmovies.domain.interactor.LoadMoviesInteractor;
import com.josecognizant.popmovies.domain.interactor.UpdateMovieInteractor;
import com.josecognizant.popmovies.domain.model.LocalMovieGateway;
import com.josecognizant.popmovies.domain.model.NetworkMovieGateway;
import com.josecognizant.popmovies.presentation.InteractorExecutor;
import com.josecognizant.popmovies.presentation.InteractorExecutorImp;

import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Main module for dependency injection with Dagger 2
 * Created by Jose on 24/07/2016.
 */
@Module
public class ApplicationModule {

    private final PopMoviesApp application;

    public ApplicationModule(PopMoviesApp application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return application;
    }

    @Provides
    @Singleton
    InteractorExecutor provideInteractorExecutor() {
        return new InteractorExecutorImp(Executors.newFixedThreadPool(2));
    }

    @Provides
    @Singleton
    LocalMovieGateway provideLocalMovieGateway(Context context) {
        return new LocalMovieGatewayImp(context);
    }

    @Provides
    @Singleton
    NetworkMovieGateway provideNetworkMovieGateway() {
        return new NetworkMovieGatewayImp(ServiceGenerator.createService(MovieDbClient.class));
    }

    @Provides
    @Singleton
    LoadMoviesInteractor provideLoadMoviesInteractor(LocalMovieGateway localGateway, NetworkMovieGateway networkGateway) {
        return new LoadMoviesInteractor(localGateway, networkGateway);
    }

    @Provides
    @Singleton
    UpdateMovieInteractor provideUpdateMovieInteractor(LocalMovieGateway localGateway) {
        return new UpdateMovieInteractor(localGateway);
    }
}
