package com.myreflectionthoughts.apimasterdetails.gateway.handler;

import com.myreflectionthoughts.apimasterdetails.core.usecase.ReadMasterDetailsUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
public class GetMasterRequestHandler extends Handler {

    private final ReadMasterDetailsUseCase readMasterDetailsUseCase;

    public GetMasterRequestHandler(ReadMasterDetailsUseCase readMasterDetailsUseCase) {
        this.readMasterDetailsUseCase = readMasterDetailsUseCase;
    }

    public Mono<ServerResponse> getMasterDetails(ServerRequest serverRequest){
        String masterId = serverRequest.pathVariable("masterId");
        return readMasterDetailsUseCase
                .readMasterDetails(Mono.just(masterId))
                .flatMap(response-> ServerResponse.ok().bodyValue(response))
                .onErrorResume(exceptionMapper);
    }
}