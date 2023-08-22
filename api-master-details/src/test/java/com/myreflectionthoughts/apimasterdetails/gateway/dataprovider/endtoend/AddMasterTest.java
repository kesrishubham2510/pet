package com.myreflectionthoughts.apimasterdetails.gateway.dataprovider.endtoend;

import com.myreflectionthoughts.apimasterdetails.configuration.TestDataGenerator;
import com.myreflectionthoughts.apimasterdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.library.dto.request.AddMasterDTO;
import com.myreflectionthoughts.library.dto.response.ExceptionResponse;
import com.myreflectionthoughts.library.dto.response.MasterDTO;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AddMasterTest extends TestSetup {


    @Test
    void testAddMaster(){

        AddMasterDTO requestPayload = TestDataGenerator.generateAddMasterDTO();
        requestPayload.setEmail("newMaster@gmail.com");

        webTestClient.post()
                .uri(String.format("%s/add", ServiceConstants.API_QUALIFIER))
                .bodyValue(requestPayload)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(MasterDTO.class)
                .consumeWith(response->{
                    assertNotNull(response);
                    assertNotNull(response.getResponseBody().getId());
                    assertEquals(requestPayload.getName(),Objects.requireNonNull(response.getResponseBody()).getName());
                    assertEquals(requestPayload.getEmail(),Objects.requireNonNull(response.getResponseBody()).getEmail());
                    assertEquals(requestPayload.getAge(),response.getResponseBody().getAge());
                    assertEquals(requestPayload.getAddress(),Objects.requireNonNull(response.getResponseBody()).getAddress());
                });
    }

    @Test
    void testAddMaster_Throws_MasterAlreadyExistsException(){

        AddMasterDTO requestPayload = TestDataGenerator.generateAddMasterDTO();
        // this is email value for one of the pre-inserted data
        requestPayload.setEmail("master@gmail.com");

        webTestClient.post()
                .uri(String.format("%s/add", ServiceConstants.API_QUALIFIER))
                .bodyValue(requestPayload)
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody(ExceptionResponse.class)
                .consumeWith(exceptionResponse->{
                    assertNotNull(exceptionResponse);
                    assertEquals("EmailAlreadyExists",Objects.requireNonNull(exceptionResponse.getResponseBody()).getError());
                    assertEquals(ServiceConstants.EMAIL_ALREADY_REGISTERED,Objects.requireNonNull(exceptionResponse.getResponseBody()).getErrorMessage());
                });
    }
}