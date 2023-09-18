package com.myreflectionthoughts.apipetdetails.gateway.dataprovider;

import com.myreflectionthoughts.apipetdetails.core.utils.LogUtility;
import com.myreflectionthoughts.apipetdetails.gateway.dataprovider.repository.PetRepository;
import com.myreflectionthoughts.apipetdetails.gateway.dataprovider.utility.MappingUtility;
import com.myreflectionthoughts.library.contract.IAdd;
import com.myreflectionthoughts.library.dto.request.AddPetDTO;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class CreatePetDataProvider extends DataProvider implements IAdd<AddPetDTO, PetDTO> {

    private final Logger logger;

    public CreatePetDataProvider(PetRepository petRepository, MappingUtility mappingUtility){
        this.mappingUtility = mappingUtility;
        this.petRepository = petRepository;
        logger = Logger.getLogger(CreatePetDataProvider.class.getName());
    }

    @Override
    public Mono<PetDTO> add(Mono<AddPetDTO> requestPayloadMono) {

        return requestPayloadMono
                .doOnNext(requestPayload-> LogUtility.loggerUtility.log(logger, "Initiating registration process for pet:- "+requestPayload.getName(), Level.INFO))
                .map(this::validatePayload)
                .map(mappingUtility::mapToPet)
                .doOnNext(pet-> LogUtility.loggerUtility.log(logger, "Initiating pet's data persistence...", Level.INFO))
                .flatMap(petRepository::save)
                .doOnNext(savedPet-> LogUtility.loggerUtility.log(logger, "pet's data persistence completed successfully...", Level.INFO))
                .map(mappingUtility::mapToPetDTO);
    }
}
