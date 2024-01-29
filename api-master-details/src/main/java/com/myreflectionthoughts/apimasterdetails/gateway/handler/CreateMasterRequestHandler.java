package com.myreflectionthoughts.apimasterdetails.gateway.handler;

import com.myreflectionthoughts.apimasterdetails.core.usecase.CreateMasterUseCase;
import com.myreflectionthoughts.apimasterdetails.core.utils.LogUtility;
import com.myreflectionthoughts.library.dto.request.AddMasterDTO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class CreateMasterRequestHandler extends Handler{

    private final CreateMasterUseCase createMasterUseCase;
    private final Logger logger;

    public CreateMasterRequestHandler(CreateMasterUseCase createMasterUseCase) {
        this.createMasterUseCase = createMasterUseCase;
        logger = Logger.getLogger(CreateMasterRequestHandler.class.getName());
    }

    public Mono<ServerResponse> createMaster(ServerRequest serverRequest){
        Mono<AddMasterDTO> addMasterDTOMono = serverRequest.bodyToMono(AddMasterDTO.class);
        LogUtility.loggerUtility.logEntry(logger, "Initiating add master request processing...");
        return createMasterUseCase
                .createMaster(addMasterDTOMono)
                .doOnNext(addedMaster-> LogUtility.loggerUtility.log(logger, "Master:- "+addedMaster.getId()+" added successfully...", Level.INFO))
                .doOnNext(addedMaster-> LogUtility.loggerUtility.log(logger, "Response:- "+addedMaster, Level.FINE))
                .flatMap(response-> ServerResponse.status(HttpStatus.CREATED).bodyValue(response))
                .doOnNext(generatedResponse-> LogUtility.loggerUtility.log(logger, "Add master request processed with successful response generation", Level.INFO))
                .onErrorResume(exceptionMapper)
                .contextCapture();
    }
}