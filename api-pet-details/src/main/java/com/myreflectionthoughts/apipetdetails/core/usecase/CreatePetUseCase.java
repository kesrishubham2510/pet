package com.myreflectionthoughts.apipetdetails.core.usecase;

import com.myreflectionthoughts.library.contract.IAdd;
import com.myreflectionthoughts.library.dto.request.AddPetDTO;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import reactor.core.publisher.Mono;

public class CreatePetUseCase {

    private final IAdd<AddPetDTO, PetDTO> iAddPet;

    public CreatePetUseCase(IAdd<AddPetDTO, PetDTO> iAddPet) {
        this.iAddPet = iAddPet;
    }

    public Mono<PetDTO> addPet(Mono<AddPetDTO> addPetDTOMono){
        return iAddPet.add(addPetDTOMono);
    }

}
