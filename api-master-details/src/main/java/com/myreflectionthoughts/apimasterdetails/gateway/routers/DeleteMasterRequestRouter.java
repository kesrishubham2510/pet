package com.myreflectionthoughts.apimasterdetails.gateway.routers;

import com.myreflectionthoughts.apimasterdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apimasterdetails.gateway.handler.DeleteMasterRequestHandler;
import com.myreflectionthoughts.library.dto.response.DeleteMasterDTO;
import com.myreflectionthoughts.library.dto.response.ExceptionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
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
public class DeleteMasterRequestRouter {

    @Autowired
    private DeleteMasterRequestHandler deleteMasterRequestHandler;

    private final String endpoint = ServiceConstants.API_QUALIFIER+ "/delete/master/{masterId}";

    @RouterOperation(
            path = endpoint,
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            beanClass = DeleteMasterRequestHandler.class,
            beanMethod = "deleteMasterDetails",
            operation = @Operation(
                    operationId = "deleteMasterDetails",
                    summary = "Delete Master's information",
                    description = "Marks the Master for next scheduled deletion",
                    parameters = {
                            @Parameter(
                                    name = "masterId",
                                    required = true,
                                    in = ParameterIn.PATH,
                                    description = "id of the master",
                                    schema = @Schema(implementation = String.class)
                            )
                    },
                    responses = {
                            @ApiResponse(
                                    responseCode = "200",
                                    description = "Successfully scheduled the Master for deletion",
                                    content = @Content(schema = @Schema(implementation = DeleteMasterDTO.class))
                            ),
                            @ApiResponse(
                                    responseCode = "400",
                                    description = "The masterId provided is wrong",
                                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
                            )
                    }
            )

    )
    @Bean
    public RouterFunction<ServerResponse> routeDeleteMasterRequest(){
        return RouterFunctions.route().DELETE(endpoint, deleteMasterRequestHandler::deleteMasterDetails).build();
    }
}