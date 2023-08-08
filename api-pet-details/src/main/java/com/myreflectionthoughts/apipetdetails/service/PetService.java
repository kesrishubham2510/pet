package com.myreflectionthoughts.apipetdetails.service;

import com.myreflectionthoughts.apipetdetails.constant.ServiceConstants;
import com.myreflectionthoughts.apipetdetails.contract.IAddPet;
import com.myreflectionthoughts.apipetdetails.contract.IDeletePet;
import com.myreflectionthoughts.apipetdetails.contract.IGetPet;
import com.myreflectionthoughts.apipetdetails.entity.Pet;
import com.myreflectionthoughts.apipetdetails.exception.PetNotFoundException;
import com.myreflectionthoughts.library.dto.request.AddPetDTO;
import com.myreflectionthoughts.library.dto.response.DeletePetDTO;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class PetService extends ServiceProvider implements
        IAddPet<AddPetDTO, PetDTO>,
        IGetPet<PetDTO>,
        IDeletePet<DeletePetDTO>{

    @Override
    public Mono<PetDTO> addPet(Mono<AddPetDTO> requestPayloadMono) {
        return requestPayloadMono
                .map(mappingUtility::mapToPet)
                .flatMap(petRepository::save)
                .map(mappingUtility::mapToPetDTO);
    }

    @Override
    public Mono<PetDTO> getPetInfo(Mono<String> petIdMono) {
        return petIdMono
                .flatMap(petRepository::findById)
                .map(mappingUtility::mapToPetDTO)
                .switchIfEmpty(Mono.error(new PetNotFoundException(ServiceConstants.PET_NOT_FOUND_EXCEPTION)));
    }

    @Override
    public Flux<PetDTO> getAllPets() {
        return petRepository
                .findAll()
                .map(mappingUtility::mapToPetDTO);
    }

    @Override
    public Mono<DeletePetDTO> deletePet(Mono<String> petIdMono) {
        return petIdMono
                .flatMap(petRepository::findById)
                .map(this::handleDeletion)
                .switchIfEmpty(Mono.error(new PetNotFoundException((ServiceConstants.PET_NOT_FOUND_EXCEPTION))));
    }

    private DeletePetDTO handleDeletion(Pet pet){
        petRepository.deleteById(pet.getId());
        return mappingUtility.createDeletePetDTO(pet.getId());
    }
}
