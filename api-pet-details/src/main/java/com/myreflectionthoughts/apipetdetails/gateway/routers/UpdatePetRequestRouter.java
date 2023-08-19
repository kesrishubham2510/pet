package com.myreflectionthoughts.apipetdetails.gateway.routers;

import com.myreflectionthoughts.apipetdetails.core.constant.ServiceConstants;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

public class UpdatePetRequestRouter {

    private String endPoint = ServiceConstants.API_QUALIFIER+"/update/pet/{petId}";

    public RouterFunction<ServerResponse> routeUpdatePetRequest(){
        return RouterFunctions.route().PUT(endPoint,null).build();
    }
}