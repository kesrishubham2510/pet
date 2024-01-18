package com.myreflectionthoughts.apipetdetails.gateway.handler;

import com.myreflectionthoughts.apipetdetails.core.usecase.CreatePetUseCase;
import com.myreflectionthoughts.apipetdetails.core.utils.LogUtility;
import com.myreflectionthoughts.library.dto.request.AddPetDTO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class CreatePetRequestHandler extends Handler{

    private final CreatePetUseCase createPetUseCase;
    private final Logger logger;

    public CreatePetRequestHandler(CreatePetUseCase createPetUseCase) {
        this.createPetUseCase = createPetUseCase;
        logger = Logger.getLogger(CreatePetRequestHandler.class.getName());
    }

    public Mono<ServerResponse> addPet(ServerRequest serverRequest){
        Mono<AddPetDTO> addPetDTOMono = serverRequest.bodyToMono(AddPetDTO.class);
        LogUtility.loggerUtility.logEntry(logger, "Initiating add pet request processing...");
        return  createPetUseCase
                .addPet(addPetDTOMono)
                .doOnNext(addedPet-> LogUtility.loggerUtility.log(logger, "Pet "+addedPet.getId()+" added successfully...", Level.INFO))
                .doOnNext(addedPet-> LogUtility.loggerUtility.log(logger, "Response:- "+addedPet, Level.FINE))
                .flatMap(addedPet-> ServerResponse.status(HttpStatus.CREATED).bodyValue(addedPet))
                .doOnNext(generatedResponse-> LogUtility.loggerUtility.log(logger, "Add pet request processed with successful response generation",Level.INFO))
                .onErrorResume(exceptionMapper)
                .contextCapture();
    }
}
