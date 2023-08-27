package com.myreflectionthoughts.apigateway.core.usecase;

import com.myreflectionthoughts.library.contract.IAdd;
import com.myreflectionthoughts.library.dto.request.AddUserDTO;
import com.myreflectionthoughts.library.dto.response.UserDTO;
import reactor.core.publisher.Mono;

public class RegisterUserUseCase{
    private final IAdd<AddUserDTO, UserDTO> iAdd;

    public RegisterUserUseCase(IAdd<AddUserDTO, UserDTO> iAdd) {
        this.iAdd = iAdd;
    }

    public Mono<UserDTO> registerUser(Mono<AddUserDTO> addUserDTOMono){
        return iAdd.add(addUserDTOMono);
    }
}