package com.myreflectionthoughts.apipetdetails.gateway.endtoend;

import com.myreflectionthoughts.apipetdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apipetdetails.gateway.dataprovider.repository.PetRepository;
import com.myreflectionthoughts.apipetdetails.gateway.dataprovider.utility.ExceptionUtility;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.MongoDBContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureWebTestClient
public class TestSetup {

    protected final static MongoDBContainer mongoDBContainer;

    static {
        mongoDBContainer = new MongoDBContainer("mongo:4.4.2")
                .withEnv("MONGO_INITDB_DATABASE", "petDBTest")
                .withExposedPorts(27017);
        mongoDBContainer.start();
    }

    protected final String baseURL;
    protected final ExceptionUtility exceptionUtility;
    @Autowired
    protected PetRepository petRepository;
    @Autowired
    protected WebTestClient petWebClient;

    public TestSetup() {
        baseURL = ServiceConstants.API_QUALIFIER;
        exceptionUtility = new ExceptionUtility();
    }

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        /*
           By design, the testContainer maps this started container to any random free
           hence, we need to set it dynamically and can't set it statically

           Approach1:- we can use getFirstMappedPort(), this is useful when test container
                       exposes only one port
           Approach2:- We can get our system's mapped port by explicitly using
                       getMappedPort("test-container's exposed port") function
        */

        registry.add("spring.data.mongodb.port", () -> mongoDBContainer.getMappedPort(27017));
        registry.add("spring.data.mongodb.host", () -> "localhost");
    }

    //  cleans the DB records inserted/updated
    @AfterEach
    public void cleanUp() {
        petRepository.deleteAll().subscribe();
    }
}
