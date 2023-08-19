package com.myreflectionthoughts.apipetdetails.gateway.routers;

import com.myreflectionthoughts.apipetdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apipetdetails.gateway.handler.UpdatePetRequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class UpdatePetRequestRouter {

    @Autowired
    private UpdatePetRequestHandler updatePetRequestHandler;

    private final String endPoint = ServiceConstants.API_QUALIFIER+"/update/pet/{petId}";

    @Bean
    public RouterFunction<ServerResponse> routeUpdatePetRequest(){
        return RouterFunctions.route().PUT(endPoint,updatePetRequestHandler::updatePet).build();
    }
}