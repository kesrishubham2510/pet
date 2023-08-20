package com.myreflectionthoughts.apimasterdetails.routers;

import com.myreflectionthoughts.apimasterdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apimasterdetails.gateway.handler.GetMastersRequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class GetMastersRequestRouter {

    @Autowired
    private GetMastersRequestHandler getMastersRequestHandler;

    private final String endpoint = ServiceConstants.API_QUALIFIER + "/get/all";

    @Bean
    public RouterFunction<ServerResponse> routeGetMastersRequest(){
        return RouterFunctions.route().GET(endpoint, getMastersRequestHandler::getMasters).build();
    }

}