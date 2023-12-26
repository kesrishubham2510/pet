package com.myreflectionthoughts.apipetdetails.gateway.handler;

import com.myreflectionthoughts.apipetdetails.core.usecase.ReadPetDetailsUseCase;
import com.myreflectionthoughts.apipetdetails.core.utils.LogUtility;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class GetPetRequestHandler extends Handler{
    private final ReadPetDetailsUseCase readPetDetailsUseCase;
    private final Logger logger;

    public GetPetRequestHandler(ReadPetDetailsUseCase readPetDetailsUseCase, MeterRegistry apiPetDetailsRegistry) {
        super(apiPetDetailsRegistry);
        this.readPetDetailsUseCase = readPetDetailsUseCase;
        this.logger = Logger.getLogger(GetPetRequestHandler.class.getName());
    }

    public Mono<ServerResponse> getPet(ServerRequest serverRequest){
        String petId = serverRequest.pathVariable("petId");

        LogUtility.loggerUtility.logEntry(logger, "Initiating get pet info request processing...");

        return readPetDetailsUseCase
                .getDetails(Mono.just(petId))
                .doOnNext(retrievedPet-> LogUtility.loggerUtility.log(logger, "Pet:- "+retrievedPet.getId()+" info retrieved successfully", Level.INFO))
                .doOnNext(retrievedPet-> LogUtility.loggerUtility.log(logger, "Response:- "+retrievedPet, Level.FINE))
                .flatMap(petDetailResponse-> ServerResponse.ok().bodyValue(petDetailResponse))
                .doOnNext(generatedResponse-> LogUtility.loggerUtility.log(logger, "pet info retrieval request processed with successfull response generation", Level.INFO))
                .onErrorResume(exceptionMapper);
    }
}
