package com.myreflectionthoughts.apipetdetails.configuration;

import com.myreflectionthoughts.apipetdetails.core.utility.ConversionUtility;
import com.myreflectionthoughts.apipetdetails.core.utility.ExceptionUtility;
import com.myreflectionthoughts.apipetdetails.core.utility.MappingUtility;
import com.myreflectionthoughts.apipetdetails.core.utility.ValidationUtility;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class GlobalBeans {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public ExceptionUtility exceptionUtility(){ return  new ExceptionUtility();}

    @Bean
    public ConversionUtility conversionUtility(){ return new ConversionUtility();}

    @Bean
    public MappingUtility mappingUtility(){ return new MappingUtility();}

    @Bean
    ValidationUtility validationUtility(){ return new ValidationUtility();}
}
