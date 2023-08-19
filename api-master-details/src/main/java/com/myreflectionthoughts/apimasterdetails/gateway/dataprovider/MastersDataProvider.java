package com.myreflectionthoughts.apimasterdetails.gateway.dataprovider;

import com.myreflectionthoughts.apimasterdetails.gateway.dataprovider.repository.MasterRepository;
import com.myreflectionthoughts.apimasterdetails.gateway.dataprovider.utility.MappingUtility;
import com.myreflectionthoughts.library.contract.IGetAll;
import com.myreflectionthoughts.library.dto.response.MasterDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class MastersDataProvider extends DataProvider implements IGetAll<MasterDTO> {

    public MastersDataProvider(MasterRepository masterRepository, MappingUtility mappingUtility) {
        this.masterRepository = masterRepository;
        this.mappingUtility = mappingUtility;
    }

    @Override
    public Flux<MasterDTO> getAll() {
        return masterRepository.findAll().map(mappingUtility::mapToMasterDTO);
    }
}