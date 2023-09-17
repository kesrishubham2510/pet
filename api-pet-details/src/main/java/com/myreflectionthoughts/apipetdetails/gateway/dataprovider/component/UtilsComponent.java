package com.myreflectionthoughts.apipetdetails.gateway.dataprovider.component;

import com.myreflectionthoughts.apipetdetails.gateway.dataprovider.utility.ConversionUtility;
import com.myreflectionthoughts.apipetdetails.gateway.dataprovider.utility.ExceptionUtility;
import com.myreflectionthoughts.apipetdetails.gateway.dataprovider.utility.MappingUtility;
import com.myreflectionthoughts.apipetdetails.gateway.dataprovider.utility.ValidationUtility;
import com.myreflectionthoughts.library.dto.logs.LoggerUtility;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class UtilsComponent {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public ConversionUtility conversionUtility() {
        return new ConversionUtility(validationUtility(), loggerUtility());
    }

    @Bean
    public MappingUtility mappingUtility(ModelMapper modelMapper, ConversionUtility conversionUtility) {
        return new MappingUtility(conversionUtility, modelMapper, loggerUtility());
    }

    @Bean
    public LoggerUtility loggerUtility() {
        return new LoggerUtility();
    }

    private ExceptionUtility exceptionUtility() {
        return new ExceptionUtility(loggerUtility());
    }

    private ValidationUtility validationUtility() {
        return new ValidationUtility(exceptionUtility());
    }
}
