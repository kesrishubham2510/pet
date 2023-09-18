package com.myreflectionthoughts.apipetdetails.gateway.dataprovider.utility;

import com.myreflectionthoughts.apipetdetails.core.enums.Category;
import com.myreflectionthoughts.apipetdetails.core.enums.ClinicCardStatus;
import com.myreflectionthoughts.apipetdetails.core.enums.Gender;
import com.myreflectionthoughts.apipetdetails.core.exception.CategoryNotFoundException;
import com.myreflectionthoughts.apipetdetails.core.exception.ClinicCardStatusNotFoundException;
import com.myreflectionthoughts.apipetdetails.core.exception.GenderNotFoundException;
import com.myreflectionthoughts.apipetdetails.core.utils.LogUtility;
import com.myreflectionthoughts.library.dto.logs.LoggerUtility;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
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

        LogUtility.loggerUtility.logEntry(logger, "Validating category:- "+categoryString, Level.FINE);
        List<String> categoryStringList = Arrays.stream(Category.values()).map(Enum::toString).collect(Collectors.toList());

        if (!categoryStringList.contains(categoryString.toUpperCase().trim())){
            LogUtility.loggerUtility.log(logger, "Exception occurred while validating category:- "+categoryString, Level.SEVERE);
            throw new CategoryNotFoundException(exceptionUtility.getCategoryNotFoundExceptionMessage(categoryString));
        }

        LogUtility.loggerUtility.logExit(logger, "Category:- "+categoryString+" validated successfully", Level.FINE);
        return Category.valueOf(categoryString.toUpperCase());
    }

    protected Gender validateGender(String genderString) {

        LogUtility.loggerUtility.logEntry(logger, "Validating gender:- "+genderString, Level.FINE);
        List<String> genderStringList = Arrays.stream(Gender.values()).map(Enum::toString).collect(Collectors.toList());

        if (!genderStringList.contains(genderString.toUpperCase().trim())){
            LogUtility.loggerUtility.log(logger, "Exception occurred while validating gender:- "+genderString, Level.SEVERE);
            throw new GenderNotFoundException(exceptionUtility.getGenderNotFoundExceptionMessage());
        }

        LogUtility.loggerUtility.logExit(logger, "Gender:- "+genderString+" validated successfully", Level.FINE);
        return Gender.valueOf(genderString.toUpperCase());
    }
    protected ClinicCardStatus validateClinicCardStatus(String clinincCardStatusString) {

        LogUtility.loggerUtility.logEntry(logger, "Validating clinicCardStatus:- "+clinincCardStatusString, Level.FINE);
        List<String> clinicCardStatusList = Arrays.stream(ClinicCardStatus.values()).map(Enum::toString).collect(Collectors.toList());

        if (!clinicCardStatusList.contains(clinincCardStatusString.toUpperCase().trim())){
            LogUtility.loggerUtility.log(logger, "Exception occurred while validating clinicCardStatus:- "+clinincCardStatusString, Level.SEVERE);
            throw new ClinicCardStatusNotFoundException(exceptionUtility.getClinicCardStatusNotFoundExceptionMessage());
        }

        LogUtility.loggerUtility.logExit(logger, "ClinicCardStatus:- "+clinincCardStatusString+" validated successfully", Level.FINE);
        return ClinicCardStatus.valueOf(clinincCardStatusString.toUpperCase());
    }
}
