package com.myreflectionthoughts.apigateway.gateway.handler;

import com.myreflectionthoughts.apigateway.core.usecase.RetrieveAllPetsOfMasterUseCase;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
public class RetrieveAllPetsOfMasterRequestHandler {

    private final RetrieveAllPetsOfMasterUseCase retrieveAllPetsOfMasterUseCase;

    public RetrieveAllPetsOfMasterRequestHandler(RetrieveAllPetsOfMasterUseCase retrieveAllPetsOfMasterUseCase) {
        this.retrieveAllPetsOfMasterUseCase = retrieveAllPetsOfMasterUseCase;
    }

    public Mono<ServerResponse> handleRetrieveAllPetsOfMasterRequest(ServerRequest serverRequest) {
        Mono<String> masterId = Mono.just(serverRequest.pathVariable("masterId"));
        return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM)
                .body(retrieveAllPetsOfMasterUseCase.getAllPetsOfMaster(masterId), PetDTO.class);
    }
}
