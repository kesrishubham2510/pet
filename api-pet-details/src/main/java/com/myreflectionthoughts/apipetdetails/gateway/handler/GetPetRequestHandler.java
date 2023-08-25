package com.myreflectionthoughts.apipetdetails.gateway.handler;

import com.myreflectionthoughts.apipetdetails.core.usecase.ReadPetDetailsUseCase;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
public class GetPetRequestHandler extends Handler{
    private final ReadPetDetailsUseCase readPetDetailsUseCase;

    public GetPetRequestHandler(ReadPetDetailsUseCase readPetDetailsUseCase) {
        this.readPetDetailsUseCase = readPetDetailsUseCase;
    }

    public Mono<ServerResponse> getPet(ServerRequest serverRequest){
        String petId = serverRequest.pathVariable("petId");
        return readPetDetailsUseCase.getDetails(Mono.just(petId))
                .flatMap(petDetailResponse-> ServerResponse.ok().bodyValue(petDetailResponse))
                .onErrorResume(exceptionMapper);
    }
}
