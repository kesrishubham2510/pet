package com.myreflectionthoughts.apipetdetails.gateway.handler;

import com.myreflectionthoughts.apipetdetails.core.exception.PetNotFoundException;
import com.myreflectionthoughts.library.dto.response.ExceptionResponse;
import org.modelmapper.MappingException;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public class Handler{
    protected Function<Throwable, Mono<ServerResponse>> exceptionMapper = (ex)->{
        ExceptionResponse exceptionResponse = new ExceptionResponse();

        if(ex instanceof MappingException){

            exceptionResponse.setError(ex.getCause().getClass().getSimpleName());
            exceptionResponse.setErrorMessage(ex.getCause().getMessage());

        }else if(ex instanceof PetNotFoundException){

            exceptionResponse.setError(PetNotFoundException.class.getSimpleName());
            exceptionResponse.setErrorMessage(ex.getMessage());

        }
        return ServerResponse.badRequest().bodyValue(exceptionResponse);
    };
}
