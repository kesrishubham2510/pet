package com.myreflectionthoughts.apipetdetails.gateway.handler;

import com.myreflectionthoughts.apipetdetails.core.usecase.ReadPetsUseCase;
import com.myreflectionthoughts.apipetdetails.core.utils.LogUtility;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

@Service
public class GetPetsRequestHandler extends Handler{

    private final ReadPetsUseCase readPetsUseCase;
    private final Logger logger;


    public GetPetsRequestHandler(ReadPetsUseCase readPetsUseCase) {
        this.readPetsUseCase = readPetsUseCase;
        logger =Logger.getLogger(GetPetRequestHandler.class.getName());
    }

    public Mono<ServerResponse> getPets(ServerRequest serverRequest){
        LogUtility.loggerUtility.logEntry(logger, "Initiating get all pets request processing...");
        return  ServerResponse.ok().contentType(MediaType.APPLICATION_NDJSON).body(readPetsUseCase.getPets(), PetDTO.class);
    }
}
