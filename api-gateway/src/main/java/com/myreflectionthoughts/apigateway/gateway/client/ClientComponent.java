package com.myreflectionthoughts.apigateway.gateway.client;

import com.myreflectionthoughts.apigateway.core.constant.ServiceConstant;
import com.myreflectionthoughts.apigateway.core.utils.LogUtility;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class ClientComponent {

    private final Logger logger;

    public ClientComponent() {
        this.logger = Logger.getLogger(ClientComponent.class.getName());
    }

    @Value("${restClient.masterService}")
    private String masterServiceURL;

    @Value("${restClient.petService}")
    private String petServiceURL;

    @Bean(name = ServiceConstant.masterServiceQualifier)
    public WebClient masterWebClient(){
        LogUtility.loggerUtility.log(logger, "Initialising master-service-client "+masterServiceURL, Level.INFO);
        return WebClient.create(masterServiceURL);
    }

    @Bean(name = ServiceConstant.petServiceQualifier)
    public WebClient petWebClient(){
        LogUtility.loggerUtility.log(logger, "Initialising pet-service-client "+petServiceURL, Level.INFO);
        return WebClient.create(petServiceURL);
    }
}