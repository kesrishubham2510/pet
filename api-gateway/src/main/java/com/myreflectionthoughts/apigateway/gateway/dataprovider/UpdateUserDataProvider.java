package com.myreflectionthoughts.apigateway.gateway.dataprovider;

import com.myreflectionthoughts.library.contract.IUpdate;
import com.myreflectionthoughts.library.dto.request.UpdateUserDTO;
import com.myreflectionthoughts.library.dto.response.UserDTO;
import reactor.core.publisher.Mono;

public class UpdateUserDataProvider extends DataProvider implements IUpdate<UserDTO, UpdateUserDTO> {

    @Override
    public Mono<UserDTO> updateInfo(Mono<UpdateUserDTO> latestInformation) {
        return null;
    }
}