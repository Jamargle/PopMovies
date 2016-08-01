package com.josecognizant.popmovies.domain.exception;

public class NetworkConnectionException extends Exception {
    private final String message;

    public NetworkConnectionException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
