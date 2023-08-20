package com.myreflectionthoughts.apimasterdetails.configuration;

import com.myreflectionthoughts.apimasterdetails.core.usecase.*;
import com.myreflectionthoughts.apimasterdetails.gateway.dataprovider.utility.MappingUtility;
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
    public MappingUtility mappingUtility() {
        return new MappingUtility();
    }
}
