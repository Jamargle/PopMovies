package com.josecognizant.popmovies.app.dependencies;

import android.content.Context;

import com.josecognizant.popmovies.presentation.InteractorExecutor;
import com.josecognizant.popmovies.presentation.InteractorExecutorImp;
import com.josecognizant.popmovies.presentation.movies.MoviesPresenter;
import com.josecognizant.popmovies.presentation.movies.MoviesPresenterImp;
import com.josecognizant.popmovies.presentation.movies.MoviesView;

import java.util.concurrent.Executors;

/**
 * Helper class to perform presenter creations
 * Created by Jose on 17/06/2016.
 */
public class PresenterFactory {
    public static MoviesPresenter makeMoviesPresenter(MoviesView view, Context context) {
        return new MoviesPresenterImp(view,
                InteractorFactory.makeLoadMoviesInteractor(context),
                makeInteractorExecutor());
    }

    private static InteractorExecutor makeInteractorExecutor() {
        return new InteractorExecutorImp(Executors.newFixedThreadPool(2));
    }
}
