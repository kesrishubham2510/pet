package com.myreflectionthoughts.apipetdetails.service;

import com.myreflectionthoughts.apipetdetails.constant.ServiceConstants;
import com.myreflectionthoughts.apipetdetails.exception.PetNotFoundException;
import com.myreflectionthoughts.library.contract.IAdd;
import com.myreflectionthoughts.library.contract.IDelete;
import com.myreflectionthoughts.library.contract.IGet;
import com.myreflectionthoughts.library.contract.IUpdate;
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
    public Mono<PetDTO> add(Mono<AddPetDTO> requestPayloadMono) {
        return requestPayloadMono
                .map(mappingUtility::mapToPet)
                .flatMap(petRepository::save)
                .map(mappingUtility::mapToPetDTO);
    }

    @Override
    public Mono<PetDTO> getInfo(Mono<String> petIdMono) {
        return petIdMono
                .flatMap(petRepository::findById)
                .map(mappingUtility::mapToPetDTO)
                .switchIfEmpty(Mono.error(new PetNotFoundException(ServiceConstants.PET_NOT_FOUND_EXCEPTION)));
    }

    @Override
    public Flux<PetDTO> getAll() {
        return petRepository
                .findAll()
                .map(mappingUtility::mapToPetDTO);
    }

    @Override
    public Mono<DeletePetDTO> delete(Mono<String> petIdMono) {
        return petIdMono.filterWhen(petRepository::existsById)
                .map(this::handleDeletion)
                .switchIfEmpty(Mono.error(new PetNotFoundException((ServiceConstants.PET_NOT_FOUND_EXCEPTION))));
    }

    private DeletePetDTO handleDeletion(String petId){
        petRepository.deleteById(petId);
        return mappingUtility.createDeletePetDTO(petId);
    }

    @Override
    public Mono<PetDTO> updateInfo(Mono<UpdatePetDTO> updatePetDTOMono) {
        return updatePetDTOMono.filterWhen(updatePetDTO -> petRepository.existsById(updatePetDTO.getId()))
                               .map(mappingUtility::mapToPet)
                               .flatMap(petRepository::save)
                               .map(mappingUtility::mapToPetDTO)
                               .switchIfEmpty(Mono.error(new PetNotFoundException(ServiceConstants.PET_NOT_FOUND_EXCEPTION))) ;
    }
}
