package com.josecognizant.popmovies.app.dependencies;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.josecognizant.popmovies.app.PopMoviesApp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Main module for dependency injection with Dagger 2
 * Created by Jose on 24/07/2016.
 */
@Module
public class ApplicationModule {

    private final PopMoviesApp application;

    public ApplicationModule(PopMoviesApp application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return application;
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }
}
