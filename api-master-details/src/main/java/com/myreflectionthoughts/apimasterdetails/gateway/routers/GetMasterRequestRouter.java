package com.myreflectionthoughts.apimasterdetails.gateway.routers;

import com.myreflectionthoughts.apimasterdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apimasterdetails.gateway.handler.GetMasterRequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class GetMasterRequestRouter {

    @Autowired
    private GetMasterRequestHandler getMasterRequestHandler;

    private final String endpoint = ServiceConstants.API_QUALIFIER + "/get/master/{masterId}";

    @Bean
    public RouterFunction<ServerResponse> routeGetMasterRequest(){
        return RouterFunctions.route().GET(endpoint, getMasterRequestHandler::getMasterDetails).build();
    }
}