package com.myreflectionthoughts.apipetdetails.gateway.dataprovider;

import com.myreflectionthoughts.apipetdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apipetdetails.core.exception.PetNotFoundException;
import com.myreflectionthoughts.apipetdetails.gateway.dataprovider.repository.PetRepository;
import com.myreflectionthoughts.apipetdetails.gateway.dataprovider.utility.MappingUtility;
import com.myreflectionthoughts.library.contract.IDelete;
import com.myreflectionthoughts.library.dto.response.DeletePetDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DeletePetDataProvider extends DataProvider implements IDelete<DeletePetDTO> {

    public DeletePetDataProvider(PetRepository petRepository, MappingUtility mappingUtility){
        this.mappingUtility = mappingUtility;
        this.petRepository = petRepository;
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
}