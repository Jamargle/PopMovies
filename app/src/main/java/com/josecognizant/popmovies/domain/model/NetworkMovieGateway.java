package com.josecognizant.popmovies.domain.model;

import java.util.List;

/**
 * Interface for accessing to Movie information through network resources
 * Created by Jose on 24/05/2016.
 */
public interface NetworkMovieGateway {
    List<Movie> obtainMovies();
}
