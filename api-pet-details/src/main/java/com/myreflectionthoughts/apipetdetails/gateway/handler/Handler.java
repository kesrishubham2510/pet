package com.myreflectionthoughts.apipetdetails.gateway.handler;

import com.myreflectionthoughts.apipetdetails.core.exception.PetNotFoundException;
import com.myreflectionthoughts.apipetdetails.core.utils.LogUtility;
import com.myreflectionthoughts.library.dto.response.ExceptionResponse;
import com.myreflectionthoughts.library.exception.InputDataException;
import com.myreflectionthoughts.library.exception.ParameterMissingException;
import io.micrometer.core.instrument.MeterRegistry;
import org.modelmapper.MappingException;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Handler{

    private static final Logger logger;
    private MeterRegistry apiPetDetailsRegistry;

    static {
        logger = Logger.getLogger(Handler.class.getName());
    }

    public Handler(MeterRegistry apiPetDetailsRegistry){
        this.apiPetDetailsRegistry = apiPetDetailsRegistry;
    }

    protected Function<Throwable, Mono<ServerResponse>> exceptionMapper = (ex)->{

        ExceptionResponse exceptionResponse = new ExceptionResponse();

        // covers GenderNotFoundException & ClinicCardStatusNotFoundException
        if(ex instanceof MappingException){
            exceptionResponse.setError(ex.getCause().getClass().getSimpleName());
            exceptionResponse.setErrorMessage(ex.getCause().getMessage());
            LogUtility.loggerUtility.log(logger, "MappingException occurred...", Level.SEVERE);
            LogUtility.loggerUtility.log(logger, "Exception :- "+ex.getMessage(), Level.INFO);
            apiPetDetailsRegistry.counter(MappingException.class.getSimpleName()).increment();

        }else if(ex instanceof PetNotFoundException){
            exceptionResponse.setError(PetNotFoundException.class.getSimpleName());
            exceptionResponse.setErrorMessage(ex.getMessage());
            LogUtility.loggerUtility.log(logger, "PetNotFoundException occurred...", Level.SEVERE);
            LogUtility.loggerUtility.log(logger, "Exception :- "+ex.getMessage(), Level.INFO);
            apiPetDetailsRegistry.counter(PetNotFoundException.class.getSimpleName()).increment();

        } else if (ex instanceof InputDataException) {
            exceptionResponse.setError(InputDataException.class.getSimpleName());
            exceptionResponse.setErrorMessage(ex.getMessage());
            LogUtility.loggerUtility.log(logger, "InputDataException occurred...", Level.SEVERE);
            LogUtility.loggerUtility.log(logger, "Exception :- "+ex.getMessage(), Level.INFO);
            apiPetDetailsRegistry.counter(InputDataException.class.getSimpleName()).increment();

        } else if (ex instanceof ParameterMissingException) {
            exceptionResponse.setError(ParameterMissingException.class.getSimpleName());
            exceptionResponse.setErrorMessage(ex.getMessage());
            LogUtility.loggerUtility.log(logger, "ParameterMissingException occurred...", Level.SEVERE);
            LogUtility.loggerUtility.log(logger, "Exception :- "+ex.getMessage(), Level.INFO);
            apiPetDetailsRegistry.counter(ParameterMissingException.class.getSimpleName()).increment();
        }

        LogUtility.loggerUtility.log(logger, "Exception occurred while processing request: "+ex, Level.SEVERE);
        LogUtility.loggerUtility.log(logger, "Exception response generated: "+exceptionResponse, Level.FINE);

        return ServerResponse.badRequest().bodyValue(exceptionResponse);
    };
}
