package com.myreflectionthoughts.apipetdetails.gateway.dataprovider;

import com.myreflectionthoughts.apipetdetails.gateway.dataprovider.repository.PetRepository;
import com.myreflectionthoughts.apipetdetails.gateway.dataprovider.utility.MappingUtility;
import com.myreflectionthoughts.library.contract.IGetByCommonAttribute;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class GetPetsOfMasterDataProvider extends DataProvider implements IGetByCommonAttribute<String, PetDTO> {

    public GetPetsOfMasterDataProvider(MappingUtility mappingUtility, PetRepository petRepository) {
        this.mappingUtility = mappingUtility;
        this.petRepository = petRepository;
    }

    @Override
    public Flux<PetDTO> retrieveByAttribute(Mono<String> attribute) {
        return attribute.flatMapMany(petRepository::findAllByMasterId);
    }
}