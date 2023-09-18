package com.myreflectionthoughts.apimasterdetails.gateway.dataprovider;

import com.myreflectionthoughts.apimasterdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apimasterdetails.core.exception.MasterNotFoundException;
import com.myreflectionthoughts.apimasterdetails.core.utils.LogUtility;
import com.myreflectionthoughts.apimasterdetails.gateway.dataprovider.repository.MasterRepository;
import com.myreflectionthoughts.apimasterdetails.gateway.dataprovider.utility.MappingUtility;
import com.myreflectionthoughts.library.contract.IUpdate;
import com.myreflectionthoughts.library.dto.request.UpdateMasterDTO;
import com.myreflectionthoughts.library.dto.response.MasterDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class UpdateMasterDataProvider extends DataProvider implements IUpdate<MasterDTO, UpdateMasterDTO> {

    private final Logger logger;

    public UpdateMasterDataProvider(MasterRepository masterRepository, MappingUtility mappingUtility){
        this.masterRepository = masterRepository;
        this.mappingUtility = mappingUtility;
        this.logger = Logger.getLogger(UpdateMasterDataProvider.class.getName());
    }

    @Override
    public Mono<MasterDTO> updateInfo(Mono<UpdateMasterDTO> updateMasterDTOMono) {
        return  updateMasterDTOMono
                .map(this::verifyPayload)
                .doOnNext(validatedRequestPayload-> LogUtility.loggerUtility.log(logger, "Checking masterId:- "+validatedRequestPayload.getId()+" in database...", Level.INFO))
                .filterWhen(updateMasterDTO -> masterRepository.existsById(updateMasterDTO.getId()))
                .map(mappingUtility::mapToMaster)
                .doOnNext(updatedMaster-> LogUtility.loggerUtility.log(logger, "Latest changes applied to master:- "+updatedMaster.getId()+" information", Level.INFO))
                .flatMap(masterRepository::save)
                .doOnNext(savedUpdatedMaster-> LogUtility.loggerUtility.log(logger,"Master:- "+savedUpdatedMaster.getId()+" persisted with latest information", Level.INFO))
                .map(mappingUtility::mapToMasterDTO)
                .switchIfEmpty(Mono.error(new MasterNotFoundException(ServiceConstants.MASTER_NOT_FOUND_EXCEPTION))) ;
    }

}