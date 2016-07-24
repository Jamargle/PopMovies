package com.josecognizant.popmovies.domain.model;

import java.util.List;

/**
 * Interface for accessing to Movie information in local sources
 * Created by Jose on 24/05/2016.
 */
public interface LocalMovieGateway {
    List<Movie> obtainMovies();

    void persist(List<Movie> moviesToPersist);

    /**
     * Updates a movie in the local database
     *
     * @param movieToUpdate Movie to be updated
     * @return Number of rows updated
     */
    int update(Movie movieToUpdate);

    void delete(Movie movieToDelete);
}
