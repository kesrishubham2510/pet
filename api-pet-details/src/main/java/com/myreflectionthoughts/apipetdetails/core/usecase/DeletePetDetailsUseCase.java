package com.myreflectionthoughts.apipetdetails.core.usecase;

import com.myreflectionthoughts.library.contract.IDelete;
import com.myreflectionthoughts.library.dto.response.DeletePetDTO;
import reactor.core.publisher.Mono;

public class DeletePetDetailsUseCase {

    private final IDelete<DeletePetDTO> iDelete;

    public DeletePetDetailsUseCase(IDelete<DeletePetDTO> iDelete) {
        this.iDelete = iDelete;
    }

    public Mono<DeletePetDTO> deletePet(Mono<String> petIdMono){
        return iDelete.delete(petIdMono);
    }
}