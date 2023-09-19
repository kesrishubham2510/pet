package com.myreflectionthoughts.apigateway.core.component;

import com.myreflectionthoughts.apigateway.core.usecase.*;
import com.myreflectionthoughts.apigateway.core.utils.LogUtility;
import com.myreflectionthoughts.library.contract.*;
import com.myreflectionthoughts.library.dto.request.AddUserDTO;
import com.myreflectionthoughts.library.dto.request.UpdatePetDTO;
import com.myreflectionthoughts.library.dto.request.UpdateUserDTO;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import com.myreflectionthoughts.library.dto.response.UserDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class CoreComponent {

    private final Logger logger;

    public CoreComponent(){
        logger = Logger.getLogger(CoreComponent.class.getName());
    }

    @Bean
    public RegisterUserUseCase registerUserUseCase(IAdd<AddUserDTO, UserDTO> iAdd) {
        LogUtility.loggerUtility.log(logger, "Initialising RegisterUserUseCase bean...", Level.FINE);
        return new RegisterUserUseCase(iAdd);
    }

    @Bean
    public RetrieveUserUseCase retrieveUserUseCase(IGet<UserDTO> iGet) {
        LogUtility.loggerUtility.log(logger, "Initialising RetrieveUserUseCase bean...", Level.FINE);
        return new RetrieveUserUseCase(iGet);
    }

    @Bean
    public UpdateUserUseCase updateUserUseCase(IUpdate<UserDTO, UpdateUserDTO> iUpdate) {
        LogUtility.loggerUtility.log(logger, "Initialising UpdateUserUseCase bean...", Level.FINE);
        return new UpdateUserUseCase(iUpdate);
    }

    @Bean
    public RetrieveAllPetsOfMasterUseCase retrieveAllPetsOfMasterUseCase(IGetByCommonAttribute<String, PetDTO> iGetByCommonAttribute) {
        LogUtility.loggerUtility.log(logger, "Initialising RetrieveAllPetsOfMasterUseCase bean...", Level.FINE);
        return new RetrieveAllPetsOfMasterUseCase(iGetByCommonAttribute);
    }

   @Bean
    public  UpdatePetDetailsUseCase updatePetDetailsUseCase(IUpdate<PetDTO, UpdatePetDTO> iUpdate){
        LogUtility.loggerUtility.log(logger, "Initialising UpdatePetDetailsUseCase bean...", Level.FINE);
        return new UpdatePetDetailsUseCase(iUpdate);
   }

   @Bean
    public DemoDataStreamUseCase demoDataStreamUseCase(IGetAll<PetDTO> iGetAll){
        LogUtility.loggerUtility.log(logger, "Initialising DemoDataStreamUseCase bean...", Level.FINE);
        return new DemoDataStreamUseCase(iGetAll);
   }
}
