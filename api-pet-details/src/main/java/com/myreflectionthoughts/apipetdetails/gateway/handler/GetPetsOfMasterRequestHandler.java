package com.myreflectionthoughts.apipetdetails.gateway.handler;

import com.myreflectionthoughts.apipetdetails.core.usecase.ReadPetsOfMasterUseCase;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
public class GetPetsOfMasterRequestHandler {

    private final ReadPetsOfMasterUseCase readAllPetsOfMasterUseCase;

    public GetPetsOfMasterRequestHandler(ReadPetsOfMasterUseCase readAllPetsOfMasterUseCase) {
        this.readAllPetsOfMasterUseCase = readAllPetsOfMasterUseCase;
    }

    public Mono<ServerResponse> retrievePetsOfMaster(ServerRequest serverRequest) {
        Mono<String> masterId = Mono.just(serverRequest.pathVariable("masterId"));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_NDJSON)
                .body(readAllPetsOfMasterUseCase.retrieveAllPetsOfMaster(masterId), PetDTO.class);
    }
}