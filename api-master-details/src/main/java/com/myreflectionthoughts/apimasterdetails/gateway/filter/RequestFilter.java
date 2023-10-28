package com.myreflectionthoughts.apimasterdetails.gateway.filter;

import com.myreflectionthoughts.library.utils.UniqueIdGenerator;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class RequestFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        for(var entry: exchange.getRequest().getHeaders().entrySet()) {
            System.out.println(entry.getKey() + " Value:- " + entry.getValue());
        }

        setMDCForRequest(exchange.getRequest().getHeaders().get("traceId").get(0), UniqueIdGenerator.generateSpanID());
        return chain.filter(exchange);
    }

    private void setMDCForRequest(String traceId, String spanId){
        MDC.put("traceId",traceId);
        MDC.put("spanId",spanId);
    }
}
