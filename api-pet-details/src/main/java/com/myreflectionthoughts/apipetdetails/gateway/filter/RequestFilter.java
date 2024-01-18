package com.myreflectionthoughts.apipetdetails.gateway.filter;

import com.myreflectionthoughts.library.utils.UniqueIdGenerator;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.util.List;

@Component
public class RequestFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        for(var entry: exchange.getRequest().getHeaders().entrySet())
            System.out.println(entry.getKey()+" Value:- "+entry.getValue());

        String traceId;

        if(null!=exchange.getRequest().getHeaders().get("traceId") && !exchange.getRequest().getHeaders().get("traceId").isEmpty()){
           traceId = exchange.getRequest().getHeaders().get("traceId").get(0);
        }else{
           traceId = UniqueIdGenerator.generateSpanID();
        }

        return chain.filter(exchange)
                .contextWrite(Context.of("traceId", traceId))
                .contextWrite(Context.of("spanId",UniqueIdGenerator.generateSpanID()));
    }
}
