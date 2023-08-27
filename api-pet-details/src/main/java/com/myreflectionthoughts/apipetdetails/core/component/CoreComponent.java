package com.myreflectionthoughts.apipetdetails.core.component;

import com.myreflectionthoughts.apipetdetails.core.usecase.*;
import com.myreflectionthoughts.library.contract.*;
import com.myreflectionthoughts.library.dto.request.AddPetDTO;
import com.myreflectionthoughts.library.dto.request.UpdatePetDTO;
import com.myreflectionthoughts.library.dto.response.DeletePetDTO;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class CoreComponent {
    @Bean
    CreatePetUseCase createPetUsecase(IAdd<AddPetDTO, PetDTO> iAddPet) {
        return new CreatePetUseCase(iAddPet);
    }

    @Bean
    ReadPetDetailsUseCase readPetDetailsUseCase(IGet<PetDTO> iGet) {
        return new ReadPetDetailsUseCase(iGet);
    }

    @Bean
    ReadPetsUseCase readPetsUseCase(IGetAll<PetDTO> iGetAll) {
        return new ReadPetsUseCase(iGetAll);
    }

    @Bean
    UpdatePetDetailsUseCase updatePetDetailsUseCase(IUpdate<PetDTO, UpdatePetDTO> iUpdate) {
        return new UpdatePetDetailsUseCase(iUpdate);
    }

    @Bean
    DeletePetDetailsUseCase deletePetDetailsUseCase(IDelete<DeletePetDTO> iDelete) {
        return new DeletePetDetailsUseCase(iDelete);
    }

    @Bean
    ReadPetsOfMasterUseCase readAllPetsOfMasterUseCase(IGetByCommonAttribute<String, PetDTO> iGetByCommonAttribute) {
        return new ReadPetsOfMasterUseCase(iGetByCommonAttribute);
    }
}
