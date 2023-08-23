package com.myreflectionthoughts.apipetdetails.gateway.routers;

import com.myreflectionthoughts.apipetdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apipetdetails.gateway.handler.GetPetsRequestHandler;
import com.myreflectionthoughts.library.dto.response.PetDTO;
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
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;


@Configuration
public class GetPetsRequestRouter {

    @Autowired
    private GetPetsRequestHandler getPetsRequestHandler;

    private final String endPoint = ServiceConstants.API_QUALIFIER+"/get/all";

    @RouterOperation(
        path = endPoint,
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE,
        beanClass = GetPetsRequestHandler.class,
        beanMethod = "getPets",
        operation = @Operation(
                operationId = "getPets",
                summary = "Retrieve pets",
                responses = {
                        @ApiResponse(
                                responseCode = "200",
                                description = ServiceConstants.API_RESPONSE_200_MESSAGE,
                                content = @Content(array = @ArraySchema(schema = @Schema(implementation = PetDTO.class)))
                        )
                }
        )
    )
    @Bean
    public RouterFunction<ServerResponse> routeGetPetsRequest(){
        return RouterFunctions.route(RequestPredicates.GET(endPoint),getPetsRequestHandler::getPets);
    }
}