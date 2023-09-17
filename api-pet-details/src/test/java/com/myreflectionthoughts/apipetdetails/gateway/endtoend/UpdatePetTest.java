package com.myreflectionthoughts.apipetdetails.gateway.endtoend;

import com.myreflectionthoughts.apipetdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apipetdetails.core.enums.ClinicCardStatus;
import com.myreflectionthoughts.apipetdetails.core.exception.PetNotFoundException;
import com.myreflectionthoughts.apipetdetails.gateway.dataprovider.TestDataGenerator;
import com.myreflectionthoughts.library.dto.logs.LoggerUtility;
import com.myreflectionthoughts.library.dto.request.AddPetDTO;
import com.myreflectionthoughts.library.dto.request.UpdatePetDTO;
import com.myreflectionthoughts.library.dto.response.ExceptionResponse;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UpdatePetTest extends TestSetup {

    public UpdatePetTest(LoggerUtility loggerUtility) {
        super(loggerUtility);
    }

    /**
     * When a request is made to the /api-pet-details/update/pet/{petId} then the controller should
     * handle the request.
     * This should to lead to update of an existing document with specified id in the
     * database.
     * The status of the response should be 200 when the request completes successfully
     * The body of the response should contain the same data as sent in the request
     * payload i.e., the updated data from the database.
     */
    @Test
    void testUpdatePetInfo() {

        String petId;
        AddPetDTO addPetRequestPayload = TestDataGenerator.getAddPetDTO();

        // Adding a pet to the database
        EntityExchangeResult<PetDTO> recievedAddPetResponse = petWebClient.post()
                .uri(String.format("%s/add", baseURL))
                .bodyValue(addPetRequestPayload)
                .exchange()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectStatus()
                .isCreated()
                .expectBody(PetDTO.class)
                .returnResult();

        petId = recievedAddPetResponse.getResponseBody().getId();

        // asserting the created pet's details with the payload sent

        assertNotNull(recievedAddPetResponse.getResponseBody());
        assertNotNull(Objects.requireNonNull(recievedAddPetResponse.getResponseBody()).getId());
        assertEquals(addPetRequestPayload.getName(), Objects.requireNonNull(recievedAddPetResponse.getResponseBody()).getName());
        assertEquals(addPetRequestPayload.getAge(), Objects.requireNonNull(recievedAddPetResponse.getResponseBody()).getAge());
        assertEquals(addPetRequestPayload.getCategory().toUpperCase(), Objects.requireNonNull(recievedAddPetResponse.getResponseBody()).getCategory());
        assertEquals(addPetRequestPayload.getGender().toUpperCase(), Objects.requireNonNull(recievedAddPetResponse.getResponseBody()).getGender());
        assertEquals(ClinicCardStatus.NOT_APPLIED.toString(), Objects.requireNonNull(recievedAddPetResponse.getResponseBody()).getClinicCardStatus().toUpperCase());
        assertEquals(ClinicCardStatus.NOT_APPLIED.getMessage(), Objects.requireNonNull(recievedAddPetResponse.getResponseBody()).getClinicCardStatusMessage());
        assertEquals(HttpStatus.CREATED, recievedAddPetResponse.getStatus());
        assertEquals(HttpStatus.CREATED, recievedAddPetResponse.getStatus());

        // updating the pet with new details
        UpdatePetDTO updatePetRequestPayload = TestDataGenerator.getUpdatePetDTO();

        updatePetRequestPayload.setId(petId);
        updatePetRequestPayload.setAge(2.5);

        petWebClient.put()
                .uri(String.format("%s/update/pet/%s", baseURL, petId))
                .bodyValue(updatePetRequestPayload)
                .exchange()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectStatus()
                .isOk()
                .expectBody(PetDTO.class)
                .consumeWith(receivedUpdatedPetResponse -> {
                    assertNotNull(receivedUpdatedPetResponse);
                    assertNotNull(Objects.requireNonNull(receivedUpdatedPetResponse.getResponseBody()).getId());
                    assertEquals(updatePetRequestPayload.getName(), Objects.requireNonNull(receivedUpdatedPetResponse.getResponseBody()).getName());
                    assertEquals(updatePetRequestPayload.getAge(), Objects.requireNonNull(receivedUpdatedPetResponse.getResponseBody()).getAge());
                    assertEquals(updatePetRequestPayload.getCategory().toUpperCase(), Objects.requireNonNull(receivedUpdatedPetResponse.getResponseBody()).getCategory());
                    assertEquals(updatePetRequestPayload.getGender().toUpperCase(), Objects.requireNonNull(receivedUpdatedPetResponse.getResponseBody()).getGender());
                    assertEquals(updatePetRequestPayload.getClinicCardStatus().toUpperCase(), Objects.requireNonNull(receivedUpdatedPetResponse.getResponseBody()).getClinicCardStatus().toUpperCase());
                    assertEquals(ClinicCardStatus.valueOf(updatePetRequestPayload.getClinicCardStatus().toUpperCase()).getMessage(), Objects.requireNonNull(receivedUpdatedPetResponse.getResponseBody()).getClinicCardStatusMessage());
                });

        // asserting the update

        petWebClient.get()
                .uri(String.format("%s/get/pet/%s", baseURL, petId))
                .exchange()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectStatus()
                .isOk()
                .expectBody(PetDTO.class)
                .consumeWith(receivedUpdatedPetResponse -> {
                    assertNotNull(receivedUpdatedPetResponse);
                    assertNotNull(Objects.requireNonNull(receivedUpdatedPetResponse.getResponseBody()).getId());
                    assertEquals(updatePetRequestPayload.getName(), Objects.requireNonNull(receivedUpdatedPetResponse.getResponseBody()).getName());
                    assertEquals(updatePetRequestPayload.getAge(), Objects.requireNonNull(receivedUpdatedPetResponse.getResponseBody()).getAge());
                    assertEquals(updatePetRequestPayload.getCategory().toUpperCase(), Objects.requireNonNull(receivedUpdatedPetResponse.getResponseBody()).getCategory());
                    assertEquals(updatePetRequestPayload.getGender().toUpperCase(), Objects.requireNonNull(receivedUpdatedPetResponse.getResponseBody()).getGender());
                    assertEquals(updatePetRequestPayload.getClinicCardStatus().toUpperCase(), Objects.requireNonNull(receivedUpdatedPetResponse.getResponseBody()).getClinicCardStatus().toUpperCase());
                    assertEquals(ClinicCardStatus.valueOf(updatePetRequestPayload.getClinicCardStatus().toUpperCase()).getMessage(), Objects.requireNonNull(receivedUpdatedPetResponse.getResponseBody()).getClinicCardStatusMessage());
                });


    }

    /**
     * When a request is made to the /api-pet-details/update/pet/{petId} then the controller should
     * handle the request.
     * This should to lead to exception if a document with specified id is not found in the
     * database, an exception response should be returned containing the exceptionMessage and
     * exception genre with bad-request(400) status code.
     */
    @Test
    void testUpdatePetInfo_Throws_PetNotFoundException() {

        UpdatePetDTO requestPayload = TestDataGenerator.getUpdatePetDTO();
        requestPayload.setAge(2.5);

        petWebClient.put()
                .uri(String.format("%s/update/pet/%s", baseURL, ServiceConstants.DUMMY_PET_ID))
                .bodyValue(requestPayload)
                .exchange()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectStatus()
                .isBadRequest()
                .expectBody(ExceptionResponse.class)
                .consumeWith(receivedExceptionResponse -> {
                    assertNotNull(receivedExceptionResponse);
                    assertEquals(PetNotFoundException.class.getSimpleName(), Objects.requireNonNull(receivedExceptionResponse.getResponseBody()).getError());
                    assertEquals(ServiceConstants.PET_NOT_FOUND_EXCEPTION, Objects.requireNonNull(receivedExceptionResponse.getResponseBody()).getErrorMessage());
                });


    }
}
