package com.myreflectionthoughts.apigateway.integration;


import com.github.tomakehurst.wiremock.WireMockServer;
import io.micrometer.context.ContextRegistry;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

/*
         * To simulate a live running server during the tests, wiremock has been used.
         * The wiremock server needs a specific port to run, here port:8005 is used.
         * The restClients ( master-service-client and pet-service-client ) are configured to run on 8002 & 8003,
           but when we mock those server using wiremock, we need them to run on a port where wiremock server runs.
           Hence, the client URLs will be overridden in the application.yml for the test purpose.

 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestSetup {

    private final int mockServerPort = 8005;
    @Autowired
    protected WebTestClient webTestClient;
    protected WireMockServer wireMockServer;

    public TestSetup() {
        wireMockServer = new WireMockServer(mockServerPort);
    }

    @BeforeEach
    void setUpServer() {
        wireMockServer.start();
        ContextRegistry.getInstance()
                .registerThreadLocalAccessor("traceId", ()-> MDC.get("traceId"), traceId -> MDC.put("traceId",traceId), ()-> MDC.remove("traceId"));

    }

    @AfterEach
    void tearDownServer() {
        wireMockServer.stop();
        ContextRegistry.getInstance().removeThreadLocalAccessor("traceId");
    }
}
