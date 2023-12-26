package com.myreflectionthoughts.apipetdetails.gateway.handler;

import com.myreflectionthoughts.apipetdetails.core.usecase.DeletePetDetailsUseCase;
import com.myreflectionthoughts.apipetdetails.core.utils.LogUtility;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class DeletePetRequestHandler extends Handler{

    private final DeletePetDetailsUseCase deletePetDetailsUseCase;
    private final Logger logger;

    public DeletePetRequestHandler(DeletePetDetailsUseCase deletePetDetailsUseCase, MeterRegistry apiPetDetailsRegistry) {
        super(apiPetDetailsRegistry);
        this.deletePetDetailsUseCase = deletePetDetailsUseCase;
        logger = Logger.getLogger(DeletePetRequestHandler.class.getName());
    }

    public Mono<ServerResponse> deletePet(ServerRequest serverRequest){
        String petId = serverRequest.pathVariable("petId");
        LogUtility.loggerUtility.logEntry(logger, "Initiating delete pet request processing...");
        return deletePetDetailsUseCase
                .deletePet(Mono.just(petId))
                .doOnNext(deletedPet-> LogUtility.loggerUtility.log(logger, "Pet:- "+deletedPet.getId()+" deleted successfully...", Level.INFO))
                .doOnNext(deletedPet-> LogUtility.loggerUtility.log(logger, "Response:- "+deletedPet, Level.FINE))
                .flatMap(deletedPet-> ServerResponse.ok().bodyValue(deletedPet))
                .doOnNext(deletionResponse-> LogUtility.loggerUtility.log(logger, "Pet deletion request processed with successful response generation", Level.INFO))
                .onErrorResume(exceptionMapper);
    }
}
