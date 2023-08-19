package com.myreflectionthoughts.apipetdetails.gateway.routers;


import com.myreflectionthoughts.apipetdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apipetdetails.gateway.handler.DeletePetRequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class DeletePetRequestRouter {
    @Autowired
    private DeletePetRequestHandler deletePetRequestHandler;

    private String endPoint = ServiceConstants.API_QUALIFIER+"/delete/pet/{petId}";


    @Bean
    public RouterFunction<ServerResponse> routeDeletePetRequest(){
        return RouterFunctions.route().DELETE(endPoint,deletePetRequestHandler::deletePet).build();
    }
}