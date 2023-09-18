package com.myreflectionthoughts.apipetdetails.gateway.handler;

import com.myreflectionthoughts.apipetdetails.core.usecase.UpdatePetDetailsUseCase;
import com.myreflectionthoughts.apipetdetails.core.utils.LogUtility;
import com.myreflectionthoughts.library.dto.request.UpdatePetDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class UpdatePetRequestHandler extends Handler{

    private final UpdatePetDetailsUseCase updatePetDetailsUseCase;
    private final Logger logger;

    public UpdatePetRequestHandler(UpdatePetDetailsUseCase updatePetDetailsUseCase) {
        this.updatePetDetailsUseCase = updatePetDetailsUseCase;
        logger = Logger.getLogger(UpdatePetRequestHandler.class.getName());
    }

    public Mono<ServerResponse> updatePet(ServerRequest serverRequest){
        Mono<UpdatePetDTO> updatePetDTOMono = serverRequest.bodyToMono(UpdatePetDTO.class);

        LogUtility.loggerUtility.logEntry(logger, "Initiating update pet request processing...");

        return updatePetDetailsUseCase
                .updatePetDetails(updatePetDTOMono)
                .doOnNext(updatePet-> LogUtility.loggerUtility.log(logger, "Pet:- "+updatePet.getId()+" updated successfully...", Level.INFO))
                .doOnNext(updatePet-> LogUtility.loggerUtility.log(logger, "Response:- "+updatePet, Level.FINE))
                .flatMap(updatedPetResponse-> ServerResponse.ok().bodyValue(updatedPetResponse))
                .doOnNext(generatedResponse-> LogUtility.loggerUtility.log(logger, "Update pet request processed with successful response generation", Level.INFO))
                .onErrorResume(exceptionMapper);
    }
}
