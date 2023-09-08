package com.myreflectionthoughts.apigateway.integration;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.myreflectionthoughts.apigateway.core.constant.ServiceConstant;
import com.myreflectionthoughts.library.dto.request.UpdatePetDTO;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UpdatePetDetailsTest extends TestSetup {

    @Test
    void testUpdatePet(){

        wireMockServer.stubFor(
                WireMock.put(String.format("%s/update/pet/%s", ServiceConstant.PET_SERVICE_API_QUALIFIER,ServiceConstant.VALID_PET_ID))
                        .willReturn(WireMock.aResponse()
                                .withStatus(200)
                                .withHeader("Content-Type", "application/json")
                                .withBodyFile("UpdatePet.json"))
        );

        UpdatePetDTO updatePetDTO = generateUpdatePetDTO();
        PetDTO expectedUpdatedPetDTO = generatePetDTO();

        webTestClient.put()
                .uri(String.format("%s/update/pet/%s",ServiceConstant.API_QUALIFIER,ServiceConstant.VALID_MASTER_ID))
                .bodyValue(generateUpdatePetDTO())
                .exchange()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectStatus()
                .isOk()
                .expectBody(PetDTO.class)
                .consumeWith(updatedPetDTOResponse-> {
                    assertEquals(expectedUpdatedPetDTO, updatedPetDTOResponse.getResponseBody());
                });
    }

    private UpdatePetDTO generateUpdatePetDTO(){
        UpdatePetDTO updatePetDTO = new UpdatePetDTO();
        updatePetDTO.setMaster(ServiceConstant.VALID_MASTER_ID);
        updatePetDTO.setId(ServiceConstant.VALID_PET_ID);
        updatePetDTO.setAge(2.0);
        updatePetDTO.setCategory("DOG");
        updatePetDTO.setGender("FEMALE");
        updatePetDTO.setName(ServiceConstant.UPDATED_PET_NAME);
        updatePetDTO.setClinicCardStatus("NOT_APPLIED");
        return updatePetDTO;
    }

    private PetDTO generatePetDTO(){
        PetDTO updatedPetDTO = new PetDTO();
        updatedPetDTO.setMaster(ServiceConstant.VALID_MASTER_ID);
        updatedPetDTO.setId(ServiceConstant.VALID_PET_ID);
        updatedPetDTO.setAge(2.0);
        updatedPetDTO.setCategory("DOG");
        updatedPetDTO.setGender("FEMALE");
        updatedPetDTO.setName(ServiceConstant.UPDATED_PET_NAME);
        updatedPetDTO.setClinicCardStatus("NOT_APPLIED");
        updatedPetDTO.setClinicCardStatusMessage("User has not applied for the Card yet");
        return updatedPetDTO;
    }
}
