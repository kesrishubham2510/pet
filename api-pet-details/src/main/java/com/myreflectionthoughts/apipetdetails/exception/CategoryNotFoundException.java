package com.myreflectionthoughts.apipetdetails.exception;

public class CategoryNotFoundException extends RuntimeException {
    String exceptionMessage;

    public CategoryNotFoundException(String exceptionMessage) {
        super(exceptionMessage);
    }
}