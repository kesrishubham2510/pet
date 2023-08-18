package com.myreflectionthoughts.apipetdetails.gateway.dataprovider;

import com.myreflectionthoughts.apipetdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apipetdetails.core.exception.PetNotFoundException;
import com.myreflectionthoughts.apipetdetails.gateway.dataprovider.repository.PetRepository;
import com.myreflectionthoughts.apipetdetails.gateway.dataprovider.utility.MappingUtility;
import com.myreflectionthoughts.library.contract.IGet;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public  class PetDataProvider extends DataProvider implements IGet<PetDTO> {

    public PetDataProvider(PetRepository petRepository, MappingUtility mappingUtility){
        this.mappingUtility = mappingUtility;
        this.petRepository = petRepository;
    }

    @Override
    public Mono<PetDTO> getInfo(Mono<String> petIdMono) {
        return petIdMono
                .flatMap(petRepository::findById)
                .map(mappingUtility::mapToPetDTO)
                .switchIfEmpty(Mono.error(new PetNotFoundException(ServiceConstants.PET_NOT_FOUND_EXCEPTION)));
    }
}