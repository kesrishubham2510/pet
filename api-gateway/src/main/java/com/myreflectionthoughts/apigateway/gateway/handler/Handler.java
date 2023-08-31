package com.myreflectionthoughts.apigateway.gateway.handler;

import com.myreflectionthoughts.library.dto.response.ExceptionResponse;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Handler {

    Function<Throwable, Mono<ServerResponse>> exceptionMapper = (exception) -> {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        if (exception instanceof WebClientResponseException) {
            Map<String, String> errorBody = errorBodyParser(((WebClientResponseException) exception).getResponseBodyAsString());
            exceptionResponse.setError(errorBody.get("error"));
            exceptionResponse.setErrorMessage(errorBody.get("errorMessage"));
        } else {
            exceptionResponse.setError("!! Oops !!");
            exceptionResponse.setErrorMessage(" Something Went Wrong, please try later");
        }
        return ServerResponse.badRequest().bodyValue(exceptionResponse);
    };

    private Map<String, String> errorBodyParser(String errorBody) {
        Map<String, String> errorResponse = new HashMap<>();

        String[] keyValuePairs = errorBody.substring(1, errorBody.length() - 1).split(",");

        for (String keyValuePair : keyValuePairs)
            setKeyValuePair(keyValuePair, errorResponse);

        return errorResponse;
    }

    private void setKeyValuePair(String pair, Map<String, String> map) {
        String[] keyValue = pair.split(":");
        map.put(keyValue[0].substring(1, keyValue[0].length() - 1), keyValue[1].substring(1, keyValue[1].length() - 1));
    }
}
