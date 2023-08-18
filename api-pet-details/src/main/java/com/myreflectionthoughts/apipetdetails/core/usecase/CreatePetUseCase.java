package com.myreflectionthoughts.apipetdetails.core.usecase;

import com.myreflectionthoughts.library.contract.IAdd;
import com.myreflectionthoughts.library.dto.request.AddPetDTO;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

public class CreatePetUseCase {

    @Autowired
    private IAdd<AddPetDTO, PetDTO> iAddPet;

    public Mono<PetDTO> addPet(Mono<AddPetDTO> addPetDTOMono){
        return iAddPet.add(addPetDTOMono);
    }

}
