package com.myreflectionthoughts.apipetdetails.gateway.handlers;

import com.myreflectionthoughts.apipetdetails.core.usecase.UpdatePetDetailsUseCase;
import com.myreflectionthoughts.library.dto.request.UpdatePetDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
public class UpdatePetRequestHandler extends Handler{

   @Autowired
    private UpdatePetDetailsUseCase updatePetDetailsUseCase;

    public Mono<ServerResponse> updatePet(ServerRequest serverRequest){
        Mono<UpdatePetDTO> updatePetDTOMono = serverRequest.bodyToMono(UpdatePetDTO.class);
        return updatePetDetailsUseCase.updatePetDetails(updatePetDTOMono)
                .flatMap(updatedPetResponse-> ServerResponse.ok().bodyValue(updatedPetResponse))
                .onErrorResume(exceptionMapper);
    }
}
