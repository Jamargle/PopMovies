package com.josecognizant.popmovies.app.ui.details;

import com.josecognizant.popmovies.app.dependencies.scopes.FragmentScope;
import com.josecognizant.popmovies.domain.interactor.UpdateMovieInteractor;
import com.josecognizant.popmovies.domain.model.LocalMovieGateway;
import com.josecognizant.popmovies.domain.model.Movie;
import com.josecognizant.popmovies.presentation.InteractorExecutor;
import com.josecognizant.popmovies.presentation.details.DetailPresenter;
import com.josecognizant.popmovies.presentation.details.DetailPresenterImp;

import dagger.Module;
import dagger.Provides;

@Module
@FragmentScope
public class MovieDetailsFragmentModule {

    private final Movie movie;

    public MovieDetailsFragmentModule(Movie movie) {
        this.movie = movie;
    }

    @Provides
    UpdateMovieInteractor provideUpdateMovieInteractor(LocalMovieGateway localGateway) {
        return new UpdateMovieInteractor(localGateway);
    }

    @Provides
    DetailPresenter provideDetailsPresenter(UpdateMovieInteractor updateMovieInteractor,
                                            InteractorExecutor interactorExecutor) {
        return new DetailPresenterImp(movie, updateMovieInteractor, interactorExecutor);
    }
}
