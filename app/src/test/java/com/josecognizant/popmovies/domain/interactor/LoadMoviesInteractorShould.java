package com.josecognizant.popmovies.domain.interactor;

import com.josecognizant.popmovies.domain.exception.NetworkConnectionException;
import com.josecognizant.popmovies.domain.model.LocalMovieGateway;
import com.josecognizant.popmovies.domain.model.Movie;
import com.josecognizant.popmovies.domain.model.NetworkMovieGateway;

import org.junit.Before;
import org.junit.Test;
import org.powermock.api.easymock.PowerMock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.easymock.EasyMock.expect;

public class LoadMoviesInteractorShould {
    private final static List<Movie> EMPTY_MOVIE_LIST = Collections.emptyList();

    private LocalMovieGateway localMovieGateway;
    private NetworkMovieGateway networkMovieGateway;
    private LoadMoviesInteractor.LoadMoviesInteractorOutput output;

    private List<Movie> movieList = Collections.emptyList();
    private LoadMoviesInteractor loadMoviesInteractor;

    @Before
    public void setUp() {
        localMovieGateway = PowerMock.createMock(LocalMovieGateway.class);
        networkMovieGateway = PowerMock.createMock(NetworkMovieGateway.class);
        output = PowerMock.createMock(LoadMoviesInteractor.LoadMoviesInteractorOutput.class);
        movieList = createMovieList();

        loadMoviesInteractor = new LoadMoviesInteractor(localMovieGateway, networkMovieGateway);
        loadMoviesInteractor.setOutput(output);
    }

    @Test
    public void callToOnMoviesLoadedWhenLocalGatewayHasMovies() {
        //given
        expect(localMovieGateway.obtainMovies()).andReturn(movieList);
        output.onMoviesLoaded(movieList);
        PowerMock.replayAll();

        //when
        loadMoviesInteractor.run();

        //then
        PowerMock.verifyAll();
    }

    @Test
    public void callToOnMoviesLoadedAfterGetMoviesFromNetworkGateway()
            throws NetworkConnectionException {
        //given
        expect(localMovieGateway.obtainMovies()).andReturn(EMPTY_MOVIE_LIST);
        expect(networkMovieGateway.obtainMovies()).andReturn(movieList);
        localMovieGateway.persist(movieList);
        output.onMoviesLoaded(movieList);
        PowerMock.replayAll();

        //when
        loadMoviesInteractor.run();

        //then
        PowerMock.verifyAll();
    }

    @Test
    public void callToOnLoadMoviesErrorWhenThereIsNoInternetNeitherLocalMovies()
            throws NetworkConnectionException {
        //given
        expect(localMovieGateway.obtainMovies()).andReturn(EMPTY_MOVIE_LIST);
        expect(networkMovieGateway.obtainMovies()).andThrow(new NetworkConnectionException(""));
        output.onLoadMoviesError();

        PowerMock.replayAll();

        //when
        loadMoviesInteractor.run();

        //then
        PowerMock.verifyAll();
    }

    private List<Movie> createMovieList() {
        List<Movie> newList = new ArrayList<>();
        newList.add(new Movie.Builder().build());
        return newList;
    }
}
