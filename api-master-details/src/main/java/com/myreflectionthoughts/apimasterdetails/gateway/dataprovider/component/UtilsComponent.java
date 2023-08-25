package com.myreflectionthoughts.apimasterdetails.gateway.dataprovider.component;

import com.myreflectionthoughts.apimasterdetails.gateway.dataprovider.utility.MappingUtility;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class UtilsComponent {

    @Bean
    public MappingUtility mappingUtility() { return new MappingUtility(); }
}
