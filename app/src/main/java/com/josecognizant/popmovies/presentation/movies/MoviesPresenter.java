package com.josecognizant.popmovies.presentation.movies;

/**
 * Presenter for the Movies screen
 * Created by Jose on 14/06/2016.
 */
public class MoviesPresenter {

    private MoviesView mMoviesView;

    public MoviesPresenter() {
    }

    public void onAttach(MoviesView view) {
        mMoviesView = view;
    }

    public void onDetach() {
        mMoviesView = null;
    }


}
