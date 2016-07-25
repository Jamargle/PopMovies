package com.josecognizant.popmovies.app.ui.details;

import com.josecognizant.popmovies.app.dependencies.ApplicationComponent;
import com.josecognizant.popmovies.app.dependencies.scopes.FragmentScope;

import dagger.Component;

@FragmentScope
@Component(dependencies = ApplicationComponent.class, modules = MovieDetailsFragmentModule.class)
public interface MovieDetailsFragmentComponent {
    void inject(MovieDetailsFragment fragment);
}