package com.myreflectionthoughts.apigateway.integration;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.myreflectionthoughts.apigateway.core.constant.ServiceConstant;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

public class StreamingDemoTest extends TestSetup{

    @Test
    void testStreamDemonstration(){
        wireMockServer.stubFor(
                WireMock
                        .get(String.format("%s/get/all", ServiceConstant.PET_SERVICE_API_QUALIFIER))
                        .willReturn(WireMock.aResponse()
                                .withStatus(200)
                                .withHeader("Content-Type", MediaType.APPLICATION_NDJSON_VALUE)
                                .withBodyFile("RetrievedPets.json"))
        );

        webTestClient.get()
                .uri(String.format("%s/demo-streaming", ServiceConstant.API_QUALIFIER))
                .exchange()
                .expectHeader()
                .contentType("text/event-stream;charset=UTF-8")
                .expectStatus()
                .isOk()
                .expectHeader()
                .exists("traceId")
                .expectBodyList(PetDTO.class)
                .hasSize(2);
    }

}
