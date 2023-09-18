package com.myreflectionthoughts.apimasterdetails.gateway.handler;

import com.myreflectionthoughts.apimasterdetails.core.usecase.DeleteMasterDetailsUseCase;
import com.myreflectionthoughts.apimasterdetails.core.utils.LogUtility;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class DeleteMasterRequestHandler extends Handler {

    private final DeleteMasterDetailsUseCase deleteMasterDetailsUseCase;
    private final Logger logger;

    public DeleteMasterRequestHandler(DeleteMasterDetailsUseCase deleteMasterDetailsUseCase) {
        this.deleteMasterDetailsUseCase = deleteMasterDetailsUseCase;
        logger = Logger.getLogger(DeleteMasterRequestHandler.class.getName());
    }

    public Mono<ServerResponse> deleteMasterDetails(ServerRequest serverRequest){
        String masterId = serverRequest.pathVariable("masterId");
        LogUtility.loggerUtility.logEntry(logger, "Initiating delete master request processing...");
        return deleteMasterDetailsUseCase
                .deleteMasterDetails(Mono.just(masterId))
                .doOnNext(deleteMasterDTO -> LogUtility.loggerUtility.log(logger, "Master:- "+deleteMasterDTO.getId()+" deleted successfully...", Level.INFO))
                .doOnNext(deleteMasterDTO -> LogUtility.loggerUtility.log(logger, "Response:- "+deleteMasterDTO, Level.FINE))
                .flatMap(response-> ServerResponse.ok().bodyValue(response))
                .doOnNext(generatedResponse-> LogUtility.loggerUtility.log(logger, "Master deletion request processed with successful response generation", Level.INFO))
                .onErrorResume(exceptionMapper);
    }
}