package com.myreflectionthoughts.apigateway.core;

public class UserNotAddedException extends RuntimeException{
    public UserNotAddedException(String message) {
        super(message);
    }
}
