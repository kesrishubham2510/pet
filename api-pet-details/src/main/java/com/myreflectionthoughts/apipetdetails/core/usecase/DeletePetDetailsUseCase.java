package com.myreflectionthoughts.apipetdetails.core.usecase;

import com.myreflectionthoughts.library.contract.IDelete;
import com.myreflectionthoughts.library.dto.response.DeletePetDTO;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

public class DeletePetDetailsUseCase {

    @Autowired
    private IDelete<DeletePetDTO> iDelete;

    public Mono<DeletePetDTO> deletePet(Mono<String> petIdMono){
        return iDelete.delete(petIdMono);
    }
}