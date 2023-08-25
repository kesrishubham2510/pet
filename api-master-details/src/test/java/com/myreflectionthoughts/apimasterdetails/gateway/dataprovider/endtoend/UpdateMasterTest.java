package com.myreflectionthoughts.apimasterdetails.gateway.dataprovider.endtoend;

import com.myreflectionthoughts.apimasterdetails.configuration.TestDataGenerator;
import com.myreflectionthoughts.apimasterdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apimasterdetails.core.exception.MasterNotFoundException;
import com.myreflectionthoughts.library.dto.request.AddMasterDTO;
import com.myreflectionthoughts.library.dto.request.UpdateMasterDTO;
import com.myreflectionthoughts.library.dto.response.ExceptionResponse;
import com.myreflectionthoughts.library.dto.response.MasterDTO;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.EntityExchangeResult;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UpdateMasterTest extends TestSetup {


    @Test
    void testUpdateMasterDetails(){

        // Adding a Master
        AddMasterDTO requestPayload = TestDataGenerator.generateAddMasterDTO();
        requestPayload.setEmail("newMaster@gmail.com");

        EntityExchangeResult<MasterDTO> addMasterResponse = webTestClient.post()
                .uri(String.format("%s/add", ServiceConstants.API_QUALIFIER))
                .bodyValue(requestPayload)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(MasterDTO.class)
                .returnResult();


        assertNotNull(addMasterResponse);
        assertNotNull(addMasterResponse.getResponseBody().getId());
        assertEquals(requestPayload.getName(),Objects.requireNonNull(addMasterResponse.getResponseBody()).getName());
        assertEquals(requestPayload.getEmail(),Objects.requireNonNull(addMasterResponse.getResponseBody()).getEmail());
        assertEquals(requestPayload.getAge(),addMasterResponse.getResponseBody().getAge());
        assertEquals(requestPayload.getAddress(),Objects.requireNonNull(addMasterResponse.getResponseBody()).getAddress());


        // updating the previously added master with new details
        String masterId = addMasterResponse.getResponseBody().getId();

        UpdateMasterDTO updateMasterDTO = TestDataGenerator.generateUpdateMasterDTO();
        updateMasterDTO.setId(masterId);

        MasterDTO expectedMasterResponse = TestDataGenerator.generateUpdatedMasterDTO();

        webTestClient.put()
                .uri(String.format("%s/update/master/%s", ServiceConstants.API_QUALIFIER, masterId))
                .bodyValue(updateMasterDTO)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(MasterDTO.class)
                .consumeWith(updatedMasterResponse->{
                    assertEquals(masterId, Objects.requireNonNull(updatedMasterResponse.getResponseBody()).getId());
                    assertEquals(expectedMasterResponse.getName(), Objects.requireNonNull(updatedMasterResponse.getResponseBody()).getName());
                    assertEquals(expectedMasterResponse.getEmail(), Objects.requireNonNull(updatedMasterResponse.getResponseBody()).getEmail());
                    assertEquals(expectedMasterResponse.getAddress(), Objects.requireNonNull(updatedMasterResponse.getResponseBody()).getAddress());
                    assertEquals(expectedMasterResponse.getAge(), Objects.requireNonNull(updatedMasterResponse.getResponseBody()).getAge());
                });
    }
    @Test
    void testUpdateMasterDetails_Throws_MasterNotFoundException(){

        String masterId = "1234455";
        UpdateMasterDTO updateMasterDTO = TestDataGenerator.generateUpdateMasterDTO();

        webTestClient.put()
                .uri(String.format("%s/update/master/%s", ServiceConstants.API_QUALIFIER, masterId))
                .bodyValue(updateMasterDTO)
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody(ExceptionResponse.class)
                .consumeWith(exceptionResponse->{
                    assertEquals(MasterNotFoundException.class.getSimpleName(), Objects.requireNonNull(exceptionResponse.getResponseBody()).getError());
                    assertEquals(ServiceConstants.MASTER_NOT_FOUND_EXCEPTION, Objects.requireNonNull(exceptionResponse.getResponseBody()).getErrorMessage());
                });
    }
}