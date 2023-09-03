package com.myreflectionthoughts.apigateway.gateway.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myreflectionthoughts.library.dto.response.ExceptionResponse;
import com.myreflectionthoughts.library.exception.ParameterMissingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
public class Handler {

    Function<Throwable, Mono<ServerResponse>> exceptionMapper = (exception) -> {
        ExceptionResponse exceptionResponse = new ExceptionResponse();

        if (exception instanceof WebClientResponseException) {
            Map<String, String> errorBody = errorBodyParser(((WebClientResponseException) exception).getResponseBodyAsString());
            exceptionResponse.setError(errorBody.get("error"));
            exceptionResponse.setErrorMessage(errorBody.get("errorMessage"));
        } else if (exception instanceof ParameterMissingException) {
            exceptionResponse.setError("Parameter is missing");
            exceptionResponse.setErrorMessage(exception.getMessage());
        } else {
            System.out.println(exception.toString());
            exceptionResponse.setError("!! Oops !!");
            exceptionResponse.setErrorMessage(" Something Went Wrong, please try later");
        }

        return ServerResponse.badRequest().bodyValue(exceptionResponse);

    };

    private Map<String, String> errorBodyParser(String errorBody){

        log.info(errorBody);
        Map<String, String> parsedJson = new HashMap<>();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            parsedJson = objectMapper.readValue(errorBody, Map.class);
        } catch (Exception ex) {
            parsedJson.put("error", "Exception");
            parsedJson.put("errorMessage", "Something went wrong, please try again later");
        }

        return parsedJson;
    }

}
