package com.josecognizant.popmovies.domain.interactor;

import com.josecognizant.popmovies.domain.exception.NetworkConnectionException;
import com.josecognizant.popmovies.domain.model.LocalMovieGateway;
import com.josecognizant.popmovies.domain.model.Movie;
import com.josecognizant.popmovies.domain.model.NetworkMovieGateway;

import java.util.List;

/**
 * Interactor for obtain Movies
 * Created by 552702 on 24/05/2016.
 */
public class LoadMoviesInteractor implements Interactor {

    private LocalMovieGateway localGateway;
    private NetworkMovieGateway networkGateway;
    private LoadMoviesInteractorOutput output;


    public LoadMoviesInteractor(LocalMovieGateway localGateway, NetworkMovieGateway networkGateway) {
        this.localGateway = localGateway;
        this.networkGateway = networkGateway;
    }

    public void setOutput(LoadMoviesInteractorOutput output) {
        this.output = output;
    }

    @Override
    public void run() {
        try {
            loadMovies();
        } catch (NetworkConnectionException e) {
            output.onLoadMoviesError();
        }
    }

    private void loadMovies() throws NetworkConnectionException {
        List<Movie> movieList;
        if (localGateway != null) {
            movieList = localGateway.obtainMovies();
            if (movieList.isEmpty() && networkGateway != null) {
                movieList = networkGateway.obtainMovies();
                localGateway.persist(movieList);
            }
            output.onMoviesLoaded(movieList);
        }
    }

    public interface LoadMoviesInteractorOutput {
        void onMoviesLoaded(List<Movie> movieList);

        void onLoadMoviesError();
    }
}
