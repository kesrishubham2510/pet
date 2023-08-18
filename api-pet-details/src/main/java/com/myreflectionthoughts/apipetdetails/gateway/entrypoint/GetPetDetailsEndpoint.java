package com.myreflectionthoughts.apipetdetails.gateway.entrypoint;

import com.myreflectionthoughts.apipetdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apipetdetails.core.usecase.ReadPetDetailsUseCase;
import com.myreflectionthoughts.apipetdetails.gateway.dataprovider.utility.MappingUtility;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(ServiceConstants.API_QUALIFIER)
public class GetPetDetailsEndpoint{

    @Autowired
    private MappingUtility mappingUtility;

    @Autowired
    private ReadPetDetailsUseCase readPetDetailsUseCase;

    @GetMapping("/get/pet/{petId}")
    public Mono<ResponseEntity<PetDTO>> getPet(@PathVariable("petId") String petId){
        return readPetDetailsUseCase.getDetails(Mono.just(petId)).map(mappingUtility::convertToResponse);
    }
}
