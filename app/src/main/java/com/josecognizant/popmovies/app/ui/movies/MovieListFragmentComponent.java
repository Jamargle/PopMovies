package com.josecognizant.popmovies.app.ui.movies;

import com.josecognizant.popmovies.app.dependencies.ApplicationComponent;
import com.josecognizant.popmovies.app.dependencies.scopes.FragmentScope;

import dagger.Component;

@FragmentScope
@Component(dependencies = ApplicationComponent.class, modules = MovieListFragmentModule.class)
public interface MovieListFragmentComponent {
    void inject(MovieListFragment fragment);
}
