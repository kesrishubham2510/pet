package com.myreflectionthoughts.apigateway.gateway.handler;

import com.myreflectionthoughts.apigateway.core.usecase.RegisterUserUseCase;
import com.myreflectionthoughts.apigateway.core.utils.LogUtility;
import com.myreflectionthoughts.library.dto.request.AddUserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class RegisterUserRequestHandler extends Handler {

    private final RegisterUserUseCase registerUserUseCase;
    private final Logger logger;

    public RegisterUserRequestHandler(RegisterUserUseCase registerUserUseCase) {
        this.registerUserUseCase = registerUserUseCase;
        logger = Logger.getLogger(RegisterUserRequestHandler.class.getName());
    }

    public Mono<ServerResponse> addUser(ServerRequest serverRequest) {
        LogUtility.loggerUtility.logEntry(logger, "Initiating Add User request...");
        return registerUserUseCase
                .registerUser(serverRequest.bodyToMono(AddUserDTO.class))
                .doOnNext(registeredUser-> LogUtility.loggerUtility.log(logger, "User:- "+registeredUser.getMaster().getId()+" registered", Level.INFO))
                .doOnNext(registeredUser-> LogUtility.loggerUtility.log(logger, "Response:- "+registeredUser, Level.FINE))
                .flatMap(userDTO -> ServerResponse.status(HttpStatus.CREATED).bodyValue(userDTO))
                .doOnNext(generatedResponse-> LogUtility.loggerUtility.log(logger, "User registered with successfull response generation", Level.INFO))
                .onErrorResume(exceptionMapper);
    }
}
