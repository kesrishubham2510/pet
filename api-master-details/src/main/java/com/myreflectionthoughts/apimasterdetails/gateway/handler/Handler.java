package com.myreflectionthoughts.apimasterdetails.gateway.handler;

import com.myreflectionthoughts.apimasterdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apimasterdetails.core.exception.MasterNotFoundException;
import com.myreflectionthoughts.library.dto.response.ExceptionResponse;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public class Handler {

    protected Function<Throwable, Mono<ServerResponse>> exceptionMapper = (exception)->{
        ExceptionResponse exceptionResponse = new ExceptionResponse();

        if(exception instanceof MasterNotFoundException){
            exceptionResponse.setError(MasterNotFoundException.class.getSimpleName());
            exceptionResponse.setErrorMessage(exception.getMessage());
        }else if(exception instanceof DuplicateKeyException){
            exceptionResponse.setError("EmailAlreadyExists");
            exceptionResponse.setErrorMessage(ServiceConstants.EMAIL_ALREADY_REGISTERED);
        }else{
            exceptionResponse.setError("!! Something Went Wrong !!");
            exceptionResponse.setErrorMessage("Please try again later");
        }

        return ServerResponse.badRequest().bodyValue(exceptionResponse);
    };
}
