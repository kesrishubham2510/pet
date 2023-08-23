package com.myreflectionthoughts.apipetdetails.gateway.dataprovider;

import com.myreflectionthoughts.apipetdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apipetdetails.core.exception.PetNotFoundException;
import com.myreflectionthoughts.apipetdetails.gateway.dataprovider.repository.PetRepository;
import com.myreflectionthoughts.apipetdetails.gateway.dataprovider.utility.MappingUtility;
import com.myreflectionthoughts.library.contract.IUpdate;
import com.myreflectionthoughts.library.dto.request.UpdatePetDTO;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UpdatePetDataProvider extends DataProvider implements  IUpdate<PetDTO, UpdatePetDTO> {

    public UpdatePetDataProvider(PetRepository petRepository, MappingUtility mappingUtility){
        this.mappingUtility = mappingUtility;
        this.petRepository = petRepository;
    }
    @Override
    public Mono<PetDTO> updateInfo(Mono<UpdatePetDTO> updatePetDTOMono) {
        return updatePetDTOMono.filterWhen(updatePetDTO -> petRepository.existsById(updatePetDTO.getId()))
                .map(mappingUtility::mapToPet)
                .flatMap(petRepository::save)
                .map(mappingUtility::mapToPetDTO)
                .switchIfEmpty(Mono.error(new PetNotFoundException(ServiceConstants.PET_NOT_FOUND_EXCEPTION)));
    }
}