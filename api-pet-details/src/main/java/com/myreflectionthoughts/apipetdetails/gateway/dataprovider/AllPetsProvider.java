package com.myreflectionthoughts.apipetdetails.gateway.dataprovider;

import com.myreflectionthoughts.apipetdetails.gateway.dataprovider.repository.PetRepository;
import com.myreflectionthoughts.apipetdetails.gateway.dataprovider.utility.MappingUtility;
import com.myreflectionthoughts.library.contract.IGetAll;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Service
public  class AllPetsProvider extends DataProvider implements IGetAll<PetDTO> {

    public AllPetsProvider(PetRepository petRepository, MappingUtility mappingUtility){
        this.mappingUtility = mappingUtility;
        this.petRepository = petRepository;
    }
    public Flux<PetDTO> getAll() {
        return petRepository
                .findAll()
                .delayElements(Duration.ofSeconds(2))
                .map(mappingUtility::mapToPetDTO);
    }
}