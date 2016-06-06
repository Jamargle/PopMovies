package com.josecognizant.popmovies.util;

import com.josecognizant.popmovies.model.MovieVideos;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Interface to define end points to get videos
 * Created by Jose on 06/06/2016.
 */
public interface MovieDbClient {

    @GET("movie/{id}/videos?api_key={api_key}")
    Call<MovieVideos> getListOfVideos(@Path("id") int movieId, @Path("api_key") int apiKey);
}
