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
    private String moviesToShow;


    public LoadMoviesInteractor(LocalMovieGateway localGateway, NetworkMovieGateway networkGateway,
                                String moviesToShow) {
        this.localGateway = localGateway;
        this.networkGateway = networkGateway;
        networkGateway.setMovieLoadListener(this);
        this.moviesToShow = moviesToShow;
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
        List<Movie> movieList;
        if (localGateway != null) {
            movieList = localGateway.obtainMovies();
            if (movieList.isEmpty() && networkGateway != null) {
                movieList = networkGateway.obtainMovies();
                localGateway.update(movieList);
            }
            output.onMoviesLoaded(movieList);
        }
    }

    public interface LoadMoviesInteractorOutput {
        void onMoviesLoaded(List<Movie> movieList);

        void onLoadMoviesError();
    }
}
