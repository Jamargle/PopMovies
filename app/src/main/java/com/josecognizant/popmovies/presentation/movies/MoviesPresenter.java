package com.josecognizant.popmovies.presentation.movies;

public interface MoviesPresenter {
    void onAttach(MoviesView view);

    void refreshMovies();

    void onDetach();
}
