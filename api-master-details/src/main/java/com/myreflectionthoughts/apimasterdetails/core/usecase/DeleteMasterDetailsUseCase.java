package com.myreflectionthoughts.apimasterdetails.core.usecase;

import com.myreflectionthoughts.library.contract.IDelete;
import com.myreflectionthoughts.library.dto.response.DeleteMasterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

public  class DeleteMasterDetailsUseCase {

    @Autowired
    private IDelete<DeleteMasterDTO> iDelete;

    public Mono<DeleteMasterDTO> deleteMasterDetails(Mono<String> masterId) {
        return iDelete.delete(masterId);
    }
}