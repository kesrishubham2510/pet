package com.myreflectionthoughts.apipetdetails.core.usecase;

import com.myreflectionthoughts.library.contract.IUpdate;
import com.myreflectionthoughts.library.dto.request.UpdatePetDTO;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

public class UpdatePetDetailsUseCase {

    @Autowired
    private IUpdate<PetDTO, UpdatePetDTO> iUpdate;

    public Mono<PetDTO> updatePetDetails(Mono<UpdatePetDTO> updatePetDTOMono){
        return  iUpdate.updateInfo(updatePetDTOMono);
    }
}