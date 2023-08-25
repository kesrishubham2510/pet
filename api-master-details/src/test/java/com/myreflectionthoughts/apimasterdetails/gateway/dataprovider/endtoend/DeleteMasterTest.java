package com.myreflectionthoughts.apimasterdetails.gateway.dataprovider.endtoend;

import com.myreflectionthoughts.apimasterdetails.gateway.dataprovider.TestDataGenerator;
import com.myreflectionthoughts.apimasterdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apimasterdetails.core.exception.MasterNotFoundException;
import com.myreflectionthoughts.library.dto.request.AddMasterDTO;
import com.myreflectionthoughts.library.dto.response.DeleteMasterDTO;
import com.myreflectionthoughts.library.dto.response.ExceptionResponse;
import com.myreflectionthoughts.library.dto.response.MasterDTO;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.EntityExchangeResult;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class DeleteMasterTest extends TestSetup{

    @Test
    void testDeleteMaster(){

        // Adding a Master to get deleted
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


        String masterId = addMasterResponse.getResponseBody().getId();

        // deleting the added master

        webTestClient.delete()
                .uri(String.format("%s/delete/master/%s", ServiceConstants.API_QUALIFIER, masterId))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(DeleteMasterDTO.class)
                .consumeWith(deleteMasterResponse->{
                    assertEquals(masterId, Objects.requireNonNull(deleteMasterResponse.getResponseBody()).getId());
                    assertEquals(String.format(ServiceConstants.MASTER_DELETION_MESSAGE_TEMPLATE,masterId), Objects.requireNonNull(deleteMasterResponse.getResponseBody()).getMessage());
                });

        // asserting the markForDelete flag
        webTestClient.get()
                .uri(String.format("%s/get/master/%s",ServiceConstants.API_QUALIFIER, masterId))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(MasterDTO.class)
                .consumeWith(masterDetailResponse->{
                    assertTrue(Objects.requireNonNull(masterDetailResponse.getResponseBody()).isMarkForDelete());
                });
    }

    @Test
    void testDeleteMaster_Throws_MasterNotFoundException(){

        String masterId = "1223123";

        webTestClient.delete()
                .uri(String.format("%s/delete/master/%s", ServiceConstants.API_QUALIFIER, masterId))
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