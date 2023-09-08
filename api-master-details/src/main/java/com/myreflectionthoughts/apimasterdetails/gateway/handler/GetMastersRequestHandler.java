package com.myreflectionthoughts.apimasterdetails.gateway.handler;

import com.myreflectionthoughts.apimasterdetails.core.usecase.ReadMastersUseCase;
import com.myreflectionthoughts.library.dto.response.MasterDTO;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
public class GetMastersRequestHandler{

    private final ReadMastersUseCase readMastersUseCase;

    public GetMastersRequestHandler(ReadMastersUseCase readMastersUseCase) {
        this.readMastersUseCase = readMastersUseCase;
    }

    public Mono<ServerResponse> getMasters(ServerRequest serverRequest){
        return ServerResponse.ok().contentType(MediaType.APPLICATION_NDJSON).body(readMastersUseCase.readMasters(), MasterDTO.class);
    }
}