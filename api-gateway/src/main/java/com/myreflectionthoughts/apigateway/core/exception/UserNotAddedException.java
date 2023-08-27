package com.myreflectionthoughts.apigateway.core.exception;

public class UserNotAddedException extends RuntimeException{
    public UserNotAddedException(String message) {
        super(message);
    }
}
