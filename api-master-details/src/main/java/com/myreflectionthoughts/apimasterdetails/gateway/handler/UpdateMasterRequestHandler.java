package com.myreflectionthoughts.apimasterdetails.gateway.handler;

import com.myreflectionthoughts.apimasterdetails.core.usecase.UpdateMasterDetailsUseCase;
import com.myreflectionthoughts.library.dto.request.UpdateMasterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
public class UpdateMasterRequestHandler extends Handler {

    private final UpdateMasterDetailsUseCase updateMasterDetailsUseCase;

    public UpdateMasterRequestHandler(UpdateMasterDetailsUseCase updateMasterDetailsUseCase) {
        this.updateMasterDetailsUseCase = updateMasterDetailsUseCase;
    }

    public Mono<ServerResponse> updateMasterDetails(ServerRequest serverRequest){
        Mono<UpdateMasterDTO> updateMasterDTOMono = serverRequest.bodyToMono(UpdateMasterDTO.class);
        return updateMasterDetailsUseCase
                .updateMasterDetails(updateMasterDTOMono)
                .flatMap(response-> ServerResponse.ok().bodyValue(response))
                .onErrorResume(exceptionMapper);
    }
}