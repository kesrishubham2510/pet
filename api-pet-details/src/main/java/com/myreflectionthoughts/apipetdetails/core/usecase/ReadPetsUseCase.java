package com.myreflectionthoughts.apipetdetails.core.usecase;

import com.myreflectionthoughts.library.contract.IGetAll;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;

public class ReadPetsUseCase {

    @Autowired
    private IGetAll<PetDTO> iGetAll;

    public Flux<PetDTO> getPets(){
        return  iGetAll.getAll();
    }
}