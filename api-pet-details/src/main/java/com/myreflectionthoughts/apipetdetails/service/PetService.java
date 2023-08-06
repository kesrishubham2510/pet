package com.myreflectionthoughts.apipetdetails.service;

import com.myreflectionthoughts.apipetdetails.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class PetService {

    @Autowired
    private PetRepository petRepository;


}
