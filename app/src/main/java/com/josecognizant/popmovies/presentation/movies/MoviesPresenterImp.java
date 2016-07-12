package com.josecognizant.popmovies.presentation.movies;

import com.josecognizant.popmovies.domain.interactor.LoadMoviesInteractor;
import com.josecognizant.popmovies.domain.model.Movie;
import com.josecognizant.popmovies.presentation.InteractorExecutor;

import java.util.List;

/**
 * Presenter for the Movies screen
 * Created by Jose on 14/06/2016.
 */
public class MoviesPresenterImp
        implements MoviesPresenter, LoadMoviesInteractor.LoadMoviesInteractorOutput {

    private final LoadMoviesInteractor loadMoviesInteractor;
    private final InteractorExecutor interactorExecutor;
    private MoviesView moviesView;

    public MoviesPresenterImp(MoviesView view,
                              LoadMoviesInteractor loadMoviesInteractor,
                              InteractorExecutor interactorExecutor) {

        moviesView = view;
        this.loadMoviesInteractor = loadMoviesInteractor;
        this.interactorExecutor = interactorExecutor;
    }

    @Override
    public void refreshMovies() {
        moviesView.showLoading();
        loadMoviesInteractor.setOutput(this);
        interactorExecutor.execute(loadMoviesInteractor);
    }

    @Override
    public void onDetach() {
        moviesView = null;
    }

    @Override
    public void onMoviesLoaded(List<Movie> movieList) {
        moviesView.hideLoading();
        moviesView.updateMoviesToShow(movieList);
    }

    @Override
    public void onLoadMoviesError() {
        moviesView.hideLoading();
        moviesView.showLoadMoviesError();
    }
}
