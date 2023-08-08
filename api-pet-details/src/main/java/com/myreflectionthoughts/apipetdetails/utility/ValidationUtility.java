package com.myreflectionthoughts.apipetdetails.utility;

import com.myreflectionthoughts.apipetdetails.enums.Category;
import com.myreflectionthoughts.apipetdetails.enums.Gender;
import com.myreflectionthoughts.apipetdetails.exception.CategoryNotFoundException;
import com.myreflectionthoughts.apipetdetails.exception.GenderNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
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
}
