package com.myreflectionthoughts.apigateway.gateway.router;

import com.myreflectionthoughts.apigateway.core.constant.ServiceConstant;
import com.myreflectionthoughts.apigateway.gateway.handler.UpdateUserRequestHandler;
import com.myreflectionthoughts.library.dto.request.UpdateUserDTO;
import com.myreflectionthoughts.library.dto.response.ExceptionResponse;
import com.myreflectionthoughts.library.dto.response.UserDTO;
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
public class UpdateUserRequestRouter {

    private final String endpoint = ServiceConstant.API_QUALIFIER + "/update/{userId}";

    private final UpdateUserRequestHandler updateUserRequestHandler;

    public UpdateUserRequestRouter(UpdateUserRequestHandler updateUserRequestHandler) {
        this.updateUserRequestHandler = updateUserRequestHandler;
    }

    @RouterOperation(
            path = endpoint,
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            beanClass = UpdateUserRequestHandler.class,
            beanMethod = "updateUser",
            operation = @Operation(
                    operationId = "updateUserDetails",
                    summary = "Updates the user",
                    description = "Updates the user with information provided in the request payload",
                    parameters = @Parameter(
                            name = "userId",
                            required = true,
                            description = "id of the user",
                            schema = @Schema(type = "string"),
                            in = ParameterIn.PATH
                    ),
                    requestBody = @RequestBody(
                            required = true,
                            content = @Content(schema = @Schema(implementation = UpdateUserDTO.class))
                    ),
                    responses = {
                            @ApiResponse(
                                    responseCode = "200",
                                    content = @Content(schema = @Schema(implementation = UserDTO.class)),
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
    RouterFunction<ServerResponse> routeUpdateUserRequest() {
        return route().PUT(endpoint, updateUserRequestHandler::updateUser).build();
    }
}
