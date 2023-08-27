package com.myreflectionthoughts.apigateway.core.exception;

public class ReceivedException extends RuntimeException{
    private final String error;
    private final String errorMessage;

    public ReceivedException(String error, String errorMessage) {
        super();
        this.error = error;
        this.errorMessage = errorMessage;
    }
}
