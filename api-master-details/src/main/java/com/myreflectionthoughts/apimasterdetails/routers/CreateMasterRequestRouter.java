package com.myreflectionthoughts.apimasterdetails.routers;

import com.myreflectionthoughts.apimasterdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apimasterdetails.gateway.handler.CreateMasterRequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class CreateMasterRequestRouter {

    @Autowired
    private CreateMasterRequestHandler createMasterRequestHandler;

    private final String endpoint = ServiceConstants.API_QUALIFIER+"/add";
    @Bean
    public RouterFunction<ServerResponse> routeCreateMasterRequest(){
        return RouterFunctions.route().POST(endpoint,createMasterRequestHandler::createMaster).build();
    }
}