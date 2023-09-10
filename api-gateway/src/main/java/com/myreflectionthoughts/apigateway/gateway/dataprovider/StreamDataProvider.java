package com.myreflectionthoughts.apigateway.gateway.dataprovider;

import com.myreflectionthoughts.apigateway.core.constant.ServiceConstant;
import com.myreflectionthoughts.library.contract.IGetAll;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class StreamDataProvider extends DataProvider implements IGetAll<PetDTO> {

    public StreamDataProvider(
            @Qualifier(ServiceConstant.masterServiceQualifier) WebClient masterServiceClient,
            @Qualifier(ServiceConstant.petServiceQualifier) WebClient petServiceClient) {
        super(masterServiceClient, petServiceClient);
    }

    @Override
    public Flux<PetDTO> getAll() {
        return petServiceClient.get()
                .uri("/get/all")
                .retrieve()
                .bodyToFlux(PetDTO.class);
    }
}
