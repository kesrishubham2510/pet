package com.myreflectionthoughts.apimasterdetails.service;

import com.myreflectionthoughts.apimasterdetails.constant.ServiceConstants;
import com.myreflectionthoughts.apimasterdetails.exception.MasterNotFoundException;
import com.myreflectionthoughts.apimasterdetails.repository.MasterRepository;
import com.myreflectionthoughts.apimasterdetails.utility.MappingUtility;
import com.myreflectionthoughts.library.contract.IAdd;
import com.myreflectionthoughts.library.contract.IGet;
import com.myreflectionthoughts.library.contract.IUpdate;
import com.myreflectionthoughts.library.dto.request.AddMasterDTO;
import com.myreflectionthoughts.library.dto.request.UpdateMasterDTO;
import com.myreflectionthoughts.library.dto.response.MasterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MasterService implements
        IAdd<AddMasterDTO, MasterDTO>,
        IGet<MasterDTO>,
        IUpdate<MasterDTO, UpdateMasterDTO> {

    @Autowired
    private MasterRepository masterRepository;

    @Autowired
    private MappingUtility mappingUtility;

    @Override
    public Mono<MasterDTO> add(Mono<AddMasterDTO> requestPayload) {
        return requestPayload
                .map(mappingUtility::mapToMaster)
                .flatMap(masterRepository::save)
                .map(mappingUtility::mapToMasterDTO);
    }

    @Override
    public Mono<MasterDTO> getInfo(Mono<String> masterId) {
        return masterId
                 .flatMap(masterRepository::findById)
                 .map(mappingUtility::mapToMasterDTO)
                 .switchIfEmpty(Mono.error(new MasterNotFoundException(ServiceConstants.MASTER_NOT_FOUND_EXCEPTION))) ;
    }

    @Override
    public Flux<MasterDTO> getAll() {
        return masterRepository
                .findAll()
                .map(mappingUtility::mapToMasterDTO);
    }

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
