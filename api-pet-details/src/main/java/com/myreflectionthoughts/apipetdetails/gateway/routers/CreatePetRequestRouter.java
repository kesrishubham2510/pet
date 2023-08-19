package com.myreflectionthoughts.apipetdetails.gateway.routers;

import com.myreflectionthoughts.apipetdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apipetdetails.gateway.handler.CreatePetRequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;


@Configuration
public class CreatePetRequestRouter {

    @Autowired
    private CreatePetRequestHandler createPetRequestHandler;
    private final String endPoint = ServiceConstants.API_QUALIFIER+"/add";

    @Bean
    public RouterFunction<ServerResponse> routeCreatePetRequest(){
        return route().POST(endPoint,createPetRequestHandler::addPet).build();
    }
}