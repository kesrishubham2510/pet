package com.myreflectionthoughts.apipetdetails.gateway.entrypoint;

import com.myreflectionthoughts.apipetdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apipetdetails.gateway.dataprovider.DeletePetDataProvider;
import com.myreflectionthoughts.apipetdetails.gateway.dataprovider.utility.MappingUtility;
import com.myreflectionthoughts.library.dto.response.DeletePetDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(ServiceConstants.API_QUALIFIER)
public class DeletePetDetailsEndpoint{

    @Autowired
    private MappingUtility mappingUtility;

    @Autowired
    private DeletePetDataProvider deletePetDataProvider;

    @DeleteMapping("/delete/pet/{petId}")
    public Mono<ResponseEntity<DeletePetDTO>> deletePet(@PathVariable("petId") String petId){
        return deletePetDataProvider.delete(Mono.just(petId)).map(mappingUtility::convertToResponse);
    }
}
