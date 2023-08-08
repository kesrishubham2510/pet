package com.myreflectionthoughts.apipetdetails.utility;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ConversionUtilityTest {

    @InjectMocks
    private ConversionUtility conversionUtility;

    @Mock
    private ValidationUtility validationUtility;

    @Test
    public void testGenderToStringConverter(){
//        conversionUtility.string_To_genderConverter.convert();
//        Converter<Category, String>
    }

}
