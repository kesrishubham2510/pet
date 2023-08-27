package com.myreflectionthoughts.apigateway.gateway.dataprovider;

import org.springframework.web.reactive.function.client.WebClient;

public class DataProvider {
     protected final WebClient masterServiceClient;
     protected final WebClient petServiceClient;

     public DataProvider(WebClient masterServiceClient, WebClient petServiceClient) {
          this.masterServiceClient = masterServiceClient;
          this.petServiceClient = petServiceClient;
     }
}
