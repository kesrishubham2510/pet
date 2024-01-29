package com.myreflectionthoughts.apigateway.integration;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.myreflectionthoughts.apigateway.core.constant.ServiceConstant;
import com.myreflectionthoughts.library.dto.request.UpdateMasterDTO;
import com.myreflectionthoughts.library.dto.request.UpdatePetDTO;
import com.myreflectionthoughts.library.dto.request.UpdateUserDTO;
import com.myreflectionthoughts.library.dto.response.MasterDTO;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import com.myreflectionthoughts.library.dto.response.UserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UpdateUserDetailsTest extends TestSetup{

    @Test
    void testUpdateUserInfo(){

        wireMockServer.stubFor(
                WireMock.put(String.format("%s/update/master/%s", ServiceConstant.MASTER_SERVICE_API_QUALIFIER,ServiceConstant.VALID_MASTER_ID))
                        .willReturn(WireMock.aResponse()
                                .withHeader("Content-Type","application/json")
                                .withStatus(200)
                                .withBodyFile("UpdateMaster.json"))
        );
        
        wireMockServer.stubFor(
                WireMock.put(String.format("%s/update/pet/%s", ServiceConstant.PET_SERVICE_API_QUALIFIER,ServiceConstant.VALID_PET_ID))
                        .willReturn(WireMock.aResponse()
                                .withHeader("Content-Type","application/json")
                                .withStatus(200)
                                .withBodyFile("UpdatePet.json"))
        );

        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setLatestUserInfo(generateUpdateMasterDTO());
        updateUserDTO.setLatestPetsInfo(List.of(generateUpdatePetDTO()));
        
        MasterDTO expectedUpdatedMaster = generateUpdatedMasterDTO();
        PetDTO expectedUpdatedPet = generatePetDTO(); 
        
        webTestClient.put()
                .uri(String.format("%s/update/%s", ServiceConstant.API_QUALIFIER,ServiceConstant.VALID_MASTER_ID))
                .bodyValue(updateUserDTO)
                .exchange()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectStatus()
                .isOk()
                .expectHeader()
                .exists("traceId")
                .expectBody(UserDTO.class)
                .consumeWith(updateUserResponse->{
                   assertEquals(expectedUpdatedMaster, updateUserResponse.getResponseBody().getMaster()); 
                   assertEquals(expectedUpdatedPet, updateUserResponse.getResponseBody().getPets().get(0)); 
                });
    }

    private  UpdateMasterDTO generateUpdateMasterDTO(){
        UpdateMasterDTO updateMasterDTO = new UpdateMasterDTO();
        updateMasterDTO.setId(ServiceConstant.VALID_MASTER_ID);
        updateMasterDTO.setName(ServiceConstant.VALID_MASTER_NAME);
        updateMasterDTO.setEmail("updated-"+ServiceConstant.VALID_MASTER_EMAIL);
        updateMasterDTO.setAddress(ServiceConstant.VALID_MASTER_ADDRESS);
        updateMasterDTO.setAge(ServiceConstant.VALID_MASTER_AGE);
        updateMasterDTO.setPassword(ServiceConstant.VALID_MASTER_PASSWORD+"-updated");
        return updateMasterDTO;
    }
    
    private  MasterDTO generateUpdatedMasterDTO(){
        MasterDTO updatedMasterDTO = new MasterDTO();
        updatedMasterDTO.setId(ServiceConstant.VALID_MASTER_ID);
        updatedMasterDTO.setName(ServiceConstant.UPDATED_MASTER_NAME);
        updatedMasterDTO.setEmail("updated-"+ServiceConstant.VALID_MASTER_EMAIL);
        updatedMasterDTO.setAddress(ServiceConstant.VALID_MASTER_ADDRESS);
        updatedMasterDTO.setAge(ServiceConstant.VALID_MASTER_AGE);
        return updatedMasterDTO;
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
