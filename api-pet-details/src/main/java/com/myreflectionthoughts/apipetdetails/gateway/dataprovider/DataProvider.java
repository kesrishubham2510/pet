package com.myreflectionthoughts.apipetdetails.gateway.dataprovider;

import com.myreflectionthoughts.apipetdetails.gateway.dataprovider.repository.PetRepository;
import com.myreflectionthoughts.apipetdetails.gateway.dataprovider.utility.MappingUtility;
import com.myreflectionthoughts.library.contract.IValidator;

public class DataProvider {
    protected PetRepository petRepository;
    protected MappingUtility mappingUtility;

    protected <T extends IValidator> T validatePayload(T payload){
        payload.validate();
        return payload;
    }
}
