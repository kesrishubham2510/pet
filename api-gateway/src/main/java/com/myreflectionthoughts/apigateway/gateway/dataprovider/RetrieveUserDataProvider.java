package com.myreflectionthoughts.apigateway.gateway.dataprovider;

import com.myreflectionthoughts.apigateway.core.constant.ServiceConstant;
import com.myreflectionthoughts.library.contract.IGet;
import com.myreflectionthoughts.library.dto.response.UserDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class RetrieveUserDataProvider extends DataProvider implements IGet<UserDTO> {

    public RetrieveUserDataProvider(
            @Qualifier(ServiceConstant.masterServiceQualifier) WebClient masterServiceClient,
            @Qualifier(ServiceConstant.petServiceQualifier) WebClient petServiceClient) {
        super(masterServiceClient, petServiceClient);
    }

    @Override
    public Mono<UserDTO> getInfo(Mono<String> id) {
        return getUserInfo(id);
    }
}