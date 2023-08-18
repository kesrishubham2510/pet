package com.myreflectionthoughts.apipetdetails.gateway.entrypoint;

import com.myreflectionthoughts.apipetdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apipetdetails.configuration.TestDataGenerator;
import com.myreflectionthoughts.apipetdetails.core.entity.Pet;
import com.myreflectionthoughts.apipetdetails.gateway.repository.PetRepository;
import com.myreflectionthoughts.apipetdetails.core.utility.ExceptionUtility;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.function.Consumer;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureWebTestClient
public class TestSetup {

    protected String baseURL = ServiceConstants.API_QUALIFIER;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    protected WebTestClient petWebClient;

    @Autowired
    protected ExceptionUtility exceptionUtility;

    @BeforeEach
    public void TestDataSetUp(){
        Consumer<Pet> petConsumer = document-> log.info("Document inserted:- "+document);
        petRepository.saveAll(TestDataGenerator.generateDummyPets()).subscribe(petConsumer);
    }


    //  cleans the DB records inserted/updated
    @AfterEach
    public void cleanUp(){
        Consumer<Void> documentConsumer = x-> log.info("Documents deleted:- ");
        petRepository.deleteAll().subscribe(documentConsumer);
    }
}
