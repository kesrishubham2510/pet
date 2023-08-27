package com.myreflectionthoughts.apigateway.core.usecase;

import com.myreflectionthoughts.library.contract.IGet;
import com.myreflectionthoughts.library.dto.response.UserDTO;
import reactor.core.publisher.Mono;

public class RetrieveUserUseCase {

    private final IGet<UserDTO> iGet;

    public RetrieveUserUseCase(IGet<UserDTO> iGet) {
        this.iGet = iGet;
    }

    public Mono<UserDTO> retrieveUser(Mono<String> userId){
        return iGet.getInfo(userId);
    }
}