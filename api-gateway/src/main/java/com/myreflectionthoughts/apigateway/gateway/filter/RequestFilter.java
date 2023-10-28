package com.myreflectionthoughts.apigateway.gateway.filter;

import com.myreflectionthoughts.library.utils.UniqueIdGenerator;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

@Component
public class RequestFilter implements WebFilter {

    private final Logger logger;
    private String requestId;
    private String correlationId;


    public RequestFilter(){
        logger = Logger.getLogger(RequestFilter.class.getName());
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
         // add request headers [traceId, spanId]
         setMDCForRequest(UniqueIdGenerator.generateTraceId(), UniqueIdGenerator.generateSpanID());
         exchange.getResponse().getHeaders().set("X-Request-Id", MDC.get("traceId") == null ? "abcd" : MDC.get("traceId"));
         return chain.filter(exchange);
    }

    private void setMDCForRequest(String traceId, String spanId){
        MDC.put("traceId",traceId);
        MDC.put("spanId",spanId);
    }
}
