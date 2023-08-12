package com.myreflectionthoughts.apipetdetails.exception;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(String exceptionMessage) {
        super(exceptionMessage);
    }
}