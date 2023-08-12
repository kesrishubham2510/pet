package com.myreflectionthoughts.apipetdetails.utility;

import com.myreflectionthoughts.library.dto.response.ExceptionResponse;
import org.modelmapper.MappingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MappingException.class)
    public ResponseEntity<ExceptionResponse> handleMappingExceptio(MappingException mapException){
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setError(mapException.getCause().getClass().getSimpleName());
        exceptionResponse.setErrorMessage(mapException.getCause().getMessage());
        return ResponseEntity.badRequest().body(exceptionResponse);
    }


}
