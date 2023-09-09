package com.myreflectionthoughts.apigateway.integration;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.myreflectionthoughts.apigateway.core.constant.ServiceConstant;
import com.myreflectionthoughts.library.dto.response.ExceptionResponse;
import com.myreflectionthoughts.library.dto.response.MasterDTO;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import com.myreflectionthoughts.library.dto.response.UserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RetrieveUserInfoTest extends TestSetup{

    @Test
    void testRetrieveUserInfo(){

        wireMockServer.stubFor(
                WireMock.get(String.format("%s/get/master/%s", ServiceConstant.MASTER_SERVICE_API_QUALIFIER, ServiceConstant.VALID_MASTER_ID))
                        .willReturn(
                                WireMock.aResponse()
                                        .withStatus(200)
                                        .withHeader("Content-Type", "application/json")
                                        .withBodyFile("RetrievedMasterInfo.json")
                        )
        );

        wireMockServer.stubFor(
                WireMock.get(String.format("%s/get/pets/%s", ServiceConstant.PET_SERVICE_API_QUALIFIER, ServiceConstant.VALID_MASTER_ID))
                        .willReturn(
                                WireMock.aResponse()
                                        .withStatus(200)
                                        .withHeader("Content-Type", "application/json")
                                        .withBodyFile("AddPet_Success.json")
                        )
        );

        UserDTO expectedUserDTO = new UserDTO();
        expectedUserDTO.setMaster(generateMasterDTO());
        expectedUserDTO.setPets(List.of(generatePetDTO()));

        webTestClient.get()
                .uri(String.format("%s/get/user/%s",ServiceConstant.API_QUALIFIER,ServiceConstant.VALID_MASTER_ID))
                .exchange()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectStatus()
                .isOk()
                .expectBody(UserDTO.class)
                .consumeWith(retrievedUSerInfoResponse->{
                    assertEquals(expectedUserDTO.getMaster(), retrievedUSerInfoResponse.getResponseBody().getMaster());
                    assertEquals(expectedUserDTO.getPets().get(0), retrievedUSerInfoResponse.getResponseBody().getPets().get(0));
                });
    }

    @Test
    void testRetrieveUserInfo_MasterNotFoundException(){

        wireMockServer.stubFor(
                WireMock.get(String.format("%s/get/master/%s", ServiceConstant.MASTER_SERVICE_API_QUALIFIER, "12347"))
                        .willReturn(
                                WireMock.aResponse()
                                        .withStatus(400)
                                        .withHeader("Content-Type", "application/json")
                                        .withBodyFile("MasterNotFoundException.json")
                        )
        );

        UserDTO expectedUserDTO = new UserDTO();
        expectedUserDTO.setMaster(generateMasterDTO());
        expectedUserDTO.setPets(List.of(generatePetDTO()));

        webTestClient.get()
                .uri(String.format("%s/get/user/%s",ServiceConstant.API_QUALIFIER,"12347"))
                .exchange()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectStatus()
                .isBadRequest()
                .expectBody(ExceptionResponse.class)
                .consumeWith(exceptionResponse->{
                    assertEquals("MasterNotFoundException", exceptionResponse.getResponseBody().getError());
                    assertEquals("Master not found", exceptionResponse.getResponseBody().getErrorMessage());
                });
    }

    private static MasterDTO generateMasterDTO(){
        MasterDTO masterDTO = new MasterDTO();
        masterDTO.setId(ServiceConstant.VALID_MASTER_ID);
        masterDTO.setName(ServiceConstant.VALID_MASTER_NAME);
        masterDTO.setEmail(ServiceConstant.VALID_MASTER_EMAIL);
        masterDTO.setAddress(ServiceConstant.VALID_MASTER_ADDRESS);
        masterDTO.setAge(ServiceConstant.VALID_MASTER_AGE);
        return masterDTO;
    }

    private  PetDTO generatePetDTO() {
        PetDTO pet = new PetDTO();
        pet.setMaster(ServiceConstant.VALID_MASTER_ID);
        pet.setId(ServiceConstant.VALID_PET_ID);
        pet.setAge(1.0);
        pet.setCategory("DOG");
        pet.setGender("FEMALE");
        pet.setName("pet-name");
        pet.setClinicCardStatus("NOT_APPLIED");
        pet.setClinicCardStatusMessage("User has not applied for the Card yet");
        return pet;
    }
}
