package com.myreflectionthoughts.apigateway.core.usecase;

import com.myreflectionthoughts.library.contract.IGetAll;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import reactor.core.publisher.Flux;

public class DemoDataStreamUseCase {

    private IGetAll<PetDTO> iGetAll;

    public DemoDataStreamUseCase(IGetAll<PetDTO> iGetAll) {
        this.iGetAll = iGetAll;
    }

    public Flux<PetDTO> demoStreaming(){
        return iGetAll.getAll();
    }
}
