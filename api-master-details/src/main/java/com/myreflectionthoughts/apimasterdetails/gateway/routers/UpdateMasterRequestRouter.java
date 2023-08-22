package com.myreflectionthoughts.apimasterdetails.gateway.routers;

import com.myreflectionthoughts.apimasterdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apimasterdetails.gateway.handler.UpdateMasterRequestHandler;
import com.myreflectionthoughts.library.dto.request.UpdateMasterDTO;
import com.myreflectionthoughts.library.dto.response.ExceptionResponse;
import com.myreflectionthoughts.library.dto.response.MasterDTO;
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
public class UpdateMasterRequestRouter {

    @Autowired
    private UpdateMasterRequestHandler updateMasterRequestHandler;

    private final String endpoint = ServiceConstants.API_QUALIFIER + "/update/master/{masterId}";

    @RouterOperation(
            path = endpoint,
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            beanClass = UpdateMasterRequestHandler.class,
            beanMethod = "updateMasterDetails",
            operation = @Operation(
                    operationId = "updateMasterDetails",
                    summary = "Updates the master",
                    description = "Updates the master with information provided in the request payload",
                    parameters = @Parameter(
                            name = "masterId",
                            required = true,
                            description = "id of the master",
                            schema = @Schema(type="string"),
                            in = ParameterIn.PATH
                    ),
                    requestBody = @RequestBody(
                            content = @Content(schema = @Schema(implementation = UpdateMasterDTO.class))
                    ),
                    responses = {
                            @ApiResponse(
                                    responseCode = "200",
                                    content = @Content(schema = @Schema(implementation = MasterDTO.class)),
                                    description = "Successfully updated the information"
                            ),
                            @ApiResponse(
                                    responseCode = "400",
                                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class)),
                                    description = "masterId provided is wrong"
                            )
                    }
            )
    )

    @Bean
    public RouterFunction<ServerResponse> routeUpdateMasterRequest(){
        return RouterFunctions.route().PUT(endpoint, updateMasterRequestHandler::updateMasterDetails).build();
    }
}