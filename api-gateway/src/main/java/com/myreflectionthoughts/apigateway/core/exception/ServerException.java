package com.myreflectionthoughts.apigateway.core.exception;

public class ServerException extends RuntimeException{
    public ServerException(String message) {
        super(message);
    }
}
