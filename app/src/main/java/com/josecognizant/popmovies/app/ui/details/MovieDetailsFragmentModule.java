package com.josecognizant.popmovies.app.ui.details;

import com.josecognizant.popmovies.app.dependencies.scopes.FragmentScope;
import com.josecognizant.popmovies.domain.interactor.UpdateMovieInteractor;
import com.josecognizant.popmovies.domain.model.Movie;
import com.josecognizant.popmovies.presentation.InteractorExecutor;
import com.josecognizant.popmovies.presentation.details.DetailPresenter;
import com.josecognizant.popmovies.presentation.details.DetailPresenterImp;

import dagger.Module;
import dagger.Provides;

@Module
public class MovieDetailsFragmentModule {

    private final Movie movie;

    public MovieDetailsFragmentModule(Movie movie) {
        this.movie = movie;
    }

    @Provides
    @FragmentScope
    DetailPresenter provideDetailsPresenter(UpdateMovieInteractor updateMovieInteractor,
                                            InteractorExecutor interactorExecutor) {
        return new DetailPresenterImp(movie, updateMovieInteractor, interactorExecutor);
    }
}
