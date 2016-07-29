package com.josecognizant.popmovies.app.dependencies;

import android.content.Context;

import com.josecognizant.popmovies.domain.model.LocalMovieGateway;
import com.josecognizant.popmovies.domain.model.NetworkMovieGateway;
import com.josecognizant.popmovies.presentation.InteractorExecutor;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Main component for dependency injection with Dagger 2
 * Created by Jose on 24/07/2016.
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    Context context();

    NetworkMovieGateway networkMovieGateway();

    LocalMovieGateway localMovieGateway();

    InteractorExecutor interactorExecutor();
}
