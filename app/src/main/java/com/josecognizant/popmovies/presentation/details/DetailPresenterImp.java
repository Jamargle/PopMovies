package com.josecognizant.popmovies.presentation.details;

import com.josecognizant.popmovies.BuildConfig;
import com.josecognizant.popmovies.app.util.ServiceGenerator;
import com.josecognizant.popmovies.data.network.MovieDbClient;
import com.josecognizant.popmovies.domain.interactor.UpdateMovieInteractor;
import com.josecognizant.popmovies.domain.model.Movie;
import com.josecognizant.popmovies.domain.model.MovieVideos;
import com.josecognizant.popmovies.domain.model.Video;
import com.josecognizant.popmovies.presentation.InteractorExecutor;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Presenter for the Movie Details screen
 * Created by Jose on 13/07/2016.
 */
public class DetailPresenterImp
        implements DetailPresenter, UpdateMovieInteractor.UpdateMoviesInteractorOutput {

    private final UpdateMovieInteractor updateMovieInteractor;
    private final InteractorExecutor interactorExecutor;

    private Movie movie;
    private DetailView view;

    public DetailPresenterImp(Movie movie,
                              UpdateMovieInteractor updateMovieInteractor,
                              InteractorExecutor interactorExecutor) {

        this.movie = movie;
        this.updateMovieInteractor = updateMovieInteractor;
        this.interactorExecutor = interactorExecutor;
    }

    @Override
    public void onAttach(DetailView view) {
        this.view = view;
    }

    @Override
    public void loadMovie() {
        getVideosForTheMovie();
        view.setTitle(movie.getOriginalTitle());
        view.setOverView(movie.getOverview());
        view.setReleaseYear(movie.getReleaseDate());
        view.setVoteAverage(movie.getVoteAverage());
        view.setMovieImage(movie.getThumbnailPosterPath());
        view.setFavorite(movie.getFavorite());
    }

    @Override
    public void saveMovieCurrentState() {
        updateMovieValues();
        updateMovieInteractor.setMovieToUpdate(movie);
        updateMovieInteractor.setOutput(this);
        interactorExecutor.execute(updateMovieInteractor);
    }

    @Override
    public void onDetach() {
        view = null;
    }

    private void getVideosForTheMovie() {
        MovieDbClient client = ServiceGenerator.createService(MovieDbClient.class);
        Call<MovieVideos> call = client.getListOfVideos(movie.getMovieApiId(), BuildConfig.MOVIES_API_KEY);

        call.enqueue(new Callback<MovieVideos>() {
            @Override
            public void onResponse(Call<MovieVideos> call, Response<MovieVideos> response) {
                MovieVideos videos = response.body();
                List<Video> videoList = videos.getResults();
                if (videoList != null) {
                    view.setVideosList(videoList);
                } else {
                    view.setVideosList(new ArrayList<Video>());
                }
            }

            @Override
            public void onFailure(Call<MovieVideos> call, Throwable t) {
            }
        });
    }

    private void updateMovieValues() {
        movie.setFavorite(view.getFavoriteValue());
    }

    @Override
    public void onMovieUpdated(int numberOfUpdatedMovies) {
        view.showMovieUpdatedMessage();
    }

    @Override
    public void onUpdateMovieError() {
        view.showUpdateMovieError();
    }
}
