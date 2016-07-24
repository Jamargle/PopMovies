package com.josecognizant.popmovies.presentation;

import com.josecognizant.popmovies.domain.interactor.Interactor;

import java.util.concurrent.ExecutorService;

/**
 * Class responsible of execute all interactors in a concurrent way
 * Created by Jose on 24/05/2016.
 */
public class InteractorExecutorImp implements InteractorExecutor {

    private ExecutorService executorService;

    public InteractorExecutorImp(ExecutorService executorService) {
        this.executorService = executorService;
    }

    @Override
    public void execute(Interactor interactor) {
        executorService.execute(interactor);
    }
}
