package com.myreflectionthoughts.apigateway.gateway.router;

import com.myreflectionthoughts.apigateway.core.constant.ServiceConstant;
import com.myreflectionthoughts.apigateway.gateway.handler.RetrieveUserDetailsRequestHandler;
import com.myreflectionthoughts.library.dto.response.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
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
public class RetrieveUserDetailsRouter {

    private final String endpoint = ServiceConstant.API_QUALIFIER + "/get/user/{userId}";

    private final RetrieveUserDetailsRequestHandler retrieveUserDetailsRequestHandler;

    public RetrieveUserDetailsRouter(RetrieveUserDetailsRequestHandler retrieveUserDetailsRequestHandler) {
        this.retrieveUserDetailsRequestHandler = retrieveUserDetailsRequestHandler;
    }

    @RouterOperation(
            path = endpoint,
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            },
            method = RequestMethod.GET,
            beanClass = RetrieveUserDetailsRequestHandler.class,
            beanMethod = "retrieveUser",
            operation = @Operation(
                    operationId = "retrieveUser",
                    summary = "Retrieve User",
                    description = "Retrieves information about the user",
                    parameters = @Parameter(
                            name = "userId",
                            in = ParameterIn.PATH,
                            description = "id of the user",
                            required = true,
                            schema = @Schema(implementation = String.class)
                    ),
                    responses = {
                            @ApiResponse(
                                    responseCode = "200",
                                    description = ServiceConstant.API_RESPONSE_200_MESSAGE,
                                    content = @Content(schema = @Schema(
                                            implementation = UserDTO.class
                                    ))
                            ),
                    }
            )
    )
    @Bean
    public RouterFunction<ServerResponse> routeRetrieveUserDetailsRequest() {
        return route().GET(endpoint, retrieveUserDetailsRequestHandler::retrieveUser).build();
    }
}
