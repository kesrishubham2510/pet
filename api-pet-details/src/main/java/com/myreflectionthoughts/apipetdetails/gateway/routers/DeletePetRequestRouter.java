package com.myreflectionthoughts.apipetdetails.gateway.routers;


import com.myreflectionthoughts.apipetdetails.core.constant.ServiceConstants;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

public class DeletePetRequestRouter {

    private String endPoint = ServiceConstants.API_QUALIFIER+"/delete/pet/{petId}";

    public RouterFunction<ServerResponse> routeDeletePetRequest(){
        return RouterFunctions.route().DELETE(endPoint,null).build();
    }
}