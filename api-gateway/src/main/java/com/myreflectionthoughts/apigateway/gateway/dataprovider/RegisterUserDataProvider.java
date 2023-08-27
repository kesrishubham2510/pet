package com.myreflectionthoughts.apigateway.gateway.dataprovider;

import com.myreflectionthoughts.library.contract.IAdd;
import com.myreflectionthoughts.library.dto.request.AddUserDTO;
import com.myreflectionthoughts.library.dto.response.UserDTO;
import reactor.core.publisher.Mono;

public class RegisterUserDataProvider extends DataProvider implements IAdd<AddUserDTO, UserDTO> {



    @Override
    public Mono<UserDTO> add(Mono<AddUserDTO> requestPayload) {
        return null;
    }
}