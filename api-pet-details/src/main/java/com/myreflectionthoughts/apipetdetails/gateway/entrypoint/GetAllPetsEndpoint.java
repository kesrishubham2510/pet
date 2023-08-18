package com.myreflectionthoughts.apipetdetails.gateway.entrypoint;

import com.myreflectionthoughts.apipetdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apipetdetails.core.usecase.ReadPetsUseCase;
import com.myreflectionthoughts.apipetdetails.gateway.dataprovider.utility.MappingUtility;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping(ServiceConstants.API_QUALIFIER)
public class GetAllPetsEndpoint{

    @Autowired
    private MappingUtility mappingUtility;

    @Autowired
    private ReadPetsUseCase readPetsUseCase;

    @GetMapping("/get/all")
    public Flux<PetDTO> getPets(){
        return readPetsUseCase.getPets();
    }
}
