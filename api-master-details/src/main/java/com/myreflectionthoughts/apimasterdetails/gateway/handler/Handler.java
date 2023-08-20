package com.myreflectionthoughts.apimasterdetails.gateway.handler;

import com.myreflectionthoughts.apimasterdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apimasterdetails.core.exception.MasterNotFoundException;
import com.myreflectionthoughts.library.dto.response.ExceptionResponse;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public class Handler {

    protected Function<Throwable, Mono<ServerResponse>> exceptionMapper = (exception)->{
        ExceptionResponse exceptionResponse = new ExceptionResponse();

        if(exception instanceof MasterNotFoundException){
            exceptionResponse.setError(MasterNotFoundException.class.getSimpleName());
            exceptionResponse.setErrorMessage(ServiceConstants.MASTER_NOT_FOUND_EXCEPTION);
        }

        return ServerResponse.badRequest().bodyValue(exceptionResponse);

    };
}
