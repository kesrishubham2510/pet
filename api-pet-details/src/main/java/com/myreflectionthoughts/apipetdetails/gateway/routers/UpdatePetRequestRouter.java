package com.myreflectionthoughts.apipetdetails.gateway.routers;

import com.myreflectionthoughts.apipetdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apipetdetails.gateway.handler.UpdatePetRequestHandler;
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
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class UpdatePetRequestRouter {

    private final UpdatePetRequestHandler updatePetRequestHandler;

    private final String endPoint = ServiceConstants.API_QUALIFIER+"/update/pet/{petId}";

    public UpdatePetRequestRouter(UpdatePetRequestHandler updatePetRequestHandler) {
        this.updatePetRequestHandler = updatePetRequestHandler;
    }

    @RouterOperation(
           path = endPoint,
           method = RequestMethod.PUT,
           consumes = MediaType.APPLICATION_JSON_VALUE,
           produces = MediaType.APPLICATION_JSON_VALUE,
           beanClass = UpdatePetRequestHandler.class,
           beanMethod = "updatePet",
           operation = @Operation(
                   operationId = "updatePet",
                   summary = "Updates the pet",
                   description = "Updates the pet with the provided request payload",
                   parameters = @Parameter(
                           name = "petId",
                           in = ParameterIn.PATH,
                           description = "id of the pet",
                           required = true,
                           schema = @Schema( implementation = String.class)
                   ),
                   requestBody = @RequestBody(
                           content = @Content(schema = @Schema(implementation = UpdatePetDTO.class)),
                           required = true
                   ),
                   responses = {
                           @ApiResponse(
                                  responseCode = "200",
                                  description = ServiceConstants.API_RESPONSE_200_MESSAGE,
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
    public RouterFunction<ServerResponse> routeUpdatePetRequest(){
        return RouterFunctions.route().PUT(endPoint,updatePetRequestHandler::updatePet).build();
    }
}