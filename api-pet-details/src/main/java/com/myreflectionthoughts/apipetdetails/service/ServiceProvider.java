package com.myreflectionthoughts.apipetdetails.service;

import com.myreflectionthoughts.apipetdetails.repository.PetRepository;
import com.myreflectionthoughts.apipetdetails.utility.MappingUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public class ServiceProvider {
    @Autowired
    protected PetRepository petRepository;

    @Autowired
    protected MappingUtility mappingUtility;
}
