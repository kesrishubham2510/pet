package com.myreflectionthoughts.apipetdetails.exception;

public class GenderNotFoundException extends RuntimeException {
    private String exceptionMessage;

    public GenderNotFoundException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
