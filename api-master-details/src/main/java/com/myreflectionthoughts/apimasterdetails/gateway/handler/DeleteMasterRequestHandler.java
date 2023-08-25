package com.myreflectionthoughts.apimasterdetails.gateway.handler;

import com.myreflectionthoughts.apimasterdetails.core.usecase.DeleteMasterDetailsUseCase;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
public class DeleteMasterRequestHandler extends Handler {

    private final DeleteMasterDetailsUseCase deleteMasterDetailsUseCase;

    public DeleteMasterRequestHandler(DeleteMasterDetailsUseCase deleteMasterDetailsUseCase) {
        this.deleteMasterDetailsUseCase = deleteMasterDetailsUseCase;
    }

    public Mono<ServerResponse> deleteMasterDetails(ServerRequest serverRequest){
        String masterId = serverRequest.pathVariable("masterId");
        return deleteMasterDetailsUseCase
                .deleteMasterDetails(Mono.just(masterId))
                .flatMap(response-> ServerResponse.ok().bodyValue(response))
                .onErrorResume(exceptionMapper);
    }
}