package com.myreflectionthoughts.apipetdetails.exception;

public class PetNotFoundException extends  RuntimeException{
    private String exceptionMessage;

    public PetNotFoundException(String exceptionMessage){
        super(exceptionMessage);
    }
}
