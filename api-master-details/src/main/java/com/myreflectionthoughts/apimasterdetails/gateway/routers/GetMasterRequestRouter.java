package com.myreflectionthoughts.apimasterdetails.gateway.routers;

import com.myreflectionthoughts.apimasterdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apimasterdetails.gateway.handler.GetMasterRequestHandler;
import com.myreflectionthoughts.library.dto.response.ExceptionResponse;
import com.myreflectionthoughts.library.dto.response.MasterDTO;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
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
public class GetMasterRequestRouter {

    @Autowired
    private GetMasterRequestHandler getMasterRequestHandler;

    private final String endpoint = ServiceConstants.API_QUALIFIER + "/get/master/{masterId}";

    @RouterOperation(
            path = endpoint,
            method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            beanClass = GetMasterRequestHandler.class,
            beanMethod = "getMasterDetails",
            operation = @Operation(
                    operationId = "getMasterDetails",
                    summary = "Get Master details",
                    description = "Retrieves master details of the provided masterId",
                    parameters = @Parameter(
                            name = "masterId",
                            in = ParameterIn.PATH,
                            required = true,
                            schema = @Schema(type = "string"),
                            description = "Id of the master you wanna retrieve"
                    ),
                    responses = {
                            @ApiResponse(
                                    responseCode = "200",
                                    description = "successfully retrieved the information",
                                    content = @Content(schema = @Schema(implementation = MasterDTO.class))
                            ),
                            @ApiResponse(
                                    responseCode = "400",
                                    description = "wrong masterId provided",
                                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
                            )
                     }
            )
    )
    @Bean
    public RouterFunction<ServerResponse> routeGetMasterRequest(){
        return RouterFunctions.route().GET(endpoint, getMasterRequestHandler::getMasterDetails).build();
    }
}