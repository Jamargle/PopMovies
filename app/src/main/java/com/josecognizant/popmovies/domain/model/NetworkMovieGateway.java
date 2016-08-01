package com.josecognizant.popmovies.domain.model;

import com.josecognizant.popmovies.domain.exception.NetworkConnectionException;

import java.util.List;

/**
 * Interface for accessing to Movie information through network resources
 * Created by Jose on 24/05/2016.
 */
public interface NetworkMovieGateway {
    List<Movie> obtainMovies() throws NetworkConnectionException;
}
