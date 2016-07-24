package com.josecognizant.popmovies.domain.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing the result of a page downloaded from the Moviedb API
 * Created by Jose on 12/06/2016.
 */
public class MoviePage {
    @SerializedName("page")
    private int numPage;
    @SerializedName("results")
    private List<Movie> movies;

    public MoviePage() {
    }

    public int getNumPage() {
        return numPage;
    }

    public void setNumPage(int numPage) {
        this.numPage = numPage;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }
}
