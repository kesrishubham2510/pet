package com.myreflectionthoughts.apimasterdetails.gateway.handler;

import com.myreflectionthoughts.apimasterdetails.core.usecase.UpdateMasterDetailsUseCase;
import com.myreflectionthoughts.apimasterdetails.core.utils.LogUtility;
import com.myreflectionthoughts.library.dto.request.UpdateMasterDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class UpdateMasterRequestHandler extends Handler {

    private final UpdateMasterDetailsUseCase updateMasterDetailsUseCase;
    private final Logger logger;

    public UpdateMasterRequestHandler(UpdateMasterDetailsUseCase updateMasterDetailsUseCase) {
        this.updateMasterDetailsUseCase = updateMasterDetailsUseCase;
        logger = Logger.getLogger(UpdateMasterRequestHandler.class.getName());
    }

    public Mono<ServerResponse> updateMasterDetails(ServerRequest serverRequest){
        Mono<UpdateMasterDTO> updateMasterDTOMono = serverRequest.bodyToMono(UpdateMasterDTO.class);

        LogUtility.loggerUtility.logEntry(logger, "Initiating update master request processing...");

        return updateMasterDetailsUseCase
                .updateMasterDetails(updateMasterDTOMono)
                .doOnNext(updatedMaster-> LogUtility.loggerUtility.log(logger, "Master:- "+updatedMaster.getId()+" updated successfully...", Level.INFO))
                .doOnNext(updatedMaster-> LogUtility.loggerUtility.log(logger, "Response:- "+updatedMaster, Level.FINE))
                .flatMap(response-> ServerResponse.ok().bodyValue(response))
                .doOnNext(generatedResponse-> LogUtility.loggerUtility.log(logger, "Update master request processed with successful response generation", Level.INFO))
                .onErrorResume(exceptionMapper);
    }
}