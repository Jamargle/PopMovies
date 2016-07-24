package com.josecognizant.popmovies.app.dependencies;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Main component for dependency injection with Dagger 2
 * Created by Jose on 24/07/2016.
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    Context context();

    SharedPreferences sharedPreferences();
}
