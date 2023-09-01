package com.myreflectionthoughts.apigateway.core.component;

import com.myreflectionthoughts.apigateway.core.usecase.*;
import com.myreflectionthoughts.library.contract.IAdd;
import com.myreflectionthoughts.library.contract.IGet;
import com.myreflectionthoughts.library.contract.IGetByCommonAttribute;
import com.myreflectionthoughts.library.contract.IUpdate;
import com.myreflectionthoughts.library.dto.request.AddUserDTO;
import com.myreflectionthoughts.library.dto.request.UpdatePetDTO;
import com.myreflectionthoughts.library.dto.request.UpdateUserDTO;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import com.myreflectionthoughts.library.dto.response.UserDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class CoreComponent {

    @Bean
    public RegisterUserUseCase registerUserUseCase(IAdd<AddUserDTO, UserDTO> iAdd) {
        return new RegisterUserUseCase(iAdd);
    }

    @Bean
    public RetrieveUserUseCase retrieveUserUseCase(IGet<UserDTO> iGet) {
        return new RetrieveUserUseCase(iGet);
    }

    @Bean
    public UpdateUserUseCase updateUserUseCase(IUpdate<UserDTO, UpdateUserDTO> iUpdate) {
        return new UpdateUserUseCase(iUpdate);
    }

    @Bean
    public RetrieveAllPetsOfMasterUseCase retrieveAllPetsOfMasterUseCase(IGetByCommonAttribute<String, PetDTO> iGetByCommonAttribute) {
        return new RetrieveAllPetsOfMasterUseCase(iGetByCommonAttribute);
    }

   @Bean
    public  UpdatePetDetailsUseCase updatePetDetailsUseCase(IUpdate<PetDTO, UpdatePetDTO> iUpdate){
        return new UpdatePetDetailsUseCase(iUpdate);
   }
}
