package com.myreflectionthoughts.apipetdetails;

import io.micrometer.context.ContextRegistry;
import org.slf4j.MDC;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import reactor.core.publisher.Hooks;

@SpringBootApplication
@EnableReactiveMongoRepositories
@ComponentScan(basePackages = {"com.myreflectionthoughts.apipetdetails.*"})
public class ApiPetDetailsApplication {

    public static void main(String[] args) {
        Hooks.enableAutomaticContextPropagation();
        ContextRegistry.getInstance()
                .registerThreadLocalAccessor("traceId",()-> MDC.get("traceId"), traceId-> MDC.put("traceId",traceId),()->MDC.remove("traceId"))
                .registerThreadLocalAccessor("spanId",()-> MDC.get("spanId"), spanId-> MDC.put("spanId",spanId),()->MDC.remove("spanId"));
        SpringApplication.run(ApiPetDetailsApplication.class, args);
    }
}
