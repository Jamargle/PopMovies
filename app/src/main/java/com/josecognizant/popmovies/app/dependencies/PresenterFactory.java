package com.josecognizant.popmovies.app.dependencies;

import android.content.Context;

import com.josecognizant.popmovies.domain.model.Movie;
import com.josecognizant.popmovies.presentation.InteractorExecutor;
import com.josecognizant.popmovies.presentation.InteractorExecutorImp;
import com.josecognizant.popmovies.presentation.details.DetailPresenter;
import com.josecognizant.popmovies.presentation.details.DetailPresenterImp;
import com.josecognizant.popmovies.presentation.details.DetailView;

import java.util.concurrent.Executors;

/**
 * Helper class to perform presenter creations
 * Created by Jose on 17/06/2016.
 */
public abstract class PresenterFactory {
    public static DetailPresenter makeDetailPresenter(DetailView view, Movie movie, Context context) {
        return new DetailPresenterImp(view, movie,
                InteractorFactory.makeUpdateMoviesInteractor(context),
                makeInteractorExecutor());
    }

    private static InteractorExecutor makeInteractorExecutor() {
        return new InteractorExecutorImp(Executors.newFixedThreadPool(2));
    }
}
