package com.josecognizant.popmovies.presentation.movies;

import com.josecognizant.popmovies.domain.model.Movie;

import java.util.List;

/**
 * View for the Movies screen
 * Created by Jose on 14/06/2016.
 */
public interface MoviesView {
    void showLoading();

    void hideLoading();

    void showLoadMoviesError();

    void hideMoviesError();

    void updateMoviesToShow(List<Movie> movies);
}
