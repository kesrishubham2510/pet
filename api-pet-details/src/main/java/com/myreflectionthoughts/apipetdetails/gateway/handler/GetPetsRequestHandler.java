package com.myreflectionthoughts.apipetdetails.gateway.handler;

import com.myreflectionthoughts.apipetdetails.core.usecase.ReadPetsUseCase;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
public class GetPetsRequestHandler extends Handler{

    private final ReadPetsUseCase readPetsUseCase;

    public GetPetsRequestHandler(ReadPetsUseCase readPetsUseCase) {
        this.readPetsUseCase = readPetsUseCase;
    }

    public Mono<ServerResponse> getPets(ServerRequest serverRequest){
        return  ServerResponse.ok().contentType(MediaType.APPLICATION_NDJSON).body(readPetsUseCase.getPets(), PetDTO.class);
    }
}
