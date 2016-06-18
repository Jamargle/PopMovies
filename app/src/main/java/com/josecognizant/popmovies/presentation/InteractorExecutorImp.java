package com.josecognizant.popmovies.presentation;


import com.josecognizant.popmovies.domain.interactor.Interactor;

import java.util.concurrent.ExecutorService;

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
