package com.myreflectionthoughts.apipetdetails.core.usecase;

import com.myreflectionthoughts.library.contract.IGetByCommonAttribute;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ReadPetsOfMasterUseCase {

    private final IGetByCommonAttribute<String, PetDTO> iGetByCommonAttribute;

    public ReadPetsOfMasterUseCase(IGetByCommonAttribute<String, PetDTO> iGetByCommonAttribute) {
        this.iGetByCommonAttribute = iGetByCommonAttribute;
    }

    public Flux<PetDTO> retrieveAllPetsOfMaster(Mono<String> masterId) {
        return iGetByCommonAttribute.retrieveByAttribute(masterId);
    }
}
