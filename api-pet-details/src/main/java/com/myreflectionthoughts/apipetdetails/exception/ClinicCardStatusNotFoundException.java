package com.myreflectionthoughts.apipetdetails.exception;

public class ClinicCardStatusNotFoundException extends  RuntimeException{
    private String exceptionMessage;

    public ClinicCardStatusNotFoundException(String exceptionMessage){
        super(exceptionMessage);
    }
}
