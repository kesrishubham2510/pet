package com.myreflectionthoughts.apipetdetails.controller;

import com.myreflectionthoughts.apipetdetails.constant.ServiceConstants;
import com.myreflectionthoughts.apipetdetails.service.PetService;
import com.myreflectionthoughts.apipetdetails.utility.MappingUtility;
import com.myreflectionthoughts.library.dto.request.AddPetDTO;
import com.myreflectionthoughts.library.dto.request.UpdatePetDTO;
import com.myreflectionthoughts.library.dto.response.DeletePetDTO;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(ServiceConstants.API_QUALIFIER)
public class PetController {

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

    @GetMapping("/get/pet/{petId}")
    public Mono<ResponseEntity<PetDTO>> getPet(@PathVariable("petId") String petId){
        return petService.getInfo(Mono.just(petId)).map(mappingUtility::convertToResponse);
    }

    @GetMapping("/get/all")
    public Flux<PetDTO> getPets(){
        return petService.getAll();
    }

    @PutMapping("/update/pet/{petId}")
    public Mono<ResponseEntity<PetDTO>> updatePet(@PathVariable("petId") String petId, @RequestBody Mono<UpdatePetDTO> updatePetDTOMono){
        return petService.updateInfo(updatePetDTOMono).map(mappingUtility::convertToResponse);
    }

    @DeleteMapping("/delete/pet/{petId}")
    public Mono<ResponseEntity<DeletePetDTO>> deletePet(@PathVariable("petId") String petId){
        return petService.delete(Mono.just(petId)).map(mappingUtility::convertToResponse);
    }
}
