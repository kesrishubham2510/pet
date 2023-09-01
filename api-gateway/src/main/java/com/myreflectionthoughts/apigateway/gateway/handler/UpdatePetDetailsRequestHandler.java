package com.myreflectionthoughts.apigateway.gateway.handler;

import com.myreflectionthoughts.apigateway.core.usecase.UpdatePetDetailsUseCase;
import com.myreflectionthoughts.library.dto.request.UpdatePetDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
public class UpdatePetDetailsRequestHandler extends Handler{

     private final UpdatePetDetailsUseCase updatePetDetailsUseCase;

    public UpdatePetDetailsRequestHandler(UpdatePetDetailsUseCase updatePetDetailsUseCase) {
        this.updatePetDetailsUseCase = updatePetDetailsUseCase;
    }

    public Mono<ServerResponse> updatePetDetails(ServerRequest serverRequest){
        Mono<UpdatePetDTO> updatePetDTOMono = serverRequest.bodyToMono(UpdatePetDTO.class);
        return updatePetDetailsUseCase.updatePet(updatePetDTOMono)
                .flatMap(updatedPetResponse-> ServerResponse.ok().bodyValue(updatedPetResponse))
                .onErrorResume(exceptionMapper);
    }
}
