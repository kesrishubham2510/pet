package com.myreflectionthoughts.apimasterdetails.gateway.handler;

import com.myreflectionthoughts.apimasterdetails.core.usecase.ReadMasterDetailsUseCase;
import com.myreflectionthoughts.apimasterdetails.core.utils.LogUtility;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class GetMasterRequestHandler extends Handler {

    private final ReadMasterDetailsUseCase readMasterDetailsUseCase;
    private final Logger logger;

    public GetMasterRequestHandler(ReadMasterDetailsUseCase readMasterDetailsUseCase) {
        this.readMasterDetailsUseCase = readMasterDetailsUseCase;
        logger = Logger.getLogger(GetMasterRequestHandler.class.getName());
    }

    public Mono<ServerResponse> getMasterDetails(ServerRequest serverRequest){
        String masterId = serverRequest.pathVariable("masterId");

        LogUtility.loggerUtility.logEntry(logger, "Initiating get master info request processing...");

        return readMasterDetailsUseCase
                .readMasterDetails(Mono.just(masterId))
                .doOnNext(retrievedMaster-> LogUtility.loggerUtility.log(logger, "Master:- "+retrievedMaster.getId()+" info retrieved successfully", Level.INFO))
                .doOnNext(retrievedMaster-> LogUtility.loggerUtility.log(logger, "Response:- "+retrievedMaster, Level.FINE))
                .flatMap(response-> ServerResponse.ok().bodyValue(response))
                .doOnNext(generatedResponse-> LogUtility.loggerUtility.log(logger, "pet info retrieval request processed with successfull response generation", Level.INFO))
                .onErrorResume(exceptionMapper);
    }
}