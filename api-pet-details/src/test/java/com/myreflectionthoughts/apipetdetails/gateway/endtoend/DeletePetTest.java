package com.myreflectionthoughts.apipetdetails.gateway.endtoend;

import com.myreflectionthoughts.apipetdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apipetdetails.core.enums.ClinicCardStatus;
import com.myreflectionthoughts.apipetdetails.core.exception.PetNotFoundException;
import com.myreflectionthoughts.apipetdetails.gateway.dataprovider.TestDataGenerator;
import com.myreflectionthoughts.library.dto.request.AddPetDTO;
import com.myreflectionthoughts.library.dto.response.DeletePetDTO;
import com.myreflectionthoughts.library.dto.response.ExceptionResponse;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.EntityExchangeResult;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DeletePetTest extends TestSetup {


    /**
     *  When a request is made to the /api-pet-details/delete/pet/{petId} then the controller should
     handle the request.
     *  This should to lead to delete of a Pet Document in the database.
     *  The status of the response should be 200, as the request completes successfully.
     *  The body of the response should contain the id of the pet deleted and message claiming
     successful deletion.

     */
    @Test
    void testDeletePet(){

        ServiceConstants serviceConstants = new ServiceConstants();
        String petId;

        // Adding the pet to be deleted

        AddPetDTO requestPayload = TestDataGenerator.getAddPetDTO();

        EntityExchangeResult<PetDTO> recievedAddPetResponse = petWebClient.post()
                .uri(String.format("%s/add",baseURL))
                .bodyValue(requestPayload)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(PetDTO.class)
                .returnResult();

        petId = recievedAddPetResponse.getResponseBody().getId();

        // asserting the created pet's details with the payload sent

        assertNotNull(recievedAddPetResponse.getResponseBody());
        assertNotNull(Objects.requireNonNull(recievedAddPetResponse.getResponseBody()).getId());
        assertEquals(requestPayload.getName(), Objects.requireNonNull(recievedAddPetResponse.getResponseBody()).getName());
        assertEquals(requestPayload.getAge(), Objects.requireNonNull(recievedAddPetResponse.getResponseBody()).getAge());
        assertEquals(requestPayload.getCategory().toUpperCase(), Objects.requireNonNull(recievedAddPetResponse.getResponseBody()).getCategory());
        assertEquals(requestPayload.getMaster(), Objects.requireNonNull(recievedAddPetResponse.getResponseBody()).getMaster());
        assertEquals(requestPayload.getGender().toUpperCase(), Objects.requireNonNull(recievedAddPetResponse.getResponseBody()).getGender());
        assertEquals(ClinicCardStatus.NOT_APPLIED.toString(), Objects.requireNonNull(recievedAddPetResponse.getResponseBody()).getClinicCardStatus().toUpperCase());
        assertEquals(ClinicCardStatus.NOT_APPLIED.getMessage(), Objects.requireNonNull(recievedAddPetResponse.getResponseBody()).getClinicCardStatusMessage());
        assertEquals(HttpStatus.CREATED,recievedAddPetResponse.getStatus());
        assertEquals(HttpStatus.CREATED,recievedAddPetResponse.getStatus());

        // deleting the pet
        petWebClient.delete()
                .uri(String.format("%s/delete/pet/%s",baseURL,petId))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(DeletePetDTO.class)
                .consumeWith(receivedDeletePetResponse->{
                    assertNotNull(receivedDeletePetResponse);
                    assertEquals(petId, Objects.requireNonNull(receivedDeletePetResponse.getResponseBody()).getId());
                    assertEquals(String.format(serviceConstants.getPET_INFO_DELETED(), petId), Objects.requireNonNull(receivedDeletePetResponse.getResponseBody()).getMessage());
                });

        // asserting the successful deletion
        petWebClient.get()
                .uri(String.format("%s/get/pet/%s",baseURL,petId))
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody(ExceptionResponse.class)
                .consumeWith(exceptionResponse->{
                    assertNotNull(exceptionResponse);
                    assertEquals(PetNotFoundException.class.getSimpleName(), Objects.requireNonNull(exceptionResponse.getResponseBody()).getError());
                    assertEquals(ServiceConstants.PET_NOT_FOUND_EXCEPTION, Objects.requireNonNull(exceptionResponse.getResponseBody()).getErrorMessage());
                });

    }


    /**
     *  When a request is made to the /api-pet-details/delete/pet/{petId} then the controller PetController
     should handle the request.
     *  This should to lead to exception if a document with specified id is not found in the
     database, an exception response should be returned containing the exceptionMessage and
     exception genre with bad-request(400) status code.

     */

    @Test
    void testDeletePet_Throws_PetNotFoundException(){

        petWebClient.delete()
                .uri(String.format("%s/delete/pet/%s",baseURL,"1234"))
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody(ExceptionResponse.class)
                .consumeWith(receivedDeletePetExceptionResponse->{
                    assertNotNull(receivedDeletePetExceptionResponse);
                    assertEquals(PetNotFoundException.class.getSimpleName(), Objects.requireNonNull(receivedDeletePetExceptionResponse.getResponseBody()).getError());
                    assertEquals(ServiceConstants.PET_NOT_FOUND_EXCEPTION, Objects.requireNonNull(receivedDeletePetExceptionResponse.getResponseBody()).getErrorMessage());
                });
    }
}
