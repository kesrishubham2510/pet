package com.myreflectionthoughts.apipetdetails.gateway.entrypoint;

import com.myreflectionthoughts.apipetdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apipetdetails.core.utility.MappingUtility;
import com.myreflectionthoughts.apipetdetails.gateway.service.PetService;
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
    private PetService petService;


    @GetMapping("/get/pet/{petId}")
    public Mono<ResponseEntity<PetDTO>> getPet(@PathVariable("petId") String petId){
        return petService.getInfo(Mono.just(petId)).map(mappingUtility::convertToResponse);
    }
}
