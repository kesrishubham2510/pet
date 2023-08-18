package com.myreflectionthoughts.apipetdetails.gateway.entrypoint;

import com.myreflectionthoughts.apipetdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apipetdetails.core.usecase.CreatePetUseCase;
import com.myreflectionthoughts.apipetdetails.gateway.dataprovider.utility.MappingUtility;
import com.myreflectionthoughts.library.dto.request.AddPetDTO;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(ServiceConstants.API_QUALIFIER)
public class CreatePetEndpoint{

    @Autowired
    private MappingUtility mappingUtility;

    @Autowired
    private CreatePetUseCase createPetUseCase;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/add")
    public Mono<ResponseEntity<PetDTO>> addPet(@RequestBody Mono<AddPetDTO> addPetDTOMono){
        return createPetUseCase.addPet(addPetDTOMono)
                .map(mappingUtility::convertToResponseCreated);
    }
}
