package com.myreflectionthoughts.apipetdetails.gateway.dataprovider;

import com.myreflectionthoughts.apipetdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apipetdetails.core.exception.PetNotFoundException;
import com.myreflectionthoughts.apipetdetails.core.utils.LogUtility;
import com.myreflectionthoughts.apipetdetails.gateway.dataprovider.repository.PetRepository;
import com.myreflectionthoughts.apipetdetails.gateway.dataprovider.utility.MappingUtility;
import com.myreflectionthoughts.library.contract.IGet;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public  class PetDataProvider extends DataProvider implements IGet<PetDTO> {

    private final Logger logger;

    public PetDataProvider(PetRepository petRepository, MappingUtility mappingUtility){
        this.mappingUtility = mappingUtility;
        this.petRepository = petRepository;
        logger = Logger.getLogger(PetDataProvider.class.getName());
    }

    @Override
    public Mono<PetDTO> getInfo(Mono<String> petIdMono) {
        return petIdMono
                .doOnNext(petId-> LogUtility.loggerUtility.log(logger, "Initiating information retrieval for pet:- "+petId, Level.INFO))
                .flatMap(petRepository::findById)
                .doOnNext(retrievedPet-> LogUtility.loggerUtility.log(logger, "Information retrieved for pet:- "+retrievedPet.getId()+" successfully...",Level.INFO))
                .map(mappingUtility::mapToPetDTO)
                .switchIfEmpty(Mono.error(new PetNotFoundException(ServiceConstants.PET_NOT_FOUND_EXCEPTION)));
    }
}