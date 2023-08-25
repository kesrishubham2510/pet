package com.myreflectionthoughts.apipetdetails.gateway.routers;

import com.myreflectionthoughts.apipetdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apipetdetails.gateway.handler.GetPetRequestHandler;
import com.myreflectionthoughts.library.dto.response.ExceptionResponse;
import com.myreflectionthoughts.library.dto.response.PetDTO;
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
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class GetPetRequestRouter {

    private final GetPetRequestHandler getPetRequestHandler;

    private final String endPoint = ServiceConstants.API_QUALIFIER+"/get/pet/{petId}";

    public GetPetRequestRouter(GetPetRequestHandler getPetRequestHandler) {
        this.getPetRequestHandler = getPetRequestHandler;
    }

    @RouterOperation(
            path = endPoint,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE,
            beanClass = GetPetRequestHandler.class,
            beanMethod = "getPet",
            operation = @Operation(
                    operationId = "getPet",
                    summary = "Get pet's information",
                    description = "Retrieve the details of the pet using petId",
                    parameters = {
                            @Parameter(
                                  name = "petId",
                                  in = ParameterIn.PATH,
                                  description = "id of the pet",
                                  required = true,
                                  schema = @Schema( implementation = String.class)
                            )
                    },
                    responses = {
                            @ApiResponse(
                                    responseCode = "200",
                                    description =  ServiceConstants.API_RESPONSE_200_MESSAGE,
                                    content = @Content(schema = @Schema(implementation = PetDTO.class))
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
    public RouterFunction<ServerResponse> routeGetPetRequest(){
        return RouterFunctions.route().GET(endPoint,getPetRequestHandler::getPet).build();
    }
}