package com.myreflectionthoughts.apipetdetails.gateway.handler;

import com.myreflectionthoughts.apipetdetails.core.usecase.ReadPetsOfMasterUseCase;
import com.myreflectionthoughts.apipetdetails.core.utils.LogUtility;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class GetPetsOfMasterRequestHandler {

    private final ReadPetsOfMasterUseCase readAllPetsOfMasterUseCase;
    private final Logger logger;

    public GetPetsOfMasterRequestHandler(ReadPetsOfMasterUseCase readAllPetsOfMasterUseCase) {
        this.readAllPetsOfMasterUseCase = readAllPetsOfMasterUseCase;
        this.logger = Logger.getLogger(GetPetsOfMasterRequestHandler.class.getName());
    }

    public Mono<ServerResponse> retrievePetsOfMaster(ServerRequest serverRequest) {
        Mono<String> masterId = Mono.just(serverRequest.pathVariable("masterId"));

        LogUtility.loggerUtility.logEntry(logger, "Initiating get master's pets request processing...");

        return ServerResponse.ok().contentType(MediaType.APPLICATION_NDJSON)
                .body(readAllPetsOfMasterUseCase.retrieveAllPetsOfMaster(masterId), PetDTO.class);
    }
}