package com.myreflectionthoughts.apigateway.gateway.dataprovider;

import com.myreflectionthoughts.library.contract.IGet;
import com.myreflectionthoughts.library.dto.response.UserDTO;
import reactor.core.publisher.Mono;

public class RetrieveUserDataProvider extends DataProvider implements IGet<UserDTO> {
    @Override
    public Mono<UserDTO> getInfo(Mono<String> id) {
        return null;
    }
}