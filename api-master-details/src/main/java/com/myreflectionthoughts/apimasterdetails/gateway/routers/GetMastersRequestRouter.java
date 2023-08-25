package com.myreflectionthoughts.apimasterdetails.gateway.routers;

import com.myreflectionthoughts.apimasterdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apimasterdetails.gateway.handler.GetMastersRequestHandler;
import com.myreflectionthoughts.library.dto.response.MasterDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class GetMastersRequestRouter {

    private final GetMastersRequestHandler getMastersRequestHandler;

    private final String endpoint = ServiceConstants.API_QUALIFIER + "/get/all";

    public GetMastersRequestRouter(GetMastersRequestHandler getMastersRequestHandler) {
        this.getMastersRequestHandler = getMastersRequestHandler;
    }

    @RouterOperation(
            path = endpoint,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE,
            beanClass = GetMastersRequestHandler.class,
            beanMethod = "getMasters",
            operation = @Operation(
                    operationId = "getMasters",
                    summary = "retrieve masters",
                    responses = {
                            @ApiResponse(
                                    responseCode = "200",
                                    content = @Content(array = @ArraySchema( schema = @Schema(implementation = MasterDTO.class))),
                                    description = ServiceConstants.API_RESPONSE_200_MESSAGE
                            )
                    }
            )
    )
    @Bean
    public RouterFunction<ServerResponse> routeGetMastersRequest(){
        return RouterFunctions.route().GET(endpoint, getMastersRequestHandler::getMasters).build();
    }
}