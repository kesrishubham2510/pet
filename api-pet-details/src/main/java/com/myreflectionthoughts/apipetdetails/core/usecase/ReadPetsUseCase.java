package com.myreflectionthoughts.apipetdetails.core.usecase;

import com.myreflectionthoughts.library.contract.IGetAll;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import reactor.core.publisher.Flux;

public class ReadPetsUseCase {

    private final IGetAll<PetDTO> iGetAll;

    public ReadPetsUseCase(IGetAll<PetDTO> iGetAll) {
        this.iGetAll = iGetAll;
    }

    public Flux<PetDTO> getPets(){
        return  iGetAll.getAll();
    }
}