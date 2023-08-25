package com.myreflectionthoughts.apimasterdetails.core.usecase;

import com.myreflectionthoughts.library.contract.IUpdate;
import com.myreflectionthoughts.library.dto.request.UpdateMasterDTO;
import com.myreflectionthoughts.library.dto.response.MasterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

public class UpdateMasterDetailsUseCase {

    private final IUpdate<MasterDTO, UpdateMasterDTO> iUpdate;

    public UpdateMasterDetailsUseCase(IUpdate<MasterDTO, UpdateMasterDTO> iUpdate) {
        this.iUpdate = iUpdate;
    }

    public Mono<MasterDTO> updateMasterDetails(Mono<UpdateMasterDTO> updateMasterDTOMono){
        return iUpdate.updateInfo(updateMasterDTOMono);
    }
}