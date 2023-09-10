package com.myreflectionthoughts.apigateway.gateway.handler;

import com.myreflectionthoughts.apigateway.core.usecase.DemoDataStreamUseCase;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
public class StreamDataRequestHandler extends Handler{

    private final DemoDataStreamUseCase demoDataStreamUseCase;

    public StreamDataRequestHandler(DemoDataStreamUseCase demoDataStreamUseCase) {
        this.demoDataStreamUseCase = demoDataStreamUseCase;
    }

    public Mono<ServerResponse> handleDataStreamRequest(ServerRequest serverRequest){
        return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(
                demoDataStreamUseCase.demoStreaming(), PetDTO.class
        );
    }
}
