package com.myreflectionthoughts.apipetdetails;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
@ComponentScan(basePackages = {"com.myreflectionthoughts.apipetdetails.*"})
public class ApiPetDetailsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiPetDetailsApplication.class, args);
    }
}
