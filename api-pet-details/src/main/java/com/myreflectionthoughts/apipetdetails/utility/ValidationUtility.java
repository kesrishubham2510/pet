package com.myreflectionthoughts.apipetdetails.utility;

import com.myreflectionthoughts.apipetdetails.enums.Category;
import com.myreflectionthoughts.apipetdetails.enums.ClinicCardStatus;
import com.myreflectionthoughts.apipetdetails.enums.Gender;
import com.myreflectionthoughts.apipetdetails.exception.CategoryNotFoundException;
import com.myreflectionthoughts.apipetdetails.exception.ClinicCardStatusNotFoundException;
import com.myreflectionthoughts.apipetdetails.exception.GenderNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class ValidationUtility {

    @Autowired
    private ExceptionUtility exceptionUtility;

    protected Category validateCategory(String categoryString) {

        List<String> categoryStringList = Arrays.stream(Category.values()).map(Enum::toString).collect(Collectors.toList());
        if (!categoryStringList.contains(categoryString.toUpperCase().trim()))
            throw new CategoryNotFoundException(exceptionUtility.getCategoryNotFoundExceptionMessage(categoryString));

        return Category.valueOf(categoryString.toUpperCase());
    }

    protected Gender validateGender(String genderString) {

        List<String> genderStringList = Arrays.stream(Gender.values()).map(Enum::toString).collect(Collectors.toList());
        if (!genderStringList.contains(genderString.toUpperCase().trim()))
            throw new GenderNotFoundException(exceptionUtility.getGenderNotFoundExceptionMessage());

        return Gender.valueOf(genderString.toUpperCase());
    }
    protected ClinicCardStatus validateClinicCardStatus(String clinincCardStatusString) {

        List<String> clinicCardStatusList = Arrays.stream(ClinicCardStatus.values()).map(Enum::toString).collect(Collectors.toList());
        if (!clinicCardStatusList.contains(clinincCardStatusString.toUpperCase().trim()))
            throw new ClinicCardStatusNotFoundException(exceptionUtility.getClinicCardStatusNotFoundExceptionMessage());

        return ClinicCardStatus.valueOf(clinincCardStatusString.toUpperCase());
    }
}
