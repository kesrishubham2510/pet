package com.myreflectionthoughts.apimasterdetails.gateway.routers;

import com.myreflectionthoughts.apimasterdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apimasterdetails.gateway.handler.UpdateMasterRequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class UpdateMasterRequestRouter {

    @Autowired
    private UpdateMasterRequestHandler updateMasterRequestHandler;

    private final String endpoint = ServiceConstants.API_QUALIFIER + "/update/master/{masterId}";

    @Bean
    public RouterFunction<ServerResponse> routeUpdateMasterRequest(){
        return RouterFunctions.route().PUT(endpoint, updateMasterRequestHandler::updateMasterDetails).build();
    }
}