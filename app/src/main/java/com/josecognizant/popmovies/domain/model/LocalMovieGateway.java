package com.josecognizant.popmovies.domain.model;

import java.util.List;

/**
 * Interface for accessing to Movie information in local sources
 * Created by Jose on 24/05/2016.
 */
public interface LocalMovieGateway {
    List<Movie> obtainMovies();

    long persist(Movie movieToPersist);

    long update(List<Movie> moviesToUpdate);

    void delete(Movie movieToDelete);
}
