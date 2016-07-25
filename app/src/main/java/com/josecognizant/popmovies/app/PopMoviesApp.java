package com.josecognizant.popmovies.app;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.josecognizant.popmovies.app.dependencies.ApplicationComponent;
import com.josecognizant.popmovies.app.dependencies.ApplicationModule;
import com.josecognizant.popmovies.app.dependencies.DaggerApplicationComponent;

/**
 * Initializes the some components
 * Created by Jose on 24/07/2016.
 */
public class PopMoviesApp extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeApp();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    private void initializeApp() {
        initializeInjector();
        initializeStetho();
    }

    private void initializeStetho() {
        Stetho.initializeWithDefaults(this);
    }

    private void initializeInjector() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }
}
