package com.josecognizant.popmovies.domain.interactor;

import com.josecognizant.popmovies.data.network.NetworkMovieGatewayImp;
import com.josecognizant.popmovies.domain.model.LocalMovieGateway;
import com.josecognizant.popmovies.domain.model.Movie;
import com.josecognizant.popmovies.domain.model.NetworkMovieGateway;

import java.util.List;

/**
 * Interactor for obtain Movies
 * Created by 552702 on 24/05/2016.
 */
public class LoadMoviesInteractor
        implements Interactor, NetworkMovieGatewayImp.OnNetWorkGatewayListener {

    private LocalMovieGateway localGateway;
    private NetworkMovieGateway networkGateway;
    private LoadMoviesInteractorOutput output;


    public LoadMoviesInteractor(LocalMovieGateway localGateway, NetworkMovieGateway networkGateway) {
        this.localGateway = localGateway;
        this.networkGateway = networkGateway;
        networkGateway.setMovieLoadListener(this);
    }

    public void setOutput(LoadMoviesInteractorOutput output) {
        this.output = output;
    }

    @Override
    public void run() {
        try {
            loadMovies();
        } catch (Exception e) {
            e.printStackTrace();
            output.onLoadMoviesError();
        }
    }

    @Override
    public void onMoviesDownloaded(List<Movie> movies) {
        if (output != null) {
            output.onMoviesLoaded(movies);
            localGateway.update(movies);
        }
    }

    @Override
    public void onErrorMoviesDownloaded() {
        if (output != null) {
            output.onLoadMoviesError();
        }
    }

    private void loadMovies() {
        if (localGateway != null) {
            List<Movie> movies = localGateway.obtainMovies();
            if (movies.isEmpty() && networkGateway != null) {
                networkGateway.refresh();
            } else {
                output.onMoviesLoaded(movies);
            }
        }
    }

    public interface LoadMoviesInteractorOutput {
        void onMoviesLoaded(List<Movie> movieList);

        void onLoadMoviesError();
    }
}
