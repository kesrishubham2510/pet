package com.myreflectionthoughts.apimasterdetails.gateway.dataprovider;

import com.myreflectionthoughts.apimasterdetails.core.utils.LogUtility;
import com.myreflectionthoughts.apimasterdetails.gateway.dataprovider.repository.MasterRepository;
import com.myreflectionthoughts.apimasterdetails.gateway.dataprovider.utility.MappingUtility;
import com.myreflectionthoughts.library.contract.IGetAll;
import com.myreflectionthoughts.library.dto.response.MasterDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class MastersDataProvider extends DataProvider implements IGetAll<MasterDTO> {

    private final Logger logger;

    public MastersDataProvider(MasterRepository masterRepository, MappingUtility mappingUtility) {
        this.masterRepository = masterRepository;
        this.mappingUtility = mappingUtility;
        this.logger = Logger.getLogger(MastersDataProvider.class.getName());
    }

    @Override
    public Flux<MasterDTO> getAll() {

        return masterRepository
                .findAll()
                .map(mappingUtility::mapToMasterDTO)
                .doOnComplete(()-> LogUtility.loggerUtility.log(logger, "Masters retrieved successfully...", Level.INFO))
                .doOnNext(mappedMaster-> LogUtility.loggerUtility.log(logger, "Retrieved Master:- "+mappedMaster.getId(), Level.FINE));

    }
}