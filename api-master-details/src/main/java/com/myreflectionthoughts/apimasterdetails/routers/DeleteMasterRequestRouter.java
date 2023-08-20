package com.myreflectionthoughts.apimasterdetails.routers;

import com.myreflectionthoughts.apimasterdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apimasterdetails.gateway.handler.DeleteMasterRequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class DeleteMasterRequestRouter {

    @Autowired
    private DeleteMasterRequestHandler deleteMasterRequestHandler;

    private final String endpoint = ServiceConstants.API_QUALIFIER+ "/delete/master/{masterId}";

    @Bean
    public RouterFunction<ServerResponse> routeDeleteMasterRequest(){
        return RouterFunctions.route().DELETE(endpoint, deleteMasterRequestHandler::deleteMasterDetails).build();
    }
}