package com.myreflectionthoughts.apipetdetails.gateway.routers;

import com.myreflectionthoughts.apipetdetails.core.constant.ServiceConstants;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

public class CreatePetRequestRouter {

    private String endPoint = ServiceConstants.API_QUALIFIER+"/add";

    public RouterFunction<ServerResponse> routeCreatePetRequest(){
        return RouterFunctions.route().POST(endPoint,null).build();
    }
}