package com.myreflectionthoughts.apimasterdetails.gateway.routers;

import com.myreflectionthoughts.apimasterdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apimasterdetails.gateway.handler.CreateMasterRequestHandler;
import com.myreflectionthoughts.library.dto.request.AddMasterDTO;
import com.myreflectionthoughts.library.dto.response.ExceptionResponse;
import com.myreflectionthoughts.library.dto.response.MasterDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
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
public class CreateMasterRequestRouter {

    private final CreateMasterRequestHandler createMasterRequestHandler;

    private final String endpoint = ServiceConstants.API_QUALIFIER+"/add";

    public CreateMasterRequestRouter(CreateMasterRequestHandler createMasterRequestHandler) {
        this.createMasterRequestHandler = createMasterRequestHandler;
    }

    @RouterOperation(
            path = endpoint,
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            beanClass = CreateMasterRequestHandler.class,
            beanMethod = "createMaster",
            operation = @Operation(
                    operationId = "createMaster",
                    summary = "Adds a master",
                    description = "Creates and saves a master with the information provided in the request payload",
                    requestBody = @RequestBody(
                            required = true,
                            content = @Content(schema = @Schema(implementation = AddMasterDTO.class))
                    ),
                    responses = {
                            @ApiResponse(
                                    responseCode = "200",
                                    description = ServiceConstants.API_RESPONSE_200_MESSAGE,
                                    content = @Content(schema = @Schema(implementation = MasterDTO.class))
                            ),
                            @ApiResponse(
                                    responseCode = "400",
                                    description = ServiceConstants.API_RESPONSE_400_MESSAGE,
                                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
                            )
                    }
            )
    )
    @Bean
    public RouterFunction<ServerResponse> routeCreateMasterRequest(){
        return RouterFunctions.route().POST(endpoint,createMasterRequestHandler::createMaster).build();
    }
}