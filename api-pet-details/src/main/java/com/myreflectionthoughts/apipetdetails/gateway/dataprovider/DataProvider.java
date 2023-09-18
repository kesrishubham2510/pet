package com.myreflectionthoughts.apipetdetails.gateway.dataprovider;

import com.myreflectionthoughts.apipetdetails.core.utils.LogUtility;
import com.myreflectionthoughts.apipetdetails.gateway.dataprovider.repository.PetRepository;
import com.myreflectionthoughts.apipetdetails.gateway.dataprovider.utility.MappingUtility;
import com.myreflectionthoughts.library.contract.IValidator;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DataProvider {
    protected PetRepository petRepository;
    protected MappingUtility mappingUtility;
    private static final Logger logger;

    static {
        logger = Logger.getLogger(DataProvider.class.getName());
    }


    protected <T extends IValidator> T validatePayload(T payload){
        LogUtility.loggerUtility.logEntry(logger, "Initiating request payload validation...", Level.FINE);
        payload.validate();
        LogUtility.loggerUtility.logExit(logger, "Request payload validated successfully...", Level.FINE);
        return payload;
    }
}
