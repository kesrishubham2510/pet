package com.myreflectionthoughts.apipetdetails.gateway.routers;

import com.myreflectionthoughts.apipetdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apipetdetails.gateway.handler.GetPetsRequestHandler;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import io.micrometer.core.annotation.Timed;
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
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;


@Configuration
public class GetPetsRequestRouter {

    private final GetPetsRequestHandler getPetsRequestHandler;

    private final String endPoint = ServiceConstants.API_QUALIFIER+"/get/all";

    public GetPetsRequestRouter(GetPetsRequestHandler getPetsRequestHandler) {
        this.getPetsRequestHandler = getPetsRequestHandler;
    }

    // since this API will not be used by browser, hence Media Type is NDJSon, (i.e, server-server http communication)

    @RouterOperation(
        path = endPoint,
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_NDJSON_VALUE,
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
    @Timed
    public RouterFunction<ServerResponse> routeGetPetsRequest(){
        return RouterFunctions.route(RequestPredicates.GET(endPoint),getPetsRequestHandler::getPets);
    }
}