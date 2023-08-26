package com.myreflectionthoughts.apimasterdetails.gateway.dataprovider.endtoend;

import com.myreflectionthoughts.apimasterdetails.gateway.dataprovider.repository.MasterRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

// Maybe at runtime a defined port might not be available, hence random-port
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureWebTestClient
public class TestSetup {

    @Autowired
    protected MasterRepository masterRepository;

    @Autowired
    protected WebTestClient webTestClient;

    @AfterEach
    void cleanUp(){
        masterRepository.deleteAll().subscribe();
    }


}
