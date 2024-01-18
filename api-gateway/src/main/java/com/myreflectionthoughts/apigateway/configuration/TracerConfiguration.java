//package com.myreflectionthoughts.apigateway.configuration;
//
//import io.micrometer.tracing.Span;
//import io.micrometer.tracing.Tracer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.server.WebFilter;
//
//
//@Configuration
//public class TracerConfiguration {
//    @Bean
//    WebFilter traceIdInResponseFilter(Tracer tracer) {
//        //            @Observed
//        return (exchange, chain) -> {
//            Span currentSpan = tracer.currentSpan();
//            if (currentSpan != null) {
//                // putting trace id value in [traceId] response header
//                exchange.getResponse().getHeaders().add("traceId", currentSpan.context().traceId());
//            }
//            return chain.filter(exchange);
//        };
//    }
//}
