package com.myreflectionthoughts.apipetdetails.utility;

import com.myreflectionthoughts.apipetdetails.constant.ServiceConstants;
import com.myreflectionthoughts.apipetdetails.data.TestDataGenerator;
import com.myreflectionthoughts.apipetdetails.enums.Category;
import com.myreflectionthoughts.apipetdetails.enums.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ExceptionUtilityTest {

    private final TestDataGenerator testDataGenerator;
    @InjectMocks
    private ExceptionUtility exceptionUtility;
    @Mock
    private ServiceConstants serviceConstants;
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
        when(serviceConstants.getCATEGORY_NOT_FOUND_EXCEPTION()).thenReturn(testDataGenerator.getGetCategoryNotFoundExceptionStringTemplate());
        String animalCategory = ServiceConstants.VALID_CATEGORY_STRING + "jg";
        String expectedString = String.format(testDataGenerator.getGetCategoryNotFoundExceptionStringTemplate(), animalCategory, prepareCategoryErrMessageSubstitute());
        String actualString = exceptionUtility.getCategoryNotFoundExceptionMessage(animalCategory);
        assertEquals(expectedString, actualString);
    }

    @Test
    public void testGetGenderNotFoundExceptionMessage() {
        when(serviceConstants.getGENDER_NOT_FOUND_EXCEPTION()).thenReturn(testDataGenerator.getGetGenderNotFoundExceptionStringTemplate());
        String expectedString = String.format(testDataGenerator.getGetGenderNotFoundExceptionStringTemplate(), prepareGenderErrMessageSubstitute());
        String actualString = exceptionUtility.getGenderNotFoundExceptionMessage();
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
}
