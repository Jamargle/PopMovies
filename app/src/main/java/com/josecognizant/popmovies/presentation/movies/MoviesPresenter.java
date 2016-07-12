package com.josecognizant.popmovies.presentation.movies;

import com.josecognizant.popmovies.domain.interactor.LoadMoviesInteractor;
import com.josecognizant.popmovies.domain.model.Movie;
import com.josecognizant.popmovies.presentation.InteractorExecutor;

import java.util.List;

/**
 * Presenter for the Movies screen
 * Created by Jose on 14/06/2016.
 */
public class MoviesPresenter implements LoadMoviesInteractor.LoadMoviesInteractorOutput {

    private final LoadMoviesInteractor loadMoviesInteractor;
    private final InteractorExecutor interactorExecutor;
    private MoviesView moviesView;
    private List<Movie> movieList;

    public MoviesPresenter(MoviesView view,
                           LoadMoviesInteractor loadMoviesInteractor,
                           InteractorExecutor interactorExecutor) {

        moviesView = view;
        this.loadMoviesInteractor = loadMoviesInteractor;
        this.interactorExecutor = interactorExecutor;
    }

    public void onDetach() {
        moviesView = null;
    }

    public void onRefreshMovies() {
        refreshCurrentLocationForecast();
    }

    private void refreshCurrentLocationForecast() {
        moviesView.showLoading();
        loadMoviesInteractor.setOutput(this);
        interactorExecutor.execute(loadMoviesInteractor);
    }

    @Override
    public void onMoviesLoaded(List<Movie> movieList) {
        this.movieList = movieList;
        updateView(movieList);
    }

    @Override
    public void onLoadMoviesError() {
        moviesView.hideLoading();
        moviesView.showLoadMoviesError();
    }

    private void updateView(List<Movie> movies) {
        moviesView.hideLoading();
        moviesView.updateMoviesToShow(movies);
    }
}
