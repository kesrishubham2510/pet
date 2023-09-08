package com.myreflectionthoughts.apigateway.integration;


import com.github.tomakehurst.wiremock.client.WireMock;
import com.myreflectionthoughts.apigateway.core.constant.ServiceConstant;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import org.junit.jupiter.api.Test;

public class RetrievePetsOfUserTest extends TestSetup{

    @Test
    void testRetrieveUserPets(){

        wireMockServer.stubFor(
                WireMock
                        .get(String.format("%s/get/pets/%s", ServiceConstant.PET_SERVICE_API_QUALIFIER, ServiceConstant.VALID_MASTER_ID))
                        .willReturn(WireMock.aResponse()
                                .withStatus(200)
                                .withHeader("Content-Type","application/x-ndjson")
                                .withBodyFile("RetrievedPetsOfUser.json"))
        );

        webTestClient.get()
                     .uri(String.format("%s/get/pets/%s", ServiceConstant.API_QUALIFIER, ServiceConstant.VALID_MASTER_ID))
                     .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(PetDTO.class)
                .hasSize(1);
    }

    @Test
    void testRetrieveUserPets_EmptyPetList(){

        wireMockServer.stubFor(
                WireMock.get(String.format("%s/get/pets/%s", ServiceConstant.PET_SERVICE_API_QUALIFIER, ServiceConstant.VALID_MASTER_ID))
                        .willReturn(
                                WireMock
                                        .aResponse()
                                        .withStatus(200)
                                        .withHeader("Content-Type","application/x-ndjson")
                                        .withBodyFile("RetrievedPetsOfUser_EmptyList.json"))
        );

        webTestClient.get()
                .uri(String.format("%s/get/pets/%s", ServiceConstant.API_QUALIFIER, ServiceConstant.VALID_MASTER_ID))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(PetDTO.class)
                .hasSize(0);
    }

}
