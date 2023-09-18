package com.myreflectionthoughts.apipetdetails.core.component;

import com.myreflectionthoughts.apipetdetails.core.usecase.*;
import com.myreflectionthoughts.apipetdetails.core.utils.LogUtility;
import com.myreflectionthoughts.library.contract.*;
import com.myreflectionthoughts.library.dto.request.AddPetDTO;
import com.myreflectionthoughts.library.dto.request.UpdatePetDTO;
import com.myreflectionthoughts.library.dto.response.DeletePetDTO;
import com.myreflectionthoughts.library.dto.response.PetDTO;
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
    CreatePetUseCase createPetUseCase(IAdd<AddPetDTO, PetDTO> iAddPet) {
        LogUtility.loggerUtility.log(logger, "Initialising CreatePetUsecase bean", Level.FINE);
        return new CreatePetUseCase(iAddPet);
    }

    @Bean
    ReadPetDetailsUseCase readPetDetailsUseCase(IGet<PetDTO> iGet) {
        LogUtility.loggerUtility.log(logger, "Initialising ReadPetDetailsUseCase bean", Level.FINE);
        return new ReadPetDetailsUseCase(iGet);
    }

    @Bean
    ReadPetsUseCase readPetsUseCase(IGetAll<PetDTO> iGetAll) {
        LogUtility.loggerUtility.log(logger, "Initialising ReadPetsUseCase bean", Level.FINE);
        return new ReadPetsUseCase(iGetAll);
    }

    @Bean
    UpdatePetDetailsUseCase updatePetDetailsUseCase(IUpdate<PetDTO, UpdatePetDTO> iUpdate) {
        LogUtility.loggerUtility.log(logger, "Initialising UpdatePetDetailsUseCase bean", Level.FINE);
        return new UpdatePetDetailsUseCase(iUpdate);
    }

    @Bean
    DeletePetDetailsUseCase deletePetDetailsUseCase(IDelete<DeletePetDTO> iDelete) {
        LogUtility.loggerUtility.log(logger, "Initialising DeletePetDetailsUseCase bean", Level.FINE);
        return new DeletePetDetailsUseCase(iDelete);
    }

    @Bean
    ReadPetsOfMasterUseCase readAllPetsOfMasterUseCase(IGetByCommonAttribute<String, PetDTO> iGetByCommonAttribute) {
        LogUtility.loggerUtility.log(logger, "Initialising ReadAllPetsOfMasterUseCase bean", Level.FINE);
        return new ReadPetsOfMasterUseCase(iGetByCommonAttribute);
    }
}
