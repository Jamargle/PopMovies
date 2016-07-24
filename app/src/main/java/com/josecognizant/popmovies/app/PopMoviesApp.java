package com.josecognizant.popmovies.app;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 *
 * Created by Jose on 24/07/2016.
 */
public class PopMoviesApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initializeApp();
    }

    private void initializeApp() {
        initializeStetho();
    }

    private void initializeStetho() {
        Stetho.initializeWithDefaults(this);
    }
}
