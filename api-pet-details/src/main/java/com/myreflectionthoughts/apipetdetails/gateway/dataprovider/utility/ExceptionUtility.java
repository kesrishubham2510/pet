package com.myreflectionthoughts.apipetdetails.gateway.dataprovider.utility;

import com.myreflectionthoughts.apipetdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apipetdetails.core.enums.Category;
import com.myreflectionthoughts.apipetdetails.core.enums.ClinicCardStatus;
import com.myreflectionthoughts.apipetdetails.core.enums.Gender;
import com.myreflectionthoughts.library.dto.logs.LoggerUtility;

import java.util.Arrays;
import java.util.logging.Logger;


public class ExceptionUtility extends Utility{

    private final StringBuilder stringBuilder;
    private final ServiceConstants serviceConstants;
    private final Logger logger;


    public ExceptionUtility(LoggerUtility loggerUtility) {
        super(loggerUtility);
        stringBuilder = new StringBuilder();
        serviceConstants = new ServiceConstants();
        logger = Logger.getLogger(ExceptionUtility.class.getName());
    }

    public String getCategoryNotFoundExceptionMessage(String animalCategory) {
        cleanUp();
        Arrays.stream(Category.values()).forEach(categoryValue -> stringBuilder.append(categoryValue).append(", "));
        stringBuilder.setLength(stringBuilder.length() - 2);
        return String.format(serviceConstants.getCATEGORY_NOT_FOUND_EXCEPTION(), animalCategory, stringBuilder);
    }

    public String getGenderNotFoundExceptionMessage() {
        cleanUp();
        Arrays.stream(Gender.values()).forEach(genderValue -> stringBuilder.append(genderValue).append(", "));
        stringBuilder.setLength(stringBuilder.length() - 2);
        return String.format(serviceConstants.getGENDER_NOT_FOUND_EXCEPTION(), stringBuilder);
    }

    public String getClinicCardStatusNotFoundExceptionMessage() {
        cleanUp();
        Arrays.stream(ClinicCardStatus.values()).forEach(clinicCardStatusValue -> stringBuilder.append(clinicCardStatusValue).append(", "));
        stringBuilder.setLength(stringBuilder.length() - 2);
        return String.format(serviceConstants.getCLINIC_CARD_STATUS_NOT_FOUND_EXCEPTION(), stringBuilder);
    }

    private void cleanUp() {
        stringBuilder.setLength(0);
    }
}
