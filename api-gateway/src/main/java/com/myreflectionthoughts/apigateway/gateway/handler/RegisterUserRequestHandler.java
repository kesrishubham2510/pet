package com.myreflectionthoughts.apigateway.gateway.handler;

import com.myreflectionthoughts.apigateway.core.usecase.RegisterUserUseCase;
import com.myreflectionthoughts.library.dto.request.AddUserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
public class RegisterUserRequestHandler {

    private final RegisterUserUseCase registerUserUseCase;

    public RegisterUserRequestHandler(RegisterUserUseCase registerUserUseCase) {
        this.registerUserUseCase = registerUserUseCase;
    }

    public Mono<ServerResponse> addUser(ServerRequest serverRequest){
        return registerUserUseCase
                .registerUser(serverRequest.bodyToMono(AddUserDTO.class))
                .flatMap(userDTO -> ServerResponse.status(HttpStatus.CREATED).bodyValue(userDTO));
    }
}
