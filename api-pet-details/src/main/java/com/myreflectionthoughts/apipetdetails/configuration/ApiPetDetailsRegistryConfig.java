package com.myreflectionthoughts.apipetdetails.configuration;

import com.myreflectionthoughts.apipetdetails.core.exception.CategoryNotFoundException;
import com.myreflectionthoughts.apipetdetails.core.exception.ClinicCardStatusNotFoundException;
import com.myreflectionthoughts.apipetdetails.core.exception.GenderNotFoundException;
import com.myreflectionthoughts.apipetdetails.core.exception.PetNotFoundException;
import com.myreflectionthoughts.library.utils.ValidationUtils;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.modelmapper.MappingException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class ApiPetDetailsRegistryConfig {

    @Bean
    public MeterRegistry apiPetDetailsRegistryInit(){
        CompositeMeterRegistry apiPetDetailsRegistry = new CompositeMeterRegistry();
        apiPetDetailsRegistry.add(initExceptionRegistry());
        return apiPetDetailsRegistry;
    }

    private SimpleMeterRegistry initExceptionRegistry(){
        SimpleMeterRegistry exceptionRegistry = new SimpleMeterRegistry();
        exceptionCounters().forEach(exceptionRegistry::counter);
        return exceptionRegistry;
    }
    private List<String> exceptionCounters(){
        List<String> exceptionCounters = new ArrayList<>(Arrays.asList(
                CategoryNotFoundException.class.getSimpleName(),
                ClinicCardStatusNotFoundException.class.getSimpleName(),
                GenderNotFoundException.class.getSimpleName(),
                PetNotFoundException.class.getSimpleName(),
                "UnhandledException"
        ));

        exceptionCounters.addAll(ValidationUtils.validationExceptions());
        return exceptionCounters;
    }
}
