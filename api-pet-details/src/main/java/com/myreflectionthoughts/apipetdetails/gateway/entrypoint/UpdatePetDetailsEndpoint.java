package com.myreflectionthoughts.apipetdetails.gateway.entrypoint;

import com.myreflectionthoughts.apipetdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apipetdetails.core.usecase.UpdatePetDetailsUseCase;
import com.myreflectionthoughts.apipetdetails.gateway.dataprovider.utility.MappingUtility;
import com.myreflectionthoughts.library.dto.request.UpdatePetDTO;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(ServiceConstants.API_QUALIFIER)
public class UpdatePetDetailsEndpoint{

    @Autowired
    private MappingUtility mappingUtility;

    @Autowired
    private UpdatePetDetailsUseCase updatePetDetailsUseCase;

    @PutMapping("/update/pet/{petId}")
    public Mono<ResponseEntity<PetDTO>> updatePet(@PathVariable("petId") String petId, @RequestBody Mono<UpdatePetDTO> updatePetDTOMono){
        return updatePetDetailsUseCase.updatePetDetails(updatePetDTOMono).map(mappingUtility::convertToResponse);
    }
}
