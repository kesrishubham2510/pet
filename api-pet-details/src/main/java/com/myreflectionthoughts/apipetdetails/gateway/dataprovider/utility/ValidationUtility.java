package com.myreflectionthoughts.apipetdetails.gateway.dataprovider.utility;

import com.myreflectionthoughts.apipetdetails.core.enums.Category;
import com.myreflectionthoughts.apipetdetails.core.enums.ClinicCardStatus;
import com.myreflectionthoughts.apipetdetails.core.enums.Gender;
import com.myreflectionthoughts.apipetdetails.core.exception.CategoryNotFoundException;
import com.myreflectionthoughts.apipetdetails.core.exception.ClinicCardStatusNotFoundException;
import com.myreflectionthoughts.apipetdetails.core.exception.GenderNotFoundException;
import com.myreflectionthoughts.apipetdetails.core.utils.LogUtility;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ValidationUtility {

    private final ExceptionUtility exceptionUtility;
    private final Logger logger;

    public ValidationUtility(ExceptionUtility exceptionUtility) {
        this.exceptionUtility = exceptionUtility;
        this.logger = Logger.getLogger(ValidationUtility.class.getName());
    }

    protected Category validateCategory(String categoryString) {

        LogUtility.loggerUtility.logEntry(logger, "Validating category:- "+categoryString);
        List<String> categoryStringList = Arrays.stream(Category.values()).map(Enum::toString).collect(Collectors.toList());

        if (!categoryStringList.contains(categoryString.toUpperCase().trim()))
            throw new CategoryNotFoundException(exceptionUtility.getCategoryNotFoundExceptionMessage(categoryString));

        LogUtility.loggerUtility.logExit(logger, "Category:- "+categoryString+" validated successfully");
        return Category.valueOf(categoryString.toUpperCase());
    }

    protected Gender validateGender(String genderString) {

        LogUtility.loggerUtility.logEntry(logger, "Validating gender:- "+genderString);
        List<String> genderStringList = Arrays.stream(Gender.values()).map(Enum::toString).collect(Collectors.toList());
        if (!genderStringList.contains(genderString.toUpperCase().trim()))
            throw new GenderNotFoundException(exceptionUtility.getGenderNotFoundExceptionMessage());

        LogUtility.loggerUtility.logExit(logger, "Gender:- "+genderString+" validated successfully");
        return Gender.valueOf(genderString.toUpperCase());
    }
    protected ClinicCardStatus validateClinicCardStatus(String clinincCardStatusString) {

        LogUtility.loggerUtility.logEntry(logger, "Validating clinicCardStatus:- "+clinincCardStatusString);
        List<String> clinicCardStatusList = Arrays.stream(ClinicCardStatus.values()).map(Enum::toString).collect(Collectors.toList());
        if (!clinicCardStatusList.contains(clinincCardStatusString.toUpperCase().trim()))
            throw new ClinicCardStatusNotFoundException(exceptionUtility.getClinicCardStatusNotFoundExceptionMessage());

        LogUtility.loggerUtility.logExit(logger, "ClinicCardStatus:- "+clinincCardStatusString+" validated successfully");
        return ClinicCardStatus.valueOf(clinincCardStatusString.toUpperCase());
    }
}
