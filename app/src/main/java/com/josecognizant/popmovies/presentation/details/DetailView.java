package com.josecognizant.popmovies.presentation.details;

/**
 * View for the Movie Details screen
 * Created by Jose on 14/06/2016.
 */
public interface DetailView {
    void setTitle(String originalTitle);

    void setOverView(String overview);

    void setReleaseYear(String releaseDate);

    void setVoteAverage(float voteAverage);

    void setMovieImage(String posterPath);

    void setFavorite(int favorite);

    int getFavoriteValue();
}
