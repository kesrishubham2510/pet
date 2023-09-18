package com.myreflectionthoughts.apimasterdetails.gateway.dataprovider;

import com.myreflectionthoughts.apimasterdetails.core.utils.LogUtility;
import com.myreflectionthoughts.apimasterdetails.gateway.dataprovider.repository.MasterRepository;
import com.myreflectionthoughts.apimasterdetails.gateway.dataprovider.utility.MappingUtility;
import com.myreflectionthoughts.library.contract.IAdd;
import com.myreflectionthoughts.library.dto.request.AddMasterDTO;
import com.myreflectionthoughts.library.dto.response.MasterDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class AddMasterDataProvider extends DataProvider implements IAdd<AddMasterDTO, MasterDTO>{

    private final Logger logger;

    public AddMasterDataProvider(MasterRepository masterRepository, MappingUtility mappingUtility){
        this.mappingUtility = mappingUtility;
        this.masterRepository = masterRepository;
        logger = Logger.getLogger(AddMasterDataProvider.class.getName());
    }

    @Override
    public Mono<MasterDTO> add(Mono<AddMasterDTO> requestPayload) {
        return requestPayload
                .doOnNext(payload-> LogUtility.loggerUtility.log(logger, "Initiating registration process for master:- "+payload.getName(), Level.INFO))
                .map(this::verifyPayload)
                .map(mappingUtility::mapToMaster)
                .doOnNext(master-> LogUtility.loggerUtility.log(logger, "Initiating master's data persistence...", Level.INFO))
                .flatMap(masterRepository::save)
                .doOnNext(savedMaster-> LogUtility.loggerUtility.log(logger, "master's data persistence completed successfully...", Level.INFO))
                .map(mappingUtility::mapToMasterDTO);
    }
}