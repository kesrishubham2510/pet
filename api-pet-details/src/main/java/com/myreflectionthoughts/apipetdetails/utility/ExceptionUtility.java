package com.myreflectionthoughts.apipetdetails.utility;

import com.myreflectionthoughts.apipetdetails.constant.ServiceConstants;
import com.myreflectionthoughts.apipetdetails.enums.Category;
import com.myreflectionthoughts.apipetdetails.enums.ClinicCardStatus;
import com.myreflectionthoughts.apipetdetails.enums.Gender;

import javax.annotation.PostConstruct;
import java.util.Arrays;


public class ExceptionUtility {

    private final StringBuilder stringBuilder;
    private ServiceConstants serviceConstants;

    public ExceptionUtility() {
        stringBuilder = new StringBuilder();
    }

    @PostConstruct
    public void setUp() {
        serviceConstants = new ServiceConstants();
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
