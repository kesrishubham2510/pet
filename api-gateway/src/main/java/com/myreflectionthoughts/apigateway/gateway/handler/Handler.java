package com.myreflectionthoughts.apigateway.gateway.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myreflectionthoughts.apigateway.core.utils.LogUtility;
import com.myreflectionthoughts.library.dto.response.ExceptionResponse;
import com.myreflectionthoughts.library.exception.ParameterMissingException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Handler {

    private static final Logger logger;

    static {
        logger = Logger.getLogger(Handler.class.getName());
    }

    Function<Throwable, Mono<ServerResponse>> exceptionMapper = (exception) -> {

        LogUtility.loggerUtility.logEntry(logger, "Exception occurred while processing request");

        ExceptionResponse exceptionResponse = new ExceptionResponse();

        if (exception instanceof WebClientResponseException) {

            LogUtility.loggerUtility.log(logger, "Service is down or it failed to process request", Level.SEVERE);
            Map<String, String> errorBody = errorBodyParser(((WebClientResponseException) exception).getResponseBodyAsString());
            exceptionResponse.setError(errorBody.get("error"));
            exceptionResponse.setErrorMessage(errorBody.get("errorMessage"));

        } else if (exception instanceof ParameterMissingException) {

            exceptionResponse.setError("Parameter is missing");
            exceptionResponse.setErrorMessage(exception.getMessage());
            LogUtility.loggerUtility.log(logger, "Request payload is missing a required parameter", Level.SEVERE);

        } else {
            exceptionResponse.setError("!! Oops !!");
            exceptionResponse.setErrorMessage(" Something Went Wrong, please try later");
            LogUtility.loggerUtility.log(logger, "Unhandled Exception:- " + exception, Level.SEVERE);
        }

        LogUtility.loggerUtility.log(logger, "Exception handled and response generated", Level.SEVERE);
        LogUtility.loggerUtility.log(logger, "Generated Exception Response:- "+exceptionResponse, Level.FINE);

        return ServerResponse.badRequest().bodyValue(exceptionResponse);
    };

    private Map<String, String> errorBodyParser(String errorBody) {

        LogUtility.loggerUtility.logEntry(logger, "Parsing exception from webclient:- " + errorBody, Level.FINE);
        Map<String, String> parsedJson = new HashMap<>();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            parsedJson = objectMapper.readValue(errorBody, Map.class);
        } catch (Exception ex) {
            parsedJson.put("error", "Exception");
            parsedJson.put("errorMessage", "Something went wrong, please try again later");
        }

        LogUtility.loggerUtility.logExit(logger, "Webclient exception parsed successfully");
        return parsedJson;
    }

}
