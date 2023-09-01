package com.myreflectionthoughts.apigateway.gateway.router;

import com.myreflectionthoughts.apigateway.core.constant.ServiceConstant;
import com.myreflectionthoughts.apigateway.gateway.handler.UpdatePetDetailsRequestHandler;
import com.myreflectionthoughts.library.dto.request.UpdatePetDTO;
import com.myreflectionthoughts.library.dto.response.ExceptionResponse;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
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
public class UpdatePetDetailsRequestRouter {

    private final String endpoint = ServiceConstant.API_QUALIFIER + "/update/pet/{userId}";

    private final UpdatePetDetailsRequestHandler updatePetDetailsRequestHandler;

    public UpdatePetDetailsRequestRouter(UpdatePetDetailsRequestHandler updatePetDetailsRequestHandler) {
        this.updatePetDetailsRequestHandler = updatePetDetailsRequestHandler;
    }

    @RouterOperation(
            path = endpoint,
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            beanClass = UpdatePetDetailsRequestHandler.class,
            beanMethod = "updatePetDetails",
            operation = @Operation(
                    operationId = "updatePetDetails",
                    summary = "Updates the pet",
                    description = "Updates the pet with information provided in the request payload",
                    parameters = @Parameter(
                            name = "userId",
                            required = true,
                            description = "id of the user",
                            schema = @Schema(type = "string"),
                            in = ParameterIn.PATH
                    ),
                    requestBody = @RequestBody(
                            required = true,
                            content = @Content(schema = @Schema(implementation = UpdatePetDTO.class))
                    ),
                    responses = {
                            @ApiResponse(
                                    responseCode = "200",
                                    content = @Content(schema = @Schema(implementation = PetDTO.class)),
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
    public RouterFunction<ServerResponse> routeUpdateDetailsRequest(){
        return route().PUT(endpoint, updatePetDetailsRequestHandler::updatePetDetails).build();
    }
}
