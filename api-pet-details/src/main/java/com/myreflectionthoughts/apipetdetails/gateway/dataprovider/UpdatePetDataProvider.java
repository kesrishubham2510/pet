package com.myreflectionthoughts.apipetdetails.gateway.dataprovider;

import com.myreflectionthoughts.apipetdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apipetdetails.core.exception.PetNotFoundException;
import com.myreflectionthoughts.apipetdetails.core.utils.LogUtility;
import com.myreflectionthoughts.apipetdetails.gateway.dataprovider.repository.PetRepository;
import com.myreflectionthoughts.apipetdetails.gateway.dataprovider.utility.MappingUtility;
import com.myreflectionthoughts.library.contract.IUpdate;
import com.myreflectionthoughts.library.dto.request.UpdatePetDTO;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class UpdatePetDataProvider extends DataProvider implements  IUpdate<PetDTO, UpdatePetDTO> {

    private final Logger logger;

    public UpdatePetDataProvider(PetRepository petRepository, MappingUtility mappingUtility){
        this.mappingUtility = mappingUtility;
        this.petRepository = petRepository;
        this.logger = Logger.getLogger(UpdatePetDataProvider.class.getName());
    }

    @Override
    public Mono<PetDTO> updateInfo(Mono<UpdatePetDTO> updatePetDTOMono) {
        return updatePetDTOMono
                .map(this::validatePayload)
                .doOnNext(validatedRequestPayload-> LogUtility.loggerUtility.log(logger, "Checking petId:- "+validatedRequestPayload.getId()+" in database...", Level.INFO))
                .filterWhen(updatePetDTO -> petRepository.existsById(updatePetDTO.getId()))
                .flatMap(this::validateMasterId)
                .map(mappingUtility::mapToPet)
                .doOnNext(updatedPet-> LogUtility.loggerUtility.log(logger, "Latest changes applied to pet:- "+updatedPet.getId()+" information",Level.INFO))
                .flatMap(petRepository::save)
                .doOnNext(savedUpdatedPet-> LogUtility.loggerUtility.log(logger, "Pet:- "+savedUpdatedPet.getId()+" persisted with latest information", Level.INFO))
                .map(mappingUtility::mapToPetDTO)
                .switchIfEmpty(Mono.error(new PetNotFoundException(ServiceConstants.PET_NOT_FOUND_EXCEPTION)));
    }

    // will correct the masterId, if it's wrong
    private Mono<UpdatePetDTO> validateMasterId(UpdatePetDTO updatePetDTO){
        return petRepository.findById(updatePetDTO.getId())
                .doOnNext(pet -> LogUtility.loggerUtility.log(logger, "Validating masterId:- "+updatePetDTO.getMaster(), Level.INFO))
                .map(existingPet->{
            updatePetDTO.setMaster(existingPet.getMaster());
            return updatePetDTO;
        });
    }
}