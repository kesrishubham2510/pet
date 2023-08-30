package com.myreflectionthoughts.apigateway.gateway.router;

import com.myreflectionthoughts.apigateway.core.constant.ServiceConstant;
import com.myreflectionthoughts.apigateway.core.usecase.RetrieveAllPetsOfMasterUseCase;
import com.myreflectionthoughts.apigateway.gateway.handler.RetrieveAllPetsOfMasterRequestHandller;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
public class RetrieveAllPetsOfUserRequestRouter {

    private final String endpoint = ServiceConstant.API_QUALIFIER + "/get/pets/{masterId}";
    private final RetrieveAllPetsOfMasterRequestHandller retrieveAllPetsOfMasterRequestHandller;

    public RetrieveAllPetsOfUserRequestRouter(RetrieveAllPetsOfMasterUseCase retrieveAllPetsOfMasterUseCase) {
        retrieveAllPetsOfMasterRequestHandller = new RetrieveAllPetsOfMasterRequestHandller(retrieveAllPetsOfMasterUseCase);
    }

    @RouterOperation(
            path = endpoint,
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            },
            method = RequestMethod.GET,
            beanClass = RetrieveAllPetsOfMasterRequestHandller.class,
            beanMethod = "handleRetrieveAllPetsOfMasterRequest",
            operation = @Operation(
                    operationId = "retrievePets",
                    summary = "Retrieve Pets",
                    description = "Retrieves all pets of the master",
                    parameters = @Parameter(
                            name = "masterId",
                            in = ParameterIn.PATH,
                            description = "id of the master",
                            required = true,
                            schema = @Schema(implementation = String.class)
                    ),
                    responses = {
                            @ApiResponse(
                                    responseCode = "200",
                                    description = ServiceConstant.API_RESPONSE_200_MESSAGE,
                                    content = @Content(array = @ArraySchema(schema = @Schema(
                                            implementation = PetDTO.class
                                    )))
                            ),
                    }
            )
    )
    @Bean
    public RouterFunction<ServerResponse> routeMasterPetsRetrievalRequest() {
        return route().GET(endpoint, retrieveAllPetsOfMasterRequestHandller::handleRetrieveAllPetsOfMasterRequest).build();
    }
}
