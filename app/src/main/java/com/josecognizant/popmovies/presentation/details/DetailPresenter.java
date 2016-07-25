package com.josecognizant.popmovies.presentation.details;

/**
 * Presenter for the Movie Deatails screen
 * Created by Jose on 13/07/2016.
 */
public interface DetailPresenter {

    void onAttach(DetailView view);

    void loadMovie();

    void saveMovieCurrentState();

    void onDetach();
}
