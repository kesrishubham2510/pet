package com.myreflectionthoughts.apipetdetails.gateway.dataprovider.utility;

import com.myreflectionthoughts.apipetdetails.core.enums.Category;
import com.myreflectionthoughts.apipetdetails.core.enums.ClinicCardStatus;
import com.myreflectionthoughts.apipetdetails.core.enums.Gender;
import com.myreflectionthoughts.library.dto.logs.LoggerUtility;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;

import java.util.logging.Logger;

public class ConversionUtility extends Utility {

    private final ValidationUtility validationUtility;
    private final Logger logger;

    public ConversionUtility(ValidationUtility validationUtility, LoggerUtility loggerUtility) {
        super(loggerUtility);
        this.validationUtility = validationUtility;
        this.logger = Logger.getLogger(ConversionUtility.class.getName());
    }

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
