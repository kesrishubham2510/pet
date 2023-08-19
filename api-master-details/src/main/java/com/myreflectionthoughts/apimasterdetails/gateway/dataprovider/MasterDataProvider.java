package com.myreflectionthoughts.apimasterdetails.gateway.dataprovider;

import com.myreflectionthoughts.apimasterdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apimasterdetails.core.exception.MasterNotFoundException;
import com.myreflectionthoughts.apimasterdetails.gateway.dataprovider.repository.MasterRepository;
import com.myreflectionthoughts.apimasterdetails.gateway.dataprovider.utility.MappingUtility;
import com.myreflectionthoughts.library.contract.IGet;
import com.myreflectionthoughts.library.dto.response.MasterDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class MasterDataProvider extends DataProvider implements IGet<MasterDTO> {

    public MasterDataProvider(MasterRepository masterRepository, MappingUtility mappingUtility) {
        this.masterRepository = masterRepository;
        this.mappingUtility = mappingUtility;
    }

    @Override
    public Mono<MasterDTO> getInfo(Mono<String> masterId) {
        return masterId
                .flatMap(masterRepository::findById)
                .map(mappingUtility::mapToMasterDTO)
                .switchIfEmpty(Mono.error(new MasterNotFoundException(ServiceConstants.MASTER_NOT_FOUND_EXCEPTION))) ;
    }
}