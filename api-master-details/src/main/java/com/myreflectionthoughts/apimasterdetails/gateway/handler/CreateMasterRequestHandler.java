package com.myreflectionthoughts.apimasterdetails.gateway.handler;

import com.myreflectionthoughts.apimasterdetails.core.usecase.CreateMasterUseCase;
import com.myreflectionthoughts.library.dto.request.AddMasterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
public class CreateMasterRequestHandler extends Handler{

    private final CreateMasterUseCase createMasterUseCase;

    public CreateMasterRequestHandler(CreateMasterUseCase createMasterUseCase) {
        this.createMasterUseCase = createMasterUseCase;
    }

    public Mono<ServerResponse> createMaster(ServerRequest serverRequest){
        Mono<AddMasterDTO> addMasterDTOMono = serverRequest.bodyToMono(AddMasterDTO.class);
        return createMasterUseCase
                .createMaster(addMasterDTOMono)
                .flatMap(response-> ServerResponse.status(HttpStatus.CREATED).bodyValue(response))
                .onErrorResume(exceptionMapper);
    }
}