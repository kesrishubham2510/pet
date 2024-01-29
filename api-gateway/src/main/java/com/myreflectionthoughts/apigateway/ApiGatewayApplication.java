package com.myreflectionthoughts.apigateway;

import io.micrometer.context.ContextRegistry;
import org.slf4j.MDC;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Hooks;

@SpringBootApplication
public class ApiGatewayApplication {

	public static void main(String[] args) {
		Hooks.enableAutomaticContextPropagation();
		ContextRegistry.getInstance()
				.registerThreadLocalAccessor("traceId", ()->MDC.get("traceId"), traceId -> MDC.put("traceId",traceId), ()-> MDC.remove("traceId"))
				.registerThreadLocalAccessor("spanId", ()->MDC.get("spanId"), spanId -> MDC.put("spanId",spanId), ()-> MDC.remove("spanId") );
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

}
