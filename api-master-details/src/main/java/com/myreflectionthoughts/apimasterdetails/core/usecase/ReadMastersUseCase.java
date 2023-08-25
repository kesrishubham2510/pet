package com.myreflectionthoughts.apimasterdetails.core.usecase;

import com.myreflectionthoughts.library.contract.IGetAll;
import com.myreflectionthoughts.library.dto.response.MasterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;

public class ReadMastersUseCase {

    private final IGetAll<MasterDTO> iGetAll;

    public ReadMastersUseCase(IGetAll<MasterDTO> iGetAll) {
        this.iGetAll = iGetAll;
    }

    public Flux<MasterDTO> readMasters() {
        return iGetAll.getAll();
    }
}