package com.myreflectionthoughts.apipetdetails.core.usecase;

import com.myreflectionthoughts.library.contract.IGet;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

public class ReadPetDetailsUseCase {

    @Autowired
    private IGet<PetDTO> iGet;

    public Mono<PetDTO> getDetails(Mono<String> petId){
        return iGet.getInfo(petId);
    }
}
