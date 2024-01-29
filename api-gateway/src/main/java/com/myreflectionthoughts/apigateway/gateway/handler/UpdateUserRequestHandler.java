package com.myreflectionthoughts.apigateway.gateway.handler;

import com.myreflectionthoughts.apigateway.core.usecase.UpdateUserUseCase;
import com.myreflectionthoughts.apigateway.core.utils.LogUtility;
import com.myreflectionthoughts.library.dto.request.UpdateUserDTO;
import io.micrometer.context.ContextRegistry;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class UpdateUserRequestHandler extends Handler {

    private final UpdateUserUseCase updateUserUseCase;
    private final Logger logger;

    public UpdateUserRequestHandler(UpdateUserUseCase updateUserUseCase) {
        this.updateUserUseCase = updateUserUseCase;
        logger = Logger.getLogger(UpdateUserRequestHandler.class.getName());
    }

    public Mono<ServerResponse> updateUser(ServerRequest serverRequest) {
        Mono<UpdateUserDTO> updateUserDTOMono = serverRequest.bodyToMono(UpdateUserDTO.class);
        LogUtility.loggerUtility.logEntry(logger, "Initiating update-user request processing...");
        return updateUserUseCase.updateUser(updateUserDTOMono)
                .doOnNext(updatedUser-> LogUtility.loggerUtility.log(logger, "User:- "+updatedUser.getMaster().getId()+" has been updated successfully", Level.INFO))
                .doOnNext(updatedUser-> LogUtility.loggerUtility.log(logger, "Response:- "+updatedUser, Level.FINE))
                .flatMap(updatedUser -> ServerResponse.ok().header("traceId",
                        Optional.ofNullable((String) ContextRegistry.getInstance()
                                        .getThreadLocalAccessors()
                                        .stream()
                                        .filter(threadLocalAccessor ->
                                                threadLocalAccessor.key().equals("traceId")
                                        ).toList()
                                        .get(0)
                                        .getValue())
                                .orElse("Custom-trace-id")).bodyValue(updatedUser))
                .doOnNext(generatedResponse-> LogUtility.loggerUtility.log(logger, "Update user request processed with successful response generation" ,Level.INFO))
                .onErrorResume(exceptionMapper);
    }
}
