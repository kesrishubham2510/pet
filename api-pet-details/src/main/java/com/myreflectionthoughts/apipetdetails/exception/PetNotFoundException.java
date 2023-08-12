package com.myreflectionthoughts.apipetdetails.exception;

public class PetNotFoundException extends  RuntimeException{
    public PetNotFoundException(String exceptionMessage){
        super(exceptionMessage);
    }
}
