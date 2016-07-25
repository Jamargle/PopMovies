package com.josecognizant.popmovies.app.ui.movies;

import com.josecognizant.popmovies.app.dependencies.scopes.FragmentScope;
import com.josecognizant.popmovies.domain.interactor.LoadMoviesInteractor;
import com.josecognizant.popmovies.presentation.InteractorExecutor;
import com.josecognizant.popmovies.presentation.movies.MoviesPresenter;
import com.josecognizant.popmovies.presentation.movies.MoviesPresenterImp;

import dagger.Module;
import dagger.Provides;

@Module
public class MovieListFragmentModule {

    @Provides
    @FragmentScope
    MoviesPresenter provideMoviesPresenter(LoadMoviesInteractor loadMoviesInteractor,
                                           InteractorExecutor interactorExecutor) {
        return new MoviesPresenterImp(loadMoviesInteractor, interactorExecutor);
    }
}
