package com.myreflectionthoughts.apipetdetails.core.utility;

import com.myreflectionthoughts.apipetdetails.core.entity.Pet;
import com.myreflectionthoughts.apipetdetails.core.exception.PetNotFoundException;
import com.myreflectionthoughts.library.dto.response.ExceptionResponse;
import org.modelmapper.MappingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MappingException.class)
    public ResponseEntity<ExceptionResponse> handleMappingException(MappingException mapException){
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setError(mapException.getCause().getClass().getSimpleName());
        exceptionResponse.setErrorMessage(mapException.getCause().getMessage());
        return ResponseEntity.badRequest().body(exceptionResponse);
    }

    @ExceptionHandler(PetNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handlePetNotFoundException(PetNotFoundException petNotFoundException){
      ExceptionResponse exceptionResponse = new ExceptionResponse();
      exceptionResponse.setError(PetNotFoundException.class.getSimpleName());
      exceptionResponse.setErrorMessage(petNotFoundException.getMessage());
      return ResponseEntity.badRequest().body(exceptionResponse);

    }


}
