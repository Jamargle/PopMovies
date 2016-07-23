package com.josecognizant.popmovies.domain.interactor;

import com.josecognizant.popmovies.domain.model.LocalMovieGateway;
import com.josecognizant.popmovies.domain.model.Movie;

/**
 * Interactor to update Movies
 * Created by 552702 on 14/07/2016.
 */
public class UpdateMovieInteractor implements Interactor {

    private final LocalMovieGateway localGateway;
    private UpdateMoviesInteractorOutput output;
    private Movie movie;

    public UpdateMovieInteractor(LocalMovieGateway localGateway) {
        this.localGateway = localGateway;
    }

    public void setOutput(UpdateMoviesInteractorOutput output) {
        this.output = output;
    }

    public void setMovieToUpdate(Movie movie) {
        this.movie = movie;
    }

    @Override
    public void run() {
        updateMovie();
    }

    private void updateMovie() {
        int updatedMovies = -1;
        if (localGateway != null) {
            if (movie != null) {
                updatedMovies = localGateway.update(movie);
            }
        }
        if (output != null) {
            if (updatedMovies > 0) {
                output.onMovieUpdated(updatedMovies);
            } else {
                output.onUpdateMovieError();
            }
        }
    }

    public interface UpdateMoviesInteractorOutput {
        void onMovieUpdated(int numberOfUpdatedMovies);

        void onUpdateMovieError();
    }
}
