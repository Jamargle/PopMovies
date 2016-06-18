package com.josecognizant.popmovies.domain.model;

import java.util.List;

/**
 * Interface for accessing to Movie information through network resources
 * Created by Jose on 24/05/2016.
 */
public interface NetworkMovieGateway {
    void refresh(String moviesToShow);

    void setMovieLoadListener(OnNetWorkGatewayListener listener);

    interface OnNetWorkGatewayListener {
        void onMoviesDownloaded(List<Movie> movies);

        void onErrorMoviesDownloaded();
    }
}
