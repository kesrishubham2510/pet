/**
 *  This is a Behaviour Driven Test to check the functionality
    of the ValidationUtility
 */

package com.myreflectionthoughts.apipetdetails.utility;

import com.myreflectionthoughts.apipetdetails.constant.ServiceConstants;
import com.myreflectionthoughts.apipetdetails.enums.Category;
import com.myreflectionthoughts.apipetdetails.enums.ClinicCardStatus;
import com.myreflectionthoughts.apipetdetails.enums.Gender;
import com.myreflectionthoughts.apipetdetails.exception.CategoryNotFoundException;
import com.myreflectionthoughts.apipetdetails.exception.ClinicCardStatusNotFoundException;
import com.myreflectionthoughts.apipetdetails.exception.GenderNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class ValidationUtilityTest {

    @Autowired
    private ValidationUtility validationUtility;

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
        assertThrows(GenderNotFoundException.class, () -> validationUtility.validateGender(invalidGender));
    }

    @Test
    void testValidateClinicCardStatus() {
        String validClinicCardStatus = ServiceConstants.VALID_CLINIC_CARD_STATUS;
        ClinicCardStatus expectedClinicCardStatus = ClinicCardStatus.valueOf(validClinicCardStatus);

        ClinicCardStatus actualClinicCardStatus = validationUtility.validateClinicCardStatus(validClinicCardStatus);
        assertEquals(expectedClinicCardStatus, actualClinicCardStatus);
    }

    @Test
    void testValidateClinicCardStatusThrowsClinicCardStatusNotFoundException() {
        String invalidClinicCardStatus = ServiceConstants.VALID_CLINIC_CARD_STATUS + "abcd";
        assertThrows(ClinicCardStatusNotFoundException.class, () -> validationUtility.validateClinicCardStatus(invalidClinicCardStatus));
    }
}
