package com.myreflectionthoughts.apipetdetails.gateway.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.util.UUID;

@Component
public class RequestFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String traceId;

        if(null!=exchange.getRequest().getHeaders().get("traceId") &&
                 !exchange.getRequest().getHeaders().get("traceId").isEmpty()){
            traceId = exchange.getRequest().getHeaders().get("traceId").get(0) ;
        }else{
            System.out.println("Generating a new traceId:- ");
            traceId = UUID.randomUUID().toString();
        }
        return chain.filter(exchange)
                .contextWrite(Context.of("traceId", traceId))
                .contextWrite(Context.of("spanId",UUID.randomUUID().toString()));
    }
}
