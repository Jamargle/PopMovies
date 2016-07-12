package com.josecognizant.popmovies.presentation;

import com.josecognizant.popmovies.domain.interactor.Interactor;

import java.util.concurrent.ExecutorService;

/**
 * Class responsible of execute all interactors in a concurrent way
 * Created by Jose on 24/05/2016.
 */
public class InteractorExecutor {

    private ExecutorService executorService;

    public InteractorExecutor(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public void execute(Interactor interactor) {
        executorService.execute(interactor);
    }
}
