package com.myreflectionthoughts.apipetdetails.component;

import com.myreflectionthoughts.apipetdetails.utility.ConversionUtility;
import com.myreflectionthoughts.apipetdetails.utility.ExceptionUtility;
import com.myreflectionthoughts.apipetdetails.utility.MappingUtility;
import com.myreflectionthoughts.apipetdetails.utility.ValidationUtility;
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
