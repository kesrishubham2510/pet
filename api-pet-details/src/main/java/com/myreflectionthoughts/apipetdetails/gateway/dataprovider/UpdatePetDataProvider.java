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
        // ToDO:- Add in the test scenarios for validateMasterId function call
        return updatePetDTOMono
                .filterWhen(updatePetDTO -> petRepository.existsById(updatePetDTO.getId()))
                .flatMap(this::validateMasterId)
                .map(mappingUtility::mapToPet)
                .flatMap(petRepository::save)
                .map(mappingUtility::mapToPetDTO)
                .switchIfEmpty(Mono.error(new PetNotFoundException(ServiceConstants.PET_NOT_FOUND_EXCEPTION)));
    }

    // will correct the masterId, even if it's wrong or not provided
    private Mono<UpdatePetDTO> validateMasterId(UpdatePetDTO updatePetDTO){
        return petRepository.findById(updatePetDTO.getId()).map(existingPet->{
            updatePetDTO.setMaster(existingPet.getMaster());
            return updatePetDTO;
        });
    }
}