package com.myreflectionthoughts.apimasterdetails.gateway.handler;

import com.myreflectionthoughts.apimasterdetails.core.usecase.ReadMastersUseCase;
import com.myreflectionthoughts.library.dto.response.MasterDTO;
import com.myreflectionthoughts.apimasterdetails.core.utils.LogUtility;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

@Service
public class GetMastersRequestHandler{

    private final ReadMastersUseCase readMastersUseCase;
    private final Logger logger;

    public GetMastersRequestHandler(ReadMastersUseCase readMastersUseCase) {
        this.readMastersUseCase = readMastersUseCase;
        logger = Logger.getLogger(GetMastersRequestHandler.class.getName());
    }

    public Mono<ServerResponse> getMasters(ServerRequest serverRequest){
        LogUtility.loggerUtility.logEntry(logger, "Initiating get all masters request processing...");
        return ServerResponse.ok().contentType(MediaType.APPLICATION_NDJSON).body(readMastersUseCase.readMasters(), MasterDTO.class);
    }
}