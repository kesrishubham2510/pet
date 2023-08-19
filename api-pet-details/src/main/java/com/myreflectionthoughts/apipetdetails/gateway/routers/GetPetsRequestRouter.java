package com.myreflectionthoughts.apipetdetails.gateway.routers;

import com.myreflectionthoughts.apipetdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apipetdetails.gateway.handler.GetPetsRequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;


@Configuration
public class GetPetsRequestRouter {

    @Autowired
    private GetPetsRequestHandler getPetsRequestHandler;

    private String endPoint = ServiceConstants.API_QUALIFIER+"/get/all";

    @Bean
    public RouterFunction<ServerResponse> routeGetPetsRequest(){
        return RouterFunctions.route(RequestPredicates.GET(endPoint),getPetsRequestHandler::getPets);
    }
}