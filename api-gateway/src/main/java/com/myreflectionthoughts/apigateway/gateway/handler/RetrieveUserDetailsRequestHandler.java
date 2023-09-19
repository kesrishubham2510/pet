package com.myreflectionthoughts.apigateway.gateway.handler;

import com.myreflectionthoughts.apigateway.core.usecase.RetrieveUserUseCase;
import com.myreflectionthoughts.apigateway.core.utils.LogUtility;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class RetrieveUserDetailsRequestHandler extends Handler {

    private final RetrieveUserUseCase retrieveUserUseCase;
    private final Logger logger;

    public RetrieveUserDetailsRequestHandler(RetrieveUserUseCase retrieveUserUseCase) {
        this.retrieveUserUseCase = retrieveUserUseCase;
        logger = Logger.getLogger(RetrieveUserDetailsRequestHandler.class.getName());
    }

    public Mono<ServerResponse> retrieveUser(ServerRequest serverRequest) {

        Mono<String> masterId = Mono.just(serverRequest.pathVariable("userId"));
        LogUtility.loggerUtility.logEntry(logger, "Initiating retrieve-user-info request processing...");
        return retrieveUserUseCase.retrieveUser(masterId)
                .doOnNext(retrievedUser-> LogUtility.loggerUtility.log(logger, "User:- "+retrievedUser.getMaster().getId()+" info retrieved successfully", Level.INFO))
                .doOnNext(retrievedUser-> LogUtility.loggerUtility.log(logger, "Response:- "+retrievedUser, Level.FINE))
                .flatMap(userInfo -> ServerResponse.ok().bodyValue(userInfo))
                .doOnNext(generatedResponse-> LogUtility.loggerUtility.log(logger, "Retrieve user-info request processed with successful response generation", Level.INFO))
                .onErrorResume(exceptionMapper);
    }
}
