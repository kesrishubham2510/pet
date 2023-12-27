package com.myreflectionthoughts.apipetdetails.gateway.routers;

import com.myreflectionthoughts.apipetdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apipetdetails.gateway.handler.GetPetsOfMasterRequestHandler;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import io.micrometer.core.annotation.Timed;
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
public class GetPetsOfMasterRequestRouter {

    private final String endpoint = ServiceConstants.API_QUALIFIER + "/get/pets/{masterId}";

    private final GetPetsOfMasterRequestHandler getPetsOfMasterRequestHandler;

    public GetPetsOfMasterRequestRouter(GetPetsOfMasterRequestHandler getPetsOfMasterRequestHandler) {
        this.getPetsOfMasterRequestHandler = getPetsOfMasterRequestHandler;
    }

    // since this API will not be used by browser, hence Media Type is NDJSon, (i.e, server-server http communication)
    @RouterOperation(
            path = endpoint,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_NDJSON_VALUE,
            beanClass = GetPetsOfMasterRequestHandler.class,
            beanMethod = "retrievePetsOfMaster",
            operation = @Operation(
                    operationId = "retrievePetsOfMaster",
                    summary = "Get all pets of the master",
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
                                    description = ServiceConstants.API_RESPONSE_200_MESSAGE,
                                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = PetDTO.class)))
                            )
                    }
            )

    )
    @Bean
    @Timed
    public RouterFunction<ServerResponse> routeGetAllPetsOfMaster() {
        return route().GET(endpoint, getPetsOfMasterRequestHandler::retrievePetsOfMaster).build();
    }
}