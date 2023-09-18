package com.myreflectionthoughts.apipetdetails.gateway.dataprovider;

import com.myreflectionthoughts.apipetdetails.core.utils.LogUtility;
import com.myreflectionthoughts.apipetdetails.gateway.dataprovider.repository.PetRepository;
import com.myreflectionthoughts.apipetdetails.gateway.dataprovider.utility.MappingUtility;
import com.myreflectionthoughts.library.contract.IGetByCommonAttribute;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.logging.Level;
import java.util.logging.Logger;


@Service
public class GetPetsOfMasterDataProvider extends DataProvider implements IGetByCommonAttribute<String, PetDTO> {

    private final Logger logger;

    public GetPetsOfMasterDataProvider(MappingUtility mappingUtility, PetRepository petRepository) {
        this.mappingUtility = mappingUtility;
        this.petRepository = petRepository;
        this.logger = Logger.getLogger(GetPetsOfMasterDataProvider.class.getName());
    }

    @Override
    public Flux<PetDTO> retrieveByAttribute(Mono<String> attribute) {
        return attribute
                .doOnNext(masterId-> LogUtility.loggerUtility.log(logger, "Initiating pet retrieval for master:- "+masterId, Level.INFO))
                .flatMapMany(petRepository::findAllByMaster)
                .doOnComplete(()-> LogUtility.loggerUtility.log(logger, "Pets retrieval completed successfully", Level.INFO))
                .map(mappingUtility::setClinicCardStatus);
    }
}