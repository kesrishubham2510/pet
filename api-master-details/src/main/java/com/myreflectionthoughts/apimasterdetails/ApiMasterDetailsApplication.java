package com.myreflectionthoughts.apimasterdetails;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
@ComponentScan(basePackages = {"com.myreflectionthoughts.apimasterdetails.*"})
public class ApiMasterDetailsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiMasterDetailsApplication.class, args);
    }

}
