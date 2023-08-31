package com.myreflectionthoughts.apipetdetails.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
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
        OpenAPI apiPetDetails = new OpenAPI();

        apiPetDetails.setInfo(getInfo());
        return apiPetDetails;
    }

    private Info getInfo() {
        Info apiPetDetailsInfo = new Info();

        apiPetDetailsInfo.setDescription(description);
        apiPetDetailsInfo.setSummary(summary);
        apiPetDetailsInfo.setTitle(title);
        apiPetDetailsInfo.setVersion(version);
        apiPetDetailsInfo.setContact(getContact());

        return apiPetDetailsInfo;
    }

    private Contact getContact() {
        Contact contact = new Contact();
        contact.name(name);
        contact.setEmail(email);
        contact.setUrl(url);
        return contact;
    }
}