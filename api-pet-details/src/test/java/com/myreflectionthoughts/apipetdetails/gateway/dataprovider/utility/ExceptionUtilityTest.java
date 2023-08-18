/**
 *  This is a Behaviour Driven Test to check the functionality
    of the ExceptionUtility
*/

package com.myreflectionthoughts.apipetdetails.gateway.dataprovider.utility;

import com.myreflectionthoughts.apipetdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apipetdetails.configuration.TestDataGenerator;
import com.myreflectionthoughts.apipetdetails.core.enums.Category;
import com.myreflectionthoughts.apipetdetails.core.enums.ClinicCardStatus;
import com.myreflectionthoughts.apipetdetails.core.enums.Gender;
import com.myreflectionthoughts.apipetdetails.gateway.dataprovider.utility.ExceptionUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ExceptionUtilityTest {

    @Autowired
    private ExceptionUtility exceptionUtility;
    private final TestDataGenerator testDataGenerator;
    private StringBuilder stringBuilder;

    public ExceptionUtilityTest() {
        testDataGenerator = new TestDataGenerator();
        stringBuilder = new StringBuilder();
    }

    @BeforeEach
    public void resetStringBuilder() {
        stringBuilder.setLength(0);
    }

    @Test
    public void testGetCategoryNotFoundExceptionMessage() {

        String animalCategory = ServiceConstants.VALID_CATEGORY_STRING + "jg";
        String expectedString = String.format(testDataGenerator.getGetCategoryNotFoundExceptionStringTemplate(), animalCategory, prepareCategoryErrMessageSubstitute());
        String actualString = exceptionUtility.getCategoryNotFoundExceptionMessage(animalCategory);
        assertEquals(expectedString, actualString);
    }

    @Test
    public void testGetGenderNotFoundExceptionMessage() {
        String expectedString = String.format(testDataGenerator.getGetGenderNotFoundExceptionStringTemplate(), prepareGenderErrMessageSubstitute());
        String actualString = exceptionUtility.getGenderNotFoundExceptionMessage();
        assertEquals(expectedString, actualString);
    }

    @Test
    public void testGetClinicCardStatusNotFoundExceptionMessage() {
        String expectedString = String.format(testDataGenerator.getClinicCardStatusNotFoundExceptionStringTemplate(), prepareClinicCardStatusErrMessageSubstitute());
        String actualString = exceptionUtility.getClinicCardStatusNotFoundExceptionMessage();
        assertEquals(expectedString, actualString);
    }


    private String prepareCategoryErrMessageSubstitute() {
        Arrays.stream(Category.values()).forEach(categoryValue -> stringBuilder.append(categoryValue).append(", "));
        stringBuilder.setLength(stringBuilder.length() - 2);
        return stringBuilder.toString();
    }

    private String prepareGenderErrMessageSubstitute() {
        Arrays.stream(Gender.values()).forEach(genderValue -> stringBuilder.append(genderValue).append(", "));
        stringBuilder.setLength(stringBuilder.length() - 2);
        return stringBuilder.toString();
    }
    private String prepareClinicCardStatusErrMessageSubstitute() {
        Arrays.stream(ClinicCardStatus.values()).forEach(clinicCardStatusValue -> stringBuilder.append(clinicCardStatusValue).append(", "));
        stringBuilder.setLength(stringBuilder.length() - 2);
        return stringBuilder.toString();
    }
}
