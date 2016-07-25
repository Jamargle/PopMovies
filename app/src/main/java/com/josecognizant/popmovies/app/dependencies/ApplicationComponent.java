package com.josecognizant.popmovies.app.dependencies;

import android.content.Context;

import com.josecognizant.popmovies.domain.interactor.LoadMoviesInteractor;
import com.josecognizant.popmovies.domain.interactor.UpdateMovieInteractor;
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

    LoadMoviesInteractor loadMoviesInteractor();

    UpdateMovieInteractor updateMovieInteractor();

    InteractorExecutor interactorExecutor();
}
