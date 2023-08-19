package com.myreflectionthoughts.apipetdetails.gateway.routers;

import com.myreflectionthoughts.apipetdetails.core.constant.ServiceConstants;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

public class GetPetRequestRouter {

    private String endPoint = ServiceConstants.API_QUALIFIER+"/get/pet/{petId}";

    public RouterFunction<ServerResponse> routeGetPetRequest(){
        return RouterFunctions.route().GET(endPoint,null).build();
    }
}