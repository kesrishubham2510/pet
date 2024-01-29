package com.myreflectionthoughts.apigateway.gateway.handler;

import com.myreflectionthoughts.apigateway.core.usecase.UpdatePetDetailsUseCase;
import com.myreflectionthoughts.apigateway.core.utils.LogUtility;
import com.myreflectionthoughts.library.dto.request.UpdatePetDTO;
import io.micrometer.context.ContextRegistry;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class UpdatePetDetailsRequestHandler extends Handler{

     private final UpdatePetDetailsUseCase updatePetDetailsUseCase;
     private final Logger logger;

    public UpdatePetDetailsRequestHandler(UpdatePetDetailsUseCase updatePetDetailsUseCase) {
        this.updatePetDetailsUseCase = updatePetDetailsUseCase;
        logger = Logger.getLogger(UpdatePetDetailsRequestHandler.class.getName());
    }

    public Mono<ServerResponse> updatePetDetails(ServerRequest serverRequest){
        Mono<UpdatePetDTO> updatePetDTOMono = serverRequest.bodyToMono(UpdatePetDTO.class);
        LogUtility.loggerUtility.logEntry(logger, "Initiating update-pet-detail request processing...");
        return updatePetDetailsUseCase
                .updatePet(updatePetDTOMono)
                .doOnNext(updatedPet-> LogUtility.loggerUtility.log(logger, "Pet:- "+updatedPet.getId()+" updated successfully", Level.INFO))
                .doOnNext(updatedPet-> LogUtility.loggerUtility.log(logger,"Response:- "+updatedPet, Level.INFO))
                .flatMap(updatedPetResponse-> ServerResponse.ok().header("traceId",
                        Optional.ofNullable((String) ContextRegistry.getInstance()
                                        .getThreadLocalAccessors()
                                        .stream()
                                        .filter(threadLocalAccessor ->
                                                threadLocalAccessor.key().equals("traceId")
                                        ).toList()
                                        .get(0)
                                        .getValue())
                                .orElse("Custom-trace-id")).bodyValue(updatedPetResponse))
                .doOnNext(generatedResponse-> LogUtility.loggerUtility.log(logger, "update-pet request completed with successful response generation", Level.INFO))
                .onErrorResume(exceptionMapper);
    }
}
