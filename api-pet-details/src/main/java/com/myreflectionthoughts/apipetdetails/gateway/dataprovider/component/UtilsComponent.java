package com.myreflectionthoughts.apipetdetails.gateway.dataprovider.component;

import com.myreflectionthoughts.apipetdetails.core.utils.LogUtility;
import com.myreflectionthoughts.apipetdetails.gateway.dataprovider.utility.ConversionUtility;
import com.myreflectionthoughts.apipetdetails.gateway.dataprovider.utility.ExceptionUtility;
import com.myreflectionthoughts.apipetdetails.gateway.dataprovider.utility.MappingUtility;
import com.myreflectionthoughts.apipetdetails.gateway.dataprovider.utility.ValidationUtility;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class UtilsComponent {

    private final Logger logger;

    public UtilsComponent(){
        logger = Logger.getLogger(UtilsComponent.class.getName());
    }

    @Bean
    public ModelMapper modelMapper() {
        LogUtility.loggerUtility.log(logger, "Initialising the ModelMapper bean" , Level.FINE);
        return new ModelMapper();
    }

    @Bean
    public ConversionUtility conversionUtility() {
        LogUtility.loggerUtility.log(logger, "Initialising the ConversionUtility bean" , Level.FINE);
        return new ConversionUtility(validationUtility());
    }

    @Bean
    public MappingUtility mappingUtility(ModelMapper modelMapper, ConversionUtility conversionUtility) {
        LogUtility.loggerUtility.log(logger, "Initialising the MappingUtility bean" , Level.FINE);
        return new MappingUtility(conversionUtility, modelMapper);
    }

    private ExceptionUtility exceptionUtility() {
        return new ExceptionUtility();
    }

    private ValidationUtility validationUtility() {
        return new ValidationUtility(exceptionUtility());
    }
}
