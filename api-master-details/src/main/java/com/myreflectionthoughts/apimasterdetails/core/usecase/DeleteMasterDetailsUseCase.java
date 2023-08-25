package com.myreflectionthoughts.apimasterdetails.core.usecase;

import com.myreflectionthoughts.library.contract.IDelete;
import com.myreflectionthoughts.library.dto.response.DeleteMasterDTO;
import reactor.core.publisher.Mono;

public  class DeleteMasterDetailsUseCase {

    private final IDelete<DeleteMasterDTO> iDelete;

    public DeleteMasterDetailsUseCase(IDelete<DeleteMasterDTO> iDelete) {
        this.iDelete = iDelete;
    }

    public Mono<DeleteMasterDTO> deleteMasterDetails(Mono<String> masterId) {
        return iDelete.delete(masterId);
    }
}