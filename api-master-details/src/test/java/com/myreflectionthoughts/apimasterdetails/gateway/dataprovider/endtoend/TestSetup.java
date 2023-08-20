package com.myreflectionthoughts.apimasterdetails.gateway.dataprovider.endtoend;

import com.myreflectionthoughts.apimasterdetails.configuration.TestDataGenerator;
import com.myreflectionthoughts.apimasterdetails.core.entity.Master;
import com.myreflectionthoughts.apimasterdetails.gateway.dataprovider.repository.MasterRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.function.Consumer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureWebTestClient
public class TestSetup {

    @Autowired
    private MasterRepository masterRepository;

    @Autowired
    protected WebTestClient webTestClient;

    @BeforeEach
    void setUp(){
        Consumer<Master> masterConsumer = (master)-> System.out.println("Inserted master:- "+master);
        masterRepository.saveAll(TestDataGenerator.generateDummyMasters()).subscribe(masterConsumer);
    }

    @AfterEach
    void cleanUp(){
        masterRepository.deleteAll().subscribe();
    }

}
