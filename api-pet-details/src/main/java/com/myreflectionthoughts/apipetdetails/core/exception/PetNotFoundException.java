package com.myreflectionthoughts.apipetdetails.core.exception;

public class PetNotFoundException extends  RuntimeException{
    public PetNotFoundException(String exceptionMessage){
        super(exceptionMessage);
    }
}
