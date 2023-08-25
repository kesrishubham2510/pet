package com.myreflectionthoughts.apimasterdetails.core.usecase;

import com.myreflectionthoughts.library.contract.IGet;
import com.myreflectionthoughts.library.dto.response.MasterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

public class ReadMasterDetailsUseCase {

    private final IGet<MasterDTO> iGet;

    public ReadMasterDetailsUseCase(IGet<MasterDTO> iGet) {
        this.iGet = iGet;
    }

    public Mono<MasterDTO> readMasterDetails(Mono<String> masterId){
        return iGet.getInfo(masterId);
    }


}