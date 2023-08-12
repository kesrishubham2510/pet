package com.myreflectionthoughts.apipetdetails.bdd;

import com.myreflectionthoughts.apipetdetails.constant.ServiceConstants;
import com.myreflectionthoughts.apipetdetails.data.TestDataGenerator;
import com.myreflectionthoughts.apipetdetails.enums.ClinicCardStatus;
import com.myreflectionthoughts.apipetdetails.exception.PetNotFoundException;
import com.myreflectionthoughts.library.dto.request.UpdatePetDTO;
import com.myreflectionthoughts.library.dto.response.ExceptionResponse;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UpdatePetTest extends TestSetup{

    /**
     *  When a request is made to the /api-pet-details/update/pet/{petId} then the controller should
     handle the request.
     *  This should to lead to update of an existing document with specified id in the
     database.
     *  The status of the response should be 200 when the request completes successfully
     *  The body of the response should contain the same data as sent in the request
     payload i.e., the updated data from the database.

     */
    @Test
    void testUpdatePetInfo(){
        String petId = ServiceConstants.DUMMY_MONGO_DB_ID;
        UpdatePetDTO requestPayload = TestDataGenerator.getUpdatePetDTO();
        requestPayload.setId(petId);
        requestPayload.setAge(2.5);

        petWebClient.put()
                .uri(String.format("/%s/update/pet/%s",baseURL,petId))
                .bodyValue(requestPayload)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(PetDTO.class)
                .consumeWith(receivedUpdatedPetResponse->{
                    assertNotNull(receivedUpdatedPetResponse);
                    assertNotNull(Objects.requireNonNull(receivedUpdatedPetResponse.getResponseBody()).getId());
                    assertEquals(requestPayload.getName(), Objects.requireNonNull(receivedUpdatedPetResponse.getResponseBody()).getName());
                    assertEquals(requestPayload.getAge(), Objects.requireNonNull(receivedUpdatedPetResponse.getResponseBody()).getAge());
                    assertEquals(requestPayload.getCategory().toUpperCase(), Objects.requireNonNull(receivedUpdatedPetResponse.getResponseBody()).getCategory());
                    assertEquals(requestPayload.getMaster(), Objects.requireNonNull(receivedUpdatedPetResponse.getResponseBody()).getMaster());
                    assertEquals(requestPayload.getGender().toUpperCase(), Objects.requireNonNull(receivedUpdatedPetResponse.getResponseBody()).getGender());
                    assertEquals(requestPayload.getClinicCardStatus().toUpperCase(), Objects.requireNonNull(receivedUpdatedPetResponse.getResponseBody()).getClinicCardStatus().toUpperCase());
                    assertEquals(ClinicCardStatus.valueOf(requestPayload.getClinicCardStatus().toUpperCase()).getMessage(), Objects.requireNonNull(receivedUpdatedPetResponse.getResponseBody()).getClinicCardStatusMessage());
                });


    }
    /**
     *  When a request is made to the /api-pet-details/update/pet/{petId} then the controller should
     handle the request.
     *  This should to lead to exception if a document with specified id is not found in the
     database, an exception response should be returned containing the exceptionMessage and
     exception genre with bad-request(400) status code.

     */
    @Test
    void testUpdatePetInfo_Throws_PetNotFoundException(){

        UpdatePetDTO requestPayload = TestDataGenerator.getUpdatePetDTO();
        requestPayload.setAge(2.5);

        petWebClient.put()
                .uri(String.format("/%s/update/pet/%s",baseURL,ServiceConstants.DUMMY_PET_ID))
                .bodyValue(requestPayload)
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody(ExceptionResponse.class)
                .consumeWith(receivedExceptionResponse->{
                    assertNotNull(receivedExceptionResponse);
                    assertEquals(PetNotFoundException.class.getSimpleName(), Objects.requireNonNull(receivedExceptionResponse.getResponseBody()).getError());
                    assertEquals(ServiceConstants.PET_NOT_FOUND_EXCEPTION, Objects.requireNonNull(receivedExceptionResponse.getResponseBody()).getErrorMessage());
                });


    }
}
