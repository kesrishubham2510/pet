package com.myreflectionthoughts.apipetdetails.core.exception;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(String exceptionMessage) {
        super(exceptionMessage);
    }
}