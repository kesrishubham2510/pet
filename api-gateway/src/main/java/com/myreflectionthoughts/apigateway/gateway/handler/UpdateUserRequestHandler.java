package com.myreflectionthoughts.apigateway.gateway.handler;

import com.myreflectionthoughts.apigateway.core.usecase.UpdateUserUseCase;
import com.myreflectionthoughts.library.dto.request.UpdateUserDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
public class UpdateUserRequestHandler extends Handler {

    private final UpdateUserUseCase updateUserUseCase;

    public UpdateUserRequestHandler(UpdateUserUseCase updateUserUseCase) {
        this.updateUserUseCase = updateUserUseCase;
    }

    public Mono<ServerResponse> updateUser(ServerRequest serverRequest) {
        Mono<UpdateUserDTO> updateUserDTOMono = serverRequest.bodyToMono(UpdateUserDTO.class);
        return updateUserUseCase.updateUser(updateUserDTOMono)
                .flatMap(updatedUser -> ServerResponse.ok().bodyValue(updatedUser))
                .onErrorResume(exceptionMapper);
    }
}
