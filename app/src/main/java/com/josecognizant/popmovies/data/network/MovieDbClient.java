package com.josecognizant.popmovies.data.network;

import com.josecognizant.popmovies.domain.model.MoviePage;
import com.josecognizant.popmovies.domain.model.MovieVideos;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Interface to define end points to get videos
 * Created by Jose on 06/06/2016.
 */
public interface MovieDbClient {

    @GET("movie/popular")
    Call<MoviePage> getListOfPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<MoviePage> getListOfTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/{id}/videos")
    Call<MovieVideos> getListOfVideos(@Path("id") int movieId, @Query("api_key") String apiKey);
}
