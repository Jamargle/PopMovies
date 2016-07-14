package com.josecognizant.popmovies.presentation.details;

import com.josecognizant.popmovies.domain.model.Movie;

/**
 * Presenter for the Movie Deatails screen
 * Created by Jose on 13/07/2016.
 */
public class DetailPresenterImp implements DetailPresenter {

    private Movie movie;
    private DetailView view;

    public DetailPresenterImp(DetailView view, Movie movie) {
        this.view = view;
        this.movie = movie;
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
}
