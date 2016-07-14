package com.josecognizant.popmovies.presentation.details;

import com.josecognizant.popmovies.domain.interactor.UpdateMovieInteractor;
import com.josecognizant.popmovies.domain.model.Movie;
import com.josecognizant.popmovies.presentation.InteractorExecutor;

/**
 * Presenter for the Movie Details screen
 * Created by Jose on 13/07/2016.
 */
public class DetailPresenterImp implements DetailPresenter {

    private final UpdateMovieInteractor updateMovieInteractor;
    private final InteractorExecutor interactorExecutor;

    private Movie movie;
    private DetailView view;

    public DetailPresenterImp(DetailView view, Movie movie,
                              UpdateMovieInteractor updateMovieInteractor,
                              InteractorExecutor interactorExecutor) {
        this.view = view;
        this.movie = movie;
        this.updateMovieInteractor = updateMovieInteractor;
        this.interactorExecutor = interactorExecutor;
    }

    @Override
    public void loadMovie() {
        view.setTitle(movie.getOriginalTitle());
        view.setOverView(movie.getOverview());
        view.setReleaseYear(movie.getReleaseDate());
        view.setVoteAverage(movie.getVoteAverage());
        view.setMovieImage(movie.getThumbnailPosterPath());
        view.setFavorite(movie.getFavorite());
    }

    @Override
    public void saveMovieCurrentState() {
        updateMovieValues();
        updateMovieInteractor.setMovieToUpdate(movie);
        interactorExecutor.execute(updateMovieInteractor);
    }

    private void updateMovieValues() {
        movie.setFavorite(view.getFavoriteValue());
    }
}
