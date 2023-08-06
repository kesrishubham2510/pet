package com.myreflectionthoughts.apipetdetails.component;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class GlobalBeans {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
