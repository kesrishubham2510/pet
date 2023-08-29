package com.myreflectionthoughts.apimasterdetails.gateway.dataprovider.endtoend;

import com.myreflectionthoughts.apimasterdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apimasterdetails.gateway.dataprovider.TestDataGenerator;
import com.myreflectionthoughts.library.dto.request.AddMasterDTO;
import com.myreflectionthoughts.library.dto.response.ExceptionResponse;
import com.myreflectionthoughts.library.dto.response.MasterDTO;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.EntityExchangeResult;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AddMasterTest extends TestSetup {


    @Test
    void testAddMaster() {

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
        assertEquals(requestPayload.getName(), Objects.requireNonNull(addMasterResponse.getResponseBody()).getName());
        assertEquals(requestPayload.getEmail(), Objects.requireNonNull(addMasterResponse.getResponseBody()).getEmail());
        assertEquals(requestPayload.getAge(), addMasterResponse.getResponseBody().getAge());
        assertEquals(requestPayload.getAddress(), Objects.requireNonNull(addMasterResponse.getResponseBody()).getAddress());

        // asserting the added master details
        String addedMasterId = addMasterResponse.getResponseBody().getId();

        webTestClient.get()
                .uri(String.format("%s/get/master/%s", ServiceConstants.API_QUALIFIER, addedMasterId))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(MasterDTO.class)
                .consumeWith(masterDetailsResponse -> {
                    assertEquals(addMasterResponse.getResponseBody(), Objects.requireNonNull(masterDetailsResponse.getResponseBody()));
                });
    }

    @Test
    void testAddMaster_Throws_MasterAlreadyExistsException() {

        String emailID = "newMaster@gmail.com";

        // Adding a Master
        AddMasterDTO requestPayload = TestDataGenerator.generateAddMasterDTO();
        requestPayload.setEmail(emailID);

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
        assertEquals(requestPayload.getName(), Objects.requireNonNull(addMasterResponse.getResponseBody()).getName());
        assertEquals(requestPayload.getEmail(), Objects.requireNonNull(addMasterResponse.getResponseBody()).getEmail());
        assertEquals(requestPayload.getAge(), addMasterResponse.getResponseBody().getAge());
        assertEquals(requestPayload.getAddress(), Objects.requireNonNull(addMasterResponse.getResponseBody()).getAddress());


        // adding a newMaster with sameEmail ID
        webTestClient.post()
                .uri(String.format("%s/add", ServiceConstants.API_QUALIFIER))
                .bodyValue(requestPayload)
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody(ExceptionResponse.class)
                .consumeWith(exceptionResponse -> {
                    assertNotNull(exceptionResponse);
                    assertEquals("EmailAlreadyExists", Objects.requireNonNull(exceptionResponse.getResponseBody()).getError());
                    assertEquals(ServiceConstants.EMAIL_ALREADY_REGISTERED, Objects.requireNonNull(exceptionResponse.getResponseBody()).getErrorMessage());
                });
    }
}