package com.myreflectionthoughts.apimasterdetails.gateway.dataprovider.endtoend;

import com.myreflectionthoughts.apimasterdetails.configuration.TestDataGenerator;
import com.myreflectionthoughts.apimasterdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apimasterdetails.core.exception.MasterNotFoundException;
import com.myreflectionthoughts.library.dto.request.UpdateMasterDTO;
import com.myreflectionthoughts.library.dto.response.ExceptionResponse;
import com.myreflectionthoughts.library.dto.response.MasterDTO;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UpdateMasterTest extends TestSetup {


    @Test
    void testUpdateMasterDetails(){

        String masterId = ServiceConstants.DUMMY_MONGO_DB_ID;
        UpdateMasterDTO updateMasterDTO = TestDataGenerator.generateUpdateMasterDTO();
        updateMasterDTO.setId(masterId);

        MasterDTO expectedMasterResponse = TestDataGenerator.generateUpdatedMasterDTO();

        webTestClient.put()
                .uri(String.format("/%s/update/master/%s", ServiceConstants.API_QUALIFIER, masterId))
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
                .uri(String.format("/%s/update/master/%s", ServiceConstants.API_QUALIFIER, masterId))
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