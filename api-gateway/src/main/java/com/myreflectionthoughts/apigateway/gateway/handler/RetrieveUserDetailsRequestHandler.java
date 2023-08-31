package com.myreflectionthoughts.apigateway.gateway.handler;

import com.myreflectionthoughts.apigateway.core.usecase.RetrieveUserUseCase;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
public class RetrieveUserDetailsRequestHandler extends Handler {

    private final RetrieveUserUseCase retrieveUserUseCase;

    public RetrieveUserDetailsRequestHandler(RetrieveUserUseCase retrieveUserUseCase) {
        this.retrieveUserUseCase = retrieveUserUseCase;
    }

    public Mono<ServerResponse> retrieveUser(ServerRequest serverRequest) {

        Mono<String> masterId = Mono.just(serverRequest.pathVariable("userId"));

        return retrieveUserUseCase.retrieveUser(masterId)
                .flatMap(userInfo -> ServerResponse.ok().bodyValue(userInfo))
                .onErrorResume(exceptionMapper);
    }
}
