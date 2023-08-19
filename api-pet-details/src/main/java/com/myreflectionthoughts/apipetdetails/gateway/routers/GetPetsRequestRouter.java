package com.myreflectionthoughts.apipetdetails.gateway.routers;

import com.myreflectionthoughts.apipetdetails.core.constant.ServiceConstants;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

public class GetPetsRequestRouter {

    private String endPoint = ServiceConstants.API_QUALIFIER+"/get/all";

    public RouterFunction<ServerResponse> routeGetPetsRequest(){
        return RouterFunctions.route().GET(endPoint,null).build();
    }
}