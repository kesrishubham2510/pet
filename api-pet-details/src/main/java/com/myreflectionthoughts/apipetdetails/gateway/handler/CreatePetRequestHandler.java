package com.myreflectionthoughts.apipetdetails.gateway.handler;

import com.myreflectionthoughts.apipetdetails.core.usecase.CreatePetUseCase;
import com.myreflectionthoughts.library.dto.request.AddPetDTO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
public class CreatePetRequestHandler extends Handler{

    private final CreatePetUseCase createPetUseCase;

    public CreatePetRequestHandler(CreatePetUseCase createPetUseCase) {
        this.createPetUseCase = createPetUseCase;
    }

    public Mono<ServerResponse> addPet(ServerRequest serverRequest){
        Mono<AddPetDTO> addPetDTOMono = serverRequest.bodyToMono(AddPetDTO.class);
        return  createPetUseCase
                .addPet(addPetDTOMono)
                .flatMap(response-> ServerResponse.status(HttpStatus.CREATED).bodyValue(response))
                .onErrorResume(exceptionMapper);
    }
}
