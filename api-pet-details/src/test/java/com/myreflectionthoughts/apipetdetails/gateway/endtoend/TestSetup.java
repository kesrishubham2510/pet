package com.myreflectionthoughts.apipetdetails.gateway.endtoend;

import com.myreflectionthoughts.apipetdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apipetdetails.gateway.dataprovider.repository.PetRepository;
import com.myreflectionthoughts.apipetdetails.gateway.dataprovider.utility.ExceptionUtility;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureWebTestClient
public class TestSetup {

    protected final String baseURL;

    @Autowired
    protected PetRepository petRepository;

    @Autowired
    protected WebTestClient petWebClient;

    protected final ExceptionUtility exceptionUtility;


    public TestSetup() {
        baseURL = ServiceConstants.API_QUALIFIER;
        exceptionUtility = new ExceptionUtility();
    }

    //  cleans the DB records inserted/updated
    @AfterEach
    public void cleanUp(){
        petRepository.deleteAll().subscribe();
    }
}
