package com.myreflectionthoughts.apigateway.gateway.handler;

import com.myreflectionthoughts.apigateway.core.usecase.RetrieveAllPetsOfMasterUseCase;
import com.myreflectionthoughts.apigateway.core.utils.LogUtility;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import io.micrometer.context.ContextRegistry;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class RetrieveAllPetsOfMasterRequestHandler {

    private final RetrieveAllPetsOfMasterUseCase retrieveAllPetsOfMasterUseCase;
    private final Logger logger;

    public RetrieveAllPetsOfMasterRequestHandler(RetrieveAllPetsOfMasterUseCase retrieveAllPetsOfMasterUseCase) {
        this.retrieveAllPetsOfMasterUseCase = retrieveAllPetsOfMasterUseCase;
        logger = Logger.getLogger(RetrieveAllPetsOfMasterRequestHandler.class.getName());
    }

    public Mono<ServerResponse> handleRetrieveAllPetsOfMasterRequest(ServerRequest serverRequest) {
        Mono<String> masterId = Mono.just(serverRequest.pathVariable("masterId"));

        LogUtility.loggerUtility.logEntry(logger, "Initiating retrieve-all-pets-of-user request processing");
        return ServerResponse.ok().header("traceId",
                        Optional.ofNullable((String) ContextRegistry.getInstance()
                                        .getThreadLocalAccessors()
                                        .stream()
                                        .filter(threadLocalAccessor ->
                                                threadLocalAccessor.key().equals("traceId")
                                        ).toList()
                                        .get(0)
                                        .getValue())
                                .orElse("Custom-trace-id")).contentType(MediaType.TEXT_EVENT_STREAM)
                .body(retrieveAllPetsOfMasterUseCase.getAllPetsOfMaster(masterId), PetDTO.class);
    }
}
