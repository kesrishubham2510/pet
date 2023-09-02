package com.myreflectionthoughts.apimasterdetails.gateway.dataprovider;

import com.myreflectionthoughts.apimasterdetails.gateway.dataprovider.repository.MasterRepository;
import com.myreflectionthoughts.apimasterdetails.gateway.dataprovider.utility.MappingUtility;
import com.myreflectionthoughts.library.contract.IValidator;

public class DataProvider {
    protected MappingUtility mappingUtility;
    protected MasterRepository masterRepository;

    protected <T extends IValidator> T verifyPayload(T payload){
        payload.validate();
        return payload;
    }
}