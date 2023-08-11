package com.myreflectionthoughts.apipetdetails.utility;

import com.myreflectionthoughts.apipetdetails.enums.Category;
import com.myreflectionthoughts.apipetdetails.enums.ClinicCardStatus;
import com.myreflectionthoughts.apipetdetails.enums.Gender;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.springframework.beans.factory.annotation.Autowired;


public class ConversionUtility {

    @Autowired
    private ValidationUtility validationUtility;

    protected Converter<Gender, String> gender_To_stringConverter = new AbstractConverter<>() {
        @Override
        protected String convert(Gender source) {
            return source.toString();
        }
    };
    protected Converter<Category, String> category_To_stringConverter = new AbstractConverter<>() {
        @Override
        protected String convert(Category source) {
            return source.toString();
        }
    };
    protected Converter<ClinicCardStatus, String> clinicCardStatus_To_stringConverter = new AbstractConverter<>() {
        @Override
        protected String convert(ClinicCardStatus source) {
            return source.toString();
        }
    };

    protected Converter<String, Category> string_To_categoryConverter = new AbstractConverter<>() {
        @Override
        protected Category convert(String source) {
            return validationUtility.validateCategory(source);
        }
    };
    protected Converter<String, Gender> string_To_genderConverter = new AbstractConverter<>() {
        @Override
        protected Gender convert(String source) {
            return validationUtility.validateGender(source);
        }
    };

    protected Converter<String,ClinicCardStatus> string_To_clinicCardStatusConverter = new AbstractConverter<>() {
        @Override
        protected ClinicCardStatus convert(String source) {
            return validationUtility.validateClinicCardStatus(source);
        }
    };

}
