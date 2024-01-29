package com.myreflectionthoughts.apimasterdetails.gateway.handler;

import com.myreflectionthoughts.apimasterdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apimasterdetails.core.exception.MasterNotFoundException;
import com.myreflectionthoughts.library.dto.response.ExceptionResponse;
import com.myreflectionthoughts.library.exception.InputDataException;
import com.myreflectionthoughts.library.exception.ParameterMissingException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import com.myreflectionthoughts.apimasterdetails.core.utils.LogUtility;

import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Handler {

    private static final Logger logger;

    static {
        logger = Logger.getLogger(Handler.class.getName());
    }

    protected Function<Throwable, Mono<ServerResponse>> exceptionMapper = (exception) -> {
        ExceptionResponse exceptionResponse = new ExceptionResponse();

        if (exception instanceof MasterNotFoundException) {

            exceptionResponse.setError(MasterNotFoundException.class.getSimpleName());
            exceptionResponse.setErrorMessage(exception.getMessage());
            LogUtility.loggerUtility.log(logger, "MasterNotFoundException occurred...", Level.SEVERE);
            LogUtility.loggerUtility.log(logger, "Exception :- " + exception.getMessage(), Level.INFO);

        } else if (exception instanceof DuplicateKeyException) {

            exceptionResponse.setError("EmailAlreadyExists");
            exceptionResponse.setErrorMessage(ServiceConstants.EMAIL_ALREADY_REGISTERED);
            LogUtility.loggerUtility.log(logger, "DuplicateKeyException occurred...", Level.SEVERE);
            LogUtility.loggerUtility.log(logger, "Exception :- " + exception.getMessage(), Level.INFO);

        } else if (exception instanceof InputDataException) {

            exceptionResponse.setError(InputDataException.class.getSimpleName());
            exceptionResponse.setErrorMessage(exception.getMessage());

        } else if (exception instanceof ParameterMissingException) {

            exceptionResponse.setError(ParameterMissingException.class.getSimpleName());
            exceptionResponse.setErrorMessage(exception.getMessage());

        } else {

            exceptionResponse.setError("!! Something Went Wrong !!");
            exceptionResponse.setErrorMessage("Please try again later");

        }

        LogUtility.loggerUtility.log(logger, "Exception occurred while processing request: " + exception, Level.SEVERE);
        LogUtility.loggerUtility.log(logger, "Exception response generated: " + exceptionResponse, Level.FINE);

        return ServerResponse.badRequest().bodyValue(exceptionResponse);
    };
}
