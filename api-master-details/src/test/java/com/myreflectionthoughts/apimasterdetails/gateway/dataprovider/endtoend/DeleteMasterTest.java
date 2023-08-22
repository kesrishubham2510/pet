package com.myreflectionthoughts.apimasterdetails.gateway.dataprovider.endtoend;

import com.myreflectionthoughts.apimasterdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apimasterdetails.core.exception.MasterNotFoundException;
import com.myreflectionthoughts.library.dto.response.DeleteMasterDTO;
import com.myreflectionthoughts.library.dto.response.ExceptionResponse;
import com.myreflectionthoughts.library.dto.response.MasterDTO;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeleteMasterTest extends TestSetup{

    @Test
    void testDeleteMaster(){

        String masterId = ServiceConstants.DUMMY_MONGO_DB_ID;

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