package com.myreflectionthoughts.apimasterdetails.gateway.dataprovider;

import com.myreflectionthoughts.apimasterdetails.gateway.dataprovider.repository.MasterRepository;
import com.myreflectionthoughts.apimasterdetails.gateway.dataprovider.utility.MappingUtility;
import com.myreflectionthoughts.library.contract.IAdd;
import com.myreflectionthoughts.library.dto.request.AddMasterDTO;
import com.myreflectionthoughts.library.dto.response.MasterDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AddMasterDataProvider extends DataProvider implements IAdd<AddMasterDTO, MasterDTO>{

    public AddMasterDataProvider(MasterRepository masterRepository, MappingUtility mappingUtility){
        this.mappingUtility = mappingUtility;
        this.masterRepository = masterRepository;
    }
    @Override
    public Mono<MasterDTO> add(Mono<AddMasterDTO> requestPayload) {
        return requestPayload
                .map(mappingUtility::mapToMaster)
                .flatMap(masterRepository::save)
                .map(mappingUtility::mapToMasterDTO);
    }
}