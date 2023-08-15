package com.myreflectionthoughts.apipetdetails.service;

import com.myreflectionthoughts.apipetdetails.constant.ServiceConstants;
import com.myreflectionthoughts.library.contract.IAdd;
import com.myreflectionthoughts.library.contract.IDelete;
import com.myreflectionthoughts.library.contract.IGet;
import com.myreflectionthoughts.library.contract.IUpdate;
import com.myreflectionthoughts.apipetdetails.entity.Pet;
import com.myreflectionthoughts.apipetdetails.exception.PetNotFoundException;
import com.myreflectionthoughts.library.dto.request.AddPetDTO;
import com.myreflectionthoughts.library.dto.request.UpdatePetDTO;
import com.myreflectionthoughts.library.dto.response.DeletePetDTO;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PetService extends ServiceProvider implements
        IAdd<AddPetDTO, PetDTO>,
        IGet<PetDTO>,
        IDelete<DeletePetDTO>,
        IUpdate<PetDTO,UpdatePetDTO> {

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

    @Override
    public Mono<PetDTO> updatePetInfo(Mono<UpdatePetDTO> updatePetDTOMono) {
        return updatePetDTOMono.flatMap(this::checkUpdatePetInfo)
                               .map(mappingUtility::mapToPet)
                               .flatMap(petRepository::save)
                               .map(mappingUtility::mapToPetDTO);
    }

    private Mono<UpdatePetDTO> checkUpdatePetInfo(UpdatePetDTO updatePetDTO) {
        return petRepository.findById(updatePetDTO.getId())
                            .map(pet-> updatePetDTO)
                            .switchIfEmpty(Mono.error(new PetNotFoundException(ServiceConstants.PET_NOT_FOUND_EXCEPTION)));
    }
}
