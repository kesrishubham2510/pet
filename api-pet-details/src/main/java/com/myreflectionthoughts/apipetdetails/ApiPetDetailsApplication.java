package com.myreflectionthoughts.apipetdetails;

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
        Hooks.enableContextLossTracking();
        SpringApplication.run(ApiPetDetailsApplication.class, args);
    }
}
