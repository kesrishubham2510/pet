package com.myreflectionthoughts.apipetdetails.gateway.entrypoint;

import com.myreflectionthoughts.apipetdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apipetdetails.core.utility.MappingUtility;
import com.myreflectionthoughts.apipetdetails.gateway.service.PetService;
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
    private PetService petService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/add")
    public Mono<ResponseEntity<PetDTO>> addPet(@RequestBody Mono<AddPetDTO> addPetDTOMono){
        return petService.add(addPetDTOMono)
                .map(mappingUtility::convertToResponseCreated);
    }
}
