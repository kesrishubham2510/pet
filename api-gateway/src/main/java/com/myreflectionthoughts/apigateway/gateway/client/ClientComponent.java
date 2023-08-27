package com.myreflectionthoughts.apigateway.gateway.client;

import com.myreflectionthoughts.apigateway.core.constant.ServiceConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ClientComponent {

    @Value("${restClient.masterService}")
    private String masterServiceURL;

    @Value("${restClient.petService}")
    private String petServiceURL;

    @Bean(name = ServiceConstant.masterServiceQualifier)
    public WebClient masterWebClient(){
        return WebClient.create(masterServiceURL);
    }

    @Bean(name = ServiceConstant.petServiceQualifier)
    public WebClient petWebClient(){
        return WebClient.create(petServiceURL);
    }
}