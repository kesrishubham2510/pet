package com.myreflectionthoughts.apimasterdetails.gateway.dataprovider.endtoend;

import com.myreflectionthoughts.apimasterdetails.gateway.dataprovider.repository.MasterRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.MongoDBContainer;

// Maybe at runtime a defined port might not be available, hence random-port
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureWebTestClient
public class TestSetup {

    protected final static MongoDBContainer mongoDBContainer;

    static {
        mongoDBContainer = new MongoDBContainer("mongo:4.4.2")
                .withEnv("MONGO_INITDB_DATABASE", "masterDBTest");
        mongoDBContainer.start();
    }

    @Autowired
    protected MasterRepository masterRepository;
    @Autowired
    protected WebTestClient webTestClient;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.auto-index-creation", () -> true);
        registry.add("spring.data.mongodb.host", () -> "localhost");
        registry.add("spring.data.mongodb.port", () -> mongoDBContainer.getFirstMappedPort());
    }

    @AfterEach
    void cleanUp() {
        masterRepository.deleteAll().subscribe();
    }

}
