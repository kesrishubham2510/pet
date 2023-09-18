package com.myreflectionthoughts.apipetdetails.gateway.dataprovider;

import com.myreflectionthoughts.apipetdetails.core.utils.LogUtility;
import com.myreflectionthoughts.apipetdetails.gateway.dataprovider.repository.PetRepository;
import com.myreflectionthoughts.apipetdetails.gateway.dataprovider.utility.MappingUtility;
import com.myreflectionthoughts.library.contract.IGetAll;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public  class AllPetsProvider extends DataProvider implements IGetAll<PetDTO> {

    private final Logger logger;

    public AllPetsProvider(PetRepository petRepository, MappingUtility mappingUtility){
        this.mappingUtility = mappingUtility;
        this.petRepository = petRepository;
        logger = Logger.getLogger(AllPetsProvider.class.getName());
    }

    public Flux<PetDTO> getAll() {
        return petRepository
                .findAll()
                .delayElements(Duration.ofSeconds(1))
                .doOnComplete(()->LogUtility.loggerUtility.log(logger, "Pet's retrieved successfully...", Level.INFO))
                .map(mappingUtility::mapToPetDTO)
                .doOnNext((petResponse)-> LogUtility.loggerUtility.log(logger, "Pet:- "+ Objects.requireNonNull(petResponse.getId())+" retrieved...", Level.INFO));
    }
}