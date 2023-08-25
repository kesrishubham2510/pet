package com.myreflectionthoughts.apipetdetails.core.usecase;

import com.myreflectionthoughts.library.contract.IUpdate;
import com.myreflectionthoughts.library.dto.request.UpdatePetDTO;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import reactor.core.publisher.Mono;

public class UpdatePetDetailsUseCase {

    private final IUpdate<PetDTO, UpdatePetDTO> iUpdate;

    public UpdatePetDetailsUseCase(IUpdate<PetDTO, UpdatePetDTO> iUpdate) {
        this.iUpdate = iUpdate;
    }

    public Mono<PetDTO> updatePetDetails(Mono<UpdatePetDTO> updatePetDTOMono){
        return  iUpdate.updateInfo(updatePetDTOMono);
    }
}