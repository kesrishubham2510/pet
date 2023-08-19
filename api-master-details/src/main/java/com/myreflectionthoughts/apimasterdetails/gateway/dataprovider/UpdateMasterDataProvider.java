package com.myreflectionthoughts.apimasterdetails.gateway.dataprovider;

import com.myreflectionthoughts.apimasterdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apimasterdetails.core.exception.MasterNotFoundException;
import com.myreflectionthoughts.library.contract.IUpdate;
import com.myreflectionthoughts.library.dto.request.UpdateMasterDTO;
import com.myreflectionthoughts.library.dto.response.MasterDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UpdateMasterDataProvider extends DataProvider implements IUpdate<MasterDTO, UpdateMasterDTO> {
    @Override
    public Mono<MasterDTO> updateInfo(Mono<UpdateMasterDTO> updateMasterDTOMono) {
        return  updateMasterDTOMono
                .filterWhen(updateMasterDTO -> masterRepository.existsById(updateMasterDTO.getId()))
                .map(mappingUtility::mapToMaster)
                .flatMap(masterRepository::save)
                .map(mappingUtility::mapToMasterDTO)
                .switchIfEmpty(Mono.error(new MasterNotFoundException(ServiceConstants.MASTER_NOT_FOUND_EXCEPTION))) ;
    }

}