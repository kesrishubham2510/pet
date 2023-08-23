package com.myreflectionthoughts.apipetdetails.gateway.routers;


import com.myreflectionthoughts.apipetdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apipetdetails.gateway.handler.DeletePetRequestHandler;
import com.myreflectionthoughts.library.dto.response.DeletePetDTO;
import com.myreflectionthoughts.library.dto.response.ExceptionResponse;
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
public class DeletePetRequestRouter {
    @Autowired
    private DeletePetRequestHandler deletePetRequestHandler;

    private final String endPoint = ServiceConstants.API_QUALIFIER+"/delete/pet/{petId}";


    @RouterOperation(
            path = endPoint,
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            beanClass = DeletePetRequestHandler.class,
            beanMethod = "deletePet",
            operation = @Operation(
                    operationId = "deletePet",
                    summary = "Delete the pet",
                    description = "Deletes the pet with specified id",
                    parameters = {
                            @Parameter(
                                    name = "petId",
                                    in = ParameterIn.PATH,
                                    description = "id of the pet",
                                    required = true,
                                    schema = @Schema(implementation = String.class))
                    },
                    responses = {
                            @ApiResponse(
                                    responseCode = "200",
                                    description = ServiceConstants.API_RESPONSE_200_MESSAGE,
                                    content = @Content(schema = @Schema(implementation = DeletePetDTO.class))
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
    public RouterFunction<ServerResponse> routeDeletePetRequest(){
        return RouterFunctions.route().DELETE(endPoint,deletePetRequestHandler::deletePet).build();
    }
}