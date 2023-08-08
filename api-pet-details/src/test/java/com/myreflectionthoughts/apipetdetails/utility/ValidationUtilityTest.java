package com.myreflectionthoughts.apipetdetails.utility;

import com.myreflectionthoughts.apipetdetails.constant.ServiceConstants;
import com.myreflectionthoughts.apipetdetails.enums.Category;
import com.myreflectionthoughts.apipetdetails.enums.Gender;
import com.myreflectionthoughts.apipetdetails.exception.CategoryNotFoundException;
import com.myreflectionthoughts.apipetdetails.exception.GenderNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ValidationUtilityTest {

    @InjectMocks
    private ValidationUtility validationUtility;

    @Mock
    private ExceptionUtility exceptionUtility;

    @Test
    void testValidateCategory() {
        String validCategory = ServiceConstants.VALID_CATEGORY_STRING;
        Category expectedCategory = Category.valueOf(validCategory);

        Category actualParsedCategory = validationUtility.validateCategory(validCategory);
        assertEquals(expectedCategory, actualParsedCategory);
    }

    @Test
    void testValidateCategoryThrowsCategoryNotFoundException() {
        String invalidCategory = ServiceConstants.VALID_CATEGORY_STRING + "fnkj";
        when(exceptionUtility.getCategoryNotFoundExceptionMessage(anyString())).thenReturn("");
        assertThrows(CategoryNotFoundException.class, () -> validationUtility.validateCategory(invalidCategory));
    }

    @Test
    void testValidateGender() {
        String validGender = ServiceConstants.VALID_GENDER_CATEGORY;
        Gender expectedGender = Gender.valueOf(validGender);

        Gender actualParsedCategory = validationUtility.validateGender(validGender);
        assertEquals(expectedGender, actualParsedCategory);
    }

    @Test
    void testValidateGenderThrowsGenderNotFoundException() {
        String invalidGender = ServiceConstants.VALID_GENDER_CATEGORY + "abcd";
        when(exceptionUtility.getGenderNotFoundExceptionMessage()).thenReturn("");
        assertThrows(GenderNotFoundException.class, () -> validationUtility.validateGender(invalidGender));
    }
}
