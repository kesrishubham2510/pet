package com.myreflectionthoughts.apipetdetails;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
@ComponentScan(basePackages = {"com.myreflectionthoughts.apipetdetails.*"})
@OpenAPIDefinition(info = @Info(title = "Pet API", version = "1.0", description = "Documentation for Pet Rest Service"))

public class ApiPetDetailsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiPetDetailsApplication.class, args);
    }
}
