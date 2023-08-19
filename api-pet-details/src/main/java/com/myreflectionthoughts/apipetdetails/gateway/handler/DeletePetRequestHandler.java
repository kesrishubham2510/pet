package com.myreflectionthoughts.apipetdetails.gateway.handler;

import com.myreflectionthoughts.apipetdetails.core.usecase.DeletePetDetailsUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
public class DeletePetRequestHandler extends Handler{

    @Autowired
    private DeletePetDetailsUseCase deletePetDetailsUseCase;

    public Mono<ServerResponse> deletePet(ServerRequest serverRequest){
        String petId = serverRequest.pathVariable("petId");
        return deletePetDetailsUseCase.deletePet(Mono.just(petId))
                .flatMap(petDetailsResponse-> ServerResponse.ok().bodyValue(petDetailsResponse))
                .onErrorResume(exceptionMapper);
    }
}
