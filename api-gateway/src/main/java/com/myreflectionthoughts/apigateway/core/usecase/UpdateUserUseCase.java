package com.myreflectionthoughts.apigateway.core.usecase;

import com.myreflectionthoughts.library.contract.IUpdate;
import com.myreflectionthoughts.library.dto.request.UpdateUserDTO;
import com.myreflectionthoughts.library.dto.response.UserDTO;
import reactor.core.publisher.Mono;

public class UpdateUserUseCase{

    private final  IUpdate<UserDTO, UpdateUserDTO> iUpdate;

    public UpdateUserUseCase(IUpdate<UserDTO, UpdateUserDTO> iUpdate) {
        this.iUpdate = iUpdate;
    }

    public Mono<UserDTO> updateUser(Mono<UpdateUserDTO> updateUserDTOMono){
        return iUpdate.updateInfo(updateUserDTOMono);
    }
}