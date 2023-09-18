package com.myreflectionthoughts.apimasterdetails.gateway.dataprovider;

import com.myreflectionthoughts.apimasterdetails.core.utils.LogUtility;
import com.myreflectionthoughts.apimasterdetails.gateway.dataprovider.repository.MasterRepository;
import com.myreflectionthoughts.apimasterdetails.gateway.dataprovider.utility.MappingUtility;
import com.myreflectionthoughts.library.contract.IValidator;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DataProvider {
    protected MappingUtility mappingUtility;
    protected MasterRepository masterRepository;
    private final Logger logger;

    public DataProvider(){
        logger = Logger.getLogger(DataProvider.class.getName());
    }

    protected <T extends IValidator> T verifyPayload(T payload){
        LogUtility.loggerUtility.logEntry(logger, "Initiating request payload validation...", Level.FINE);
        payload.validate();
        LogUtility.loggerUtility.logExit(logger, "Request payload validated successfully...", Level.FINE);
        return payload;
    }
}