package com.myreflectionthoughts.apimasterdetails;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
@ComponentScan(basePackages = {"com.myreflectionthoughts.apimasterdetails.*"})
@OpenAPIDefinition(info = @Info(title = "Master API", version = "1.0", description = "Documentation for Master Rest Service"))
public class ApiMasterDetailsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiMasterDetailsApplication.class, args);
	}

}
