package com.myreflectionthoughts.apigateway.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(value = "SwaggerConfig")
public class SwaggerConfig {

    @Value("${SwaggerConfig.info.description}")
    private String description;
    @Value("${SwaggerConfig.info.title}")
    private String title;
    @Value("${SwaggerConfig.info.summary}")
    private String summary;
    @Value("${SwaggerConfig.info.version}")
    private String version;


    @Value("${SwaggerConfig.contact.name}")
    private String name;
    @Value("${SwaggerConfig.contact.email}")
    private String email;
    @Value("${SwaggerConfig.contact.url}")
    private String url;

    @Value("${SwaggerConfig.servers[0].url}")
    private String serverUrl;

    @Bean
    public OpenAPI openAPI() {
        OpenAPI apiGateway = new OpenAPI();

        apiGateway.setInfo(getInfo());
        return apiGateway;
    }

    private Info getInfo() {
        Info apiGatewayInfo = new Info();

        apiGatewayInfo.setDescription(description);
        apiGatewayInfo.setSummary(summary);
        apiGatewayInfo.setTitle(title);
        apiGatewayInfo.setVersion(version);
        apiGatewayInfo.setContact(getContact());

        return apiGatewayInfo;
    }

    private Contact getContact() {
        Contact contact = new Contact();
        contact.name(name);
        contact.setEmail(email);
        contact.setUrl(url);
        return contact;
    }
}
