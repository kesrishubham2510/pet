package com.myreflectionthoughts.apimasterdetails.gateway.dataprovider;

import com.myreflectionthoughts.apimasterdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apimasterdetails.core.exception.MasterNotFoundException;
import com.myreflectionthoughts.apimasterdetails.core.utils.LogUtility;
import com.myreflectionthoughts.apimasterdetails.gateway.dataprovider.repository.MasterRepository;
import com.myreflectionthoughts.apimasterdetails.gateway.dataprovider.utility.MappingUtility;
import com.myreflectionthoughts.library.contract.IGet;
import com.myreflectionthoughts.library.dto.response.MasterDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class MasterDataProvider extends DataProvider implements IGet<MasterDTO> {

    private final Logger logger;

    public MasterDataProvider(MasterRepository masterRepository, MappingUtility mappingUtility) {
        this.masterRepository = masterRepository;
        this.mappingUtility = mappingUtility;
        this.logger = Logger.getLogger(MasterDataProvider.class.getName());
    }

    @Override
    public Mono<MasterDTO> getInfo(Mono<String> masterIdMono) {
        return masterIdMono
                .doOnNext(masterId-> LogUtility.loggerUtility.log(logger, "Initiating info retrieval for master:- "+masterId, Level.INFO))
                .flatMap(masterRepository::findById)
                .doOnNext(retrievedMaster-> LogUtility.loggerUtility.log(logger, "Information retrieved for master:- "+retrievedMaster.getId()+" successfully...",Level.INFO))
                .map(mappingUtility::mapToMasterDTO)
                .switchIfEmpty(Mono.error(new MasterNotFoundException(ServiceConstants.MASTER_NOT_FOUND_EXCEPTION))) ;
    }
}