package com.myreflectionthoughts.apimasterdetails.gateway.dataprovider.component;

import com.myreflectionthoughts.apimasterdetails.core.utils.LogUtility;
import com.myreflectionthoughts.apimasterdetails.gateway.dataprovider.utility.MappingUtility;
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
    public MappingUtility mappingUtility(){
        LogUtility.loggerUtility.log(logger, "Initialised MappingUtility bean", Level.FINE);
        return new MappingUtility();
    }
}
