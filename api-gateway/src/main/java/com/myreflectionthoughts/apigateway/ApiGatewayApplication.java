package com.myreflectionthoughts.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Hooks;

@SpringBootApplication
public class ApiGatewayApplication {

	public static void main(String[] args) {
		Hooks.enableAutomaticContextPropagation();
		Hooks.enableContextLossTracking();
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

}
