package com.myreflectionthoughts.apipetdetails.gateway.service;

import com.myreflectionthoughts.apipetdetails.gateway.repository.PetRepository;
import com.myreflectionthoughts.apipetdetails.core.utility.MappingUtility;
import org.springframework.beans.factory.annotation.Autowired;

public class ServiceProvider {
    @Autowired
    protected PetRepository petRepository;

    @Autowired
    protected MappingUtility mappingUtility;
}
