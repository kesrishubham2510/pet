package com.myreflectionthoughts.apipetdetails.gateway.routers;

import com.myreflectionthoughts.apipetdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apipetdetails.gateway.handler.CreatePetRequestHandler;
import com.myreflectionthoughts.library.dto.request.AddPetDTO;
import com.myreflectionthoughts.library.dto.response.ExceptionResponse;
import com.myreflectionthoughts.library.dto.response.PetDTO;
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
public class CreatePetRequestRouter {

    private final CreatePetRequestHandler createPetRequestHandler;
    private final String endPoint = ServiceConstants.API_QUALIFIER+"/add";

    public CreatePetRequestRouter(CreatePetRequestHandler createPetRequestHandler) {
        this.createPetRequestHandler = createPetRequestHandler;
    }

    @RouterOperation(
            path = endPoint,
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            },
            method = RequestMethod.POST,
            beanClass = CreatePetRequestHandler.class,
            beanMethod = "addPet",
            operation = @Operation(
                    operationId = "addPet",
                    summary = "Add a pet",
                    description = "Adds a Pet with information provided in request payload, and return details along with unique petId",
                    requestBody = @RequestBody(required = true, content = @Content(schema = @Schema(implementation = AddPetDTO.class))),
                    responses = {
                            @ApiResponse(
                                    responseCode = "200",
                                    description = ServiceConstants.API_RESPONSE_200_MESSAGE,
                                    content = @Content(schema = @Schema(
                                            implementation = PetDTO.class
                                    ))
                            ),
                            @ApiResponse(
                                    responseCode = "400",
                                    description = ServiceConstants.API_RESPONSE_400_MESSAGE,
                                    content = @Content(schema = @Schema(
                                            implementation = ExceptionResponse.class
                                    ))
                            )
                    }
            )
    )
    @Bean
    public RouterFunction<ServerResponse> routeCreatePetRequest(){
        return route().POST(endPoint,createPetRequestHandler::addPet).build();
    }
}