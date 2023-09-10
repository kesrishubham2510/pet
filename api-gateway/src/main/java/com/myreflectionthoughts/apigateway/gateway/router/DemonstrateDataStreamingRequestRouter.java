package com.myreflectionthoughts.apigateway.gateway.router;

import com.myreflectionthoughts.apigateway.core.constant.ServiceConstant;
import com.myreflectionthoughts.apigateway.gateway.handler.StreamDataRequestHandler;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class DemonstrateDataStreamingRequestRouter {

    private final String endpoint = ServiceConstant.API_QUALIFIER+"/demo-streaming";
    private final StreamDataRequestHandler streamDataRequestHandler;

    public DemonstrateDataStreamingRequestRouter(StreamDataRequestHandler streamDataRequestHandler) {
        this.streamDataRequestHandler = streamDataRequestHandler;
    }

    @RouterOperation(
            path = endpoint,
            produces = {
                    MediaType.TEXT_EVENT_STREAM_VALUE
            },
            method = RequestMethod.GET,
            beanClass = StreamDataRequestHandler.class,
            beanMethod = "handleDataStreamRequest",
            operation = @Operation(
                    operationId = "retrievePets",
                    summary = "Retrieve Pets",
                    description = "Retrieves all pets using Data streaming",
                    responses = {
                            @ApiResponse(
                                    responseCode = "200",
                                    description = ServiceConstant.API_RESPONSE_200_MESSAGE,
                                    content = @Content(array = @ArraySchema(schema = @Schema(
                                            implementation = PetDTO.class
                                    )))
                            ),
                    }
            )
    )
    @Bean
    public RouterFunction<ServerResponse> routeStreamingRequest(){
        return route().GET(endpoint, streamDataRequestHandler::handleDataStreamRequest).build();
    }
}
