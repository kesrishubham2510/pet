package com.myreflectionthoughts.apipetdetails.gateway.routers;

import com.myreflectionthoughts.apipetdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apipetdetails.gateway.handler.GetPetRequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class GetPetRequestRouter {

    @Autowired
    private GetPetRequestHandler getPetRequestHandler;

    private String endPoint = ServiceConstants.API_QUALIFIER+"/get/pet/{petId}";

    @Bean
    public RouterFunction<ServerResponse> routeGetPetRequest(){
        return RouterFunctions.route().GET(endPoint,getPetRequestHandler::getPet).build();
    }
}