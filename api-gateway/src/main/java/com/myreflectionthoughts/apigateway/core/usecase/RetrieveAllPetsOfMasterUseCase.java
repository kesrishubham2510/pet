package com.myreflectionthoughts.apigateway.core.usecase;

import com.myreflectionthoughts.library.contract.IGetByCommonAttribute;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class RetrieveAllPetsOfMasterUseCase {

    private IGetByCommonAttribute<String, PetDTO> iGetByCommonAttribute;

    public RetrieveAllPetsOfMasterUseCase(IGetByCommonAttribute<String, PetDTO> iGetByCommonAttribute) {
        this.iGetByCommonAttribute = iGetByCommonAttribute;
    }

    public Flux<PetDTO> getAllPetsOfMaster(Mono<String> masterId) {
        return iGetByCommonAttribute.retrieveByAttribute(masterId);
    }
}
