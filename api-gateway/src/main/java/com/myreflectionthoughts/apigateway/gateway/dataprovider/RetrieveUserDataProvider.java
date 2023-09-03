package com.myreflectionthoughts.apigateway.gateway.dataprovider;

import com.myreflectionthoughts.apigateway.core.constant.ServiceConstant;
import com.myreflectionthoughts.library.contract.IGet;
import com.myreflectionthoughts.library.dto.response.MasterDTO;
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

    private Mono<UserDTO> getUserInfo(Mono<String> masterIdMono) {
        return masterIdMono.flatMap(masterId ->
                handleMasterInfoRetrieval(masterId).flatMap(
                        masterDTO ->
                                this.handleAllPetsOfUserRetrieval(masterId).collectList().map(
                                        pets -> {
                                            UserDTO userDTO = new UserDTO();
                                            userDTO.setPets(pets);
                                            userDTO.setMaster(masterDTO);
                                            return userDTO;
                                        }
                                )
                )

        );
    }

    private Mono<MasterDTO> handleMasterInfoRetrieval(String masterId) {
        return masterServiceClient.get()
                .uri(String.format("/get/master/%s", masterId))
                .retrieve()
                .bodyToMono(MasterDTO.class);
    }


}