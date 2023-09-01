package com.myreflectionthoughts.apigateway.gateway.dataprovider;

import com.myreflectionthoughts.apigateway.core.constant.ServiceConstant;
import com.myreflectionthoughts.library.contract.IUpdate;
import com.myreflectionthoughts.library.dto.request.UpdateUserDTO;
import com.myreflectionthoughts.library.dto.response.UserDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class UpdateUserDataProvider extends DataProvider implements IUpdate<UserDTO, UpdateUserDTO> {

    public UpdateUserDataProvider(
            @Qualifier(ServiceConstant.masterServiceQualifier) WebClient masterServiceClient,
            @Qualifier(ServiceConstant.petServiceQualifier) WebClient petServiceClient) {
        super(masterServiceClient, petServiceClient);
    }

    @Override
    public Mono<UserDTO> updateInfo(Mono<UpdateUserDTO> latestInformation) {

        return updateUser(latestInformation);
    }
}