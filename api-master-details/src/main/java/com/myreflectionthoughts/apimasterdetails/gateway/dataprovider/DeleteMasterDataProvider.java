package com.myreflectionthoughts.apimasterdetails.gateway.dataprovider;

import com.myreflectionthoughts.apimasterdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apimasterdetails.core.entity.Master;
import com.myreflectionthoughts.apimasterdetails.core.exception.MasterNotFoundException;
import com.myreflectionthoughts.apimasterdetails.core.utils.LogUtility;
import com.myreflectionthoughts.apimasterdetails.gateway.dataprovider.repository.MasterRepository;
import com.myreflectionthoughts.apimasterdetails.gateway.dataprovider.utility.MappingUtility;
import com.myreflectionthoughts.library.contract.IDelete;
import com.myreflectionthoughts.library.dto.response.DeleteMasterDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class DeleteMasterDataProvider extends DataProvider implements IDelete<DeleteMasterDTO> {

    private final Logger logger;

    public DeleteMasterDataProvider(MasterRepository masterRepository, MappingUtility mappingUtility) {
        this.masterRepository = masterRepository;
        this.mappingUtility = mappingUtility;
        this.logger = Logger.getLogger(DeleteMasterDataProvider.class.getName());
    }

    @Override
    public Mono<DeleteMasterDTO> delete(Mono<String> masterIdMono) {
        return masterIdMono
                .doOnNext(masterId -> LogUtility.loggerUtility.log(logger, "Checking for masterId:- "+masterId+" in database", Level.INFO))
                .flatMap(masterRepository::findById)
                .doOnNext(master-> LogUtility.loggerUtility.log(logger, "Initiating master deletion...", Level.INFO))
                .flatMap(this::handleDeletion)
                .doOnNext(deleteMasterDTO -> LogUtility.loggerUtility.log(logger, "Master's field markedForDelete updated to true", Level.FINE))
                .switchIfEmpty(Mono.error(new MasterNotFoundException(ServiceConstants.MASTER_NOT_FOUND_EXCEPTION)));
    }

    private Mono<DeleteMasterDTO> handleDeletion(Master master) {
        master.setMarkForDelete(true);
        // since reactive pipeline runs only when subscribed
        return masterRepository
                .save(master)
                .doOnNext(savedMaster-> LogUtility.loggerUtility.log(logger, "Master:- "+master.getId()+" marked for deletion and persisted successfully", Level.INFO))
                .map(updatedMaster -> {
                    DeleteMasterDTO deleteMasterDTO = new DeleteMasterDTO();
                    deleteMasterDTO.setId(master.getId());
                    deleteMasterDTO.setMessage(String.format(ServiceConstants.MASTER_DELETION_MESSAGE_TEMPLATE, master.getId()));
                    return deleteMasterDTO;
                });
    }
}