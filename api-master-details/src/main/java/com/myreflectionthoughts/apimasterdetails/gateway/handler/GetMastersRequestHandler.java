package com.myreflectionthoughts.apimasterdetails.gateway.handler;

import com.myreflectionthoughts.apimasterdetails.core.usecase.ReadMastersUseCase;
import com.myreflectionthoughts.library.dto.response.MasterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
public class GetMastersRequestHandler{

    @Autowired
    private ReadMastersUseCase readMastersUseCase;

    public Mono<ServerResponse> getMasters(ServerRequest serverRequest){
        return ServerResponse.ok().body(readMastersUseCase.readMasters(), MasterDTO.class);
    }
}