package com.myreflectionthoughts.apimasterdetails.core.usecase;

import com.myreflectionthoughts.library.contract.IAdd;
import com.myreflectionthoughts.library.dto.request.AddMasterDTO;
import com.myreflectionthoughts.library.dto.response.MasterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

public class CreateMasterUseCase {

    @Autowired
    private IAdd<AddMasterDTO, MasterDTO> iAdd;

    public Mono<MasterDTO> createMaster(Mono<AddMasterDTO> addMasterDTOMono){
        return iAdd.add(addMasterDTOMono);
    }
}