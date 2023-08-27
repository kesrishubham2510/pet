package com.myreflectionthoughts.apigateway.gateway.router;

import com.myreflectionthoughts.apigateway.core.constant.ServiceConstant;
import com.myreflectionthoughts.apigateway.gateway.handler.RegisterUserRequestHandler;
import com.myreflectionthoughts.library.dto.request.AddPetDTO;
import com.myreflectionthoughts.library.dto.request.AddUserDTO;
import com.myreflectionthoughts.library.dto.response.ExceptionResponse;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import com.myreflectionthoughts.library.dto.response.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
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
public class RegisterUserRequestRouter {

    private final RegisterUserRequestHandler registerUserRequestHandler;
    private final String endpoint = ServiceConstant.API_QUALIFIER+"/register";


    public RegisterUserRequestRouter(RegisterUserRequestHandler registerUserRequestHandler) {
        this.registerUserRequestHandler = registerUserRequestHandler;
    }

    @Bean
    @RouterOperation(
            path = endpoint,
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            },
            method = RequestMethod.POST,
            beanClass = RegisterUserRequestHandler.class,
            beanMethod = "addUser",
            operation = @Operation(
                    operationId = "addUser",
                    summary = "Add a User",
                    description = "Adds a User with information provided in request payload, and return details along with saved information",
                    requestBody = @RequestBody(required = true, content = @Content(schema = @Schema(implementation = AddUserDTO.class))),
                    responses = {
                            @ApiResponse(
                                    responseCode = "200",
                                    description = ServiceConstant.API_RESPONSE_200_MESSAGE,
                                    content = @Content(schema = @Schema(
                                            implementation = UserDTO.class
                                    ))
                            ),
                            @ApiResponse(
                                    responseCode = "400",
                                    description = ServiceConstant.API_RESPONSE_400_MESSAGE,
                                    content = @Content(schema = @Schema(
                                            implementation = ExceptionResponse.class
                                    ))
                            )
                    }
            )
    )
    public RouterFunction<ServerResponse> routeUserRegistrationRequest(){
        return route().POST(endpoint, registerUserRequestHandler::addUser).build();
    }
}
