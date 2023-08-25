package com.myreflectionthoughts.apipetdetails.gateway.handler;

import com.myreflectionthoughts.apipetdetails.core.usecase.UpdatePetDetailsUseCase;
import com.myreflectionthoughts.library.dto.request.UpdatePetDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
public class UpdatePetRequestHandler extends Handler{

    private final UpdatePetDetailsUseCase updatePetDetailsUseCase;

    public UpdatePetRequestHandler(UpdatePetDetailsUseCase updatePetDetailsUseCase) {
        this.updatePetDetailsUseCase = updatePetDetailsUseCase;
    }

    public Mono<ServerResponse> updatePet(ServerRequest serverRequest){
        Mono<UpdatePetDTO> updatePetDTOMono = serverRequest.bodyToMono(UpdatePetDTO.class);
        return updatePetDetailsUseCase.updatePetDetails(updatePetDTOMono)
                .flatMap(updatedPetResponse-> ServerResponse.ok().bodyValue(updatedPetResponse))
                .onErrorResume(exceptionMapper);
    }
}
