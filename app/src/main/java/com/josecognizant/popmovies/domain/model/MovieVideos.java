package com.josecognizant.popmovies.domain.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Class representing a set of videos of a movie
 * Created by Jose on 06/06/2016.
 */
public class MovieVideos {
    @SerializedName("id")
    private int movieId;
    @SerializedName("results")
    private List<Video> videos;

    public int getId() {
        return movieId;
    }

    public void setId(int movieId) {
        this.movieId = movieId;
    }

    public List<Video> getResults() {
        return videos;
    }

    public void setResults(List<Video> videos) {
        this.videos = videos;
    }
}
