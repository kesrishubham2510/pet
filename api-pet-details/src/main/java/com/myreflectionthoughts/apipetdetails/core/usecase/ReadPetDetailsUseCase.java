package com.myreflectionthoughts.apipetdetails.core.usecase;

import com.myreflectionthoughts.library.contract.IGet;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import reactor.core.publisher.Mono;

public class ReadPetDetailsUseCase {

    private final IGet<PetDTO> iGet;

    public ReadPetDetailsUseCase(IGet<PetDTO> iGet) {
        this.iGet = iGet;
    }

    public Mono<PetDTO> getDetails(Mono<String> petId){
        return iGet.getInfo(petId);
    }
}
