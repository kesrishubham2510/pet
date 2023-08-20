package com.myreflectionthoughts.apimasterdetails.gateway.dataprovider;

import com.myreflectionthoughts.apimasterdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apimasterdetails.core.entity.Master;
import com.myreflectionthoughts.apimasterdetails.core.exception.MasterNotFoundException;
import com.myreflectionthoughts.apimasterdetails.gateway.dataprovider.repository.MasterRepository;
import com.myreflectionthoughts.apimasterdetails.gateway.dataprovider.utility.MappingUtility;
import com.myreflectionthoughts.library.contract.IDelete;
import com.myreflectionthoughts.library.dto.response.DeleteMasterDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DeleteMasterDataProvider extends DataProvider implements IDelete<DeleteMasterDTO> {

    public DeleteMasterDataProvider(MasterRepository masterRepository, MappingUtility mappingUtility) {
      this.masterRepository = masterRepository;
      this.mappingUtility = mappingUtility;
    }

    @Override
    public Mono<DeleteMasterDTO> delete(Mono<String> masterIdMono) {
        return masterIdMono
                .flatMap(masterRepository::findById)
                .map(this::handleDeletion)
                .switchIfEmpty(Mono.error(new MasterNotFoundException(ServiceConstants.MASTER_NOT_FOUND_EXCEPTION))) ;
    }

    private DeleteMasterDTO handleDeletion(Master master){
        master.setMarkForDelete(true);
        // since reactive pipeline runs only when subscribed
        masterRepository.save(master).subscribe();
        DeleteMasterDTO deleteMasterDTO = new DeleteMasterDTO();
        deleteMasterDTO.setId(master.getId());
        deleteMasterDTO.setMessage(String.format(ServiceConstants.MASTER_DELETION_MESSAGE_TEMPLATE,master.getId()));
        return deleteMasterDTO;
    }
}