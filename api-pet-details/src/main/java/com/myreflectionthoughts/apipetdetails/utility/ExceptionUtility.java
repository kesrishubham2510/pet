package com.myreflectionthoughts.apipetdetails.utility;

import com.myreflectionthoughts.apipetdetails.enums.Category;
import com.myreflectionthoughts.apipetdetails.enums.Gender;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class ExceptionUtility {

    private final StringBuilder stringBuilder;
    private String CATEGORY_NOT_FOUND_EXCEPTION = "We currently do not provide service for %s in our clinic, please select from the available category options [ %s ]";
    private String GENDER_NOT_FOUND_EXCEPTION = "Please enter a correct gender, please select from the available gender options [ %s ]";

    public ExceptionUtility() {
        stringBuilder = new StringBuilder();
    }

    public String getCategoryNotFoundExceptionMessage(String animalCategory) {
        cleanUp();
        Arrays.stream(Category.values()).forEach(categoryValue -> stringBuilder.append(categoryValue).append(", "));
        stringBuilder.setLength(stringBuilder.length() - 2);
        return String.format(CATEGORY_NOT_FOUND_EXCEPTION, animalCategory, stringBuilder.toString());
    }

    public String getGenderNotFoundExceptionMessage() {
        cleanUp();
        Arrays.stream(Gender.values()).forEach(genderValue -> stringBuilder.append(genderValue).append(", "));
        stringBuilder.setLength(stringBuilder.length() - 2);
        return String.format(GENDER_NOT_FOUND_EXCEPTION, stringBuilder.toString());
    }

    public void cleanUp() {
        stringBuilder.setLength(0);
    }
}
