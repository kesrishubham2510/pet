package com.myreflectionthoughts.apigateway.gateway.handler;

import com.myreflectionthoughts.apigateway.core.usecase.DemoDataStreamUseCase;
import com.myreflectionthoughts.apigateway.core.utils.LogUtility;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

@Service
public class StreamDataRequestHandler extends Handler{

    private final DemoDataStreamUseCase demoDataStreamUseCase;
    private final Logger logger;

    public StreamDataRequestHandler(DemoDataStreamUseCase demoDataStreamUseCase) {
        this.demoDataStreamUseCase = demoDataStreamUseCase;
        logger = Logger.getLogger(StreamDataRequestHandler.class.getName());
    }

    public Mono<ServerResponse> handleDataStreamRequest(ServerRequest serverRequest){
        LogUtility.loggerUtility.logEntry(logger, "Initiating stream-demonstration request processing...");
        return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(
                demoDataStreamUseCase.demoStreaming(), PetDTO.class
        );
    }
}
