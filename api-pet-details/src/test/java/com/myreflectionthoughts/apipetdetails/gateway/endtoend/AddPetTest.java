package com.myreflectionthoughts.apipetdetails.gateway.endtoend;

import com.myreflectionthoughts.apipetdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apipetdetails.core.enums.ClinicCardStatus;
import com.myreflectionthoughts.apipetdetails.core.exception.CategoryNotFoundException;
import com.myreflectionthoughts.apipetdetails.core.exception.GenderNotFoundException;
import com.myreflectionthoughts.apipetdetails.gateway.dataprovider.TestDataGenerator;
import com.myreflectionthoughts.library.dto.request.AddPetDTO;
import com.myreflectionthoughts.library.dto.response.ExceptionResponse;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class AddPetTest extends TestSetup {

    /**
     * When a request is made to the /api-pet-details/add then the controller should
     * handle the request.
     * This should to lead to creation of a Pet Document in the
     * database.
     * The status of the response should be 201, as a new resource is created due to
     * the request
     * The body of the response should contain the same data as sent in the request
     * payload.
     */
    @Test
    void testAddPetToDatabase() {

        String petId;
        AddPetDTO requestPayload = TestDataGenerator.getAddPetDTO();

        // Adding a pet to the database
        EntityExchangeResult<PetDTO> recievedAddPetResponse = petWebClient.post()
                .uri(String.format("%s/add", baseURL))
                .bodyValue(requestPayload)
                .exchange()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectStatus()
                .isCreated()
                .expectBody(PetDTO.class)
                .returnResult();

        petId = recievedAddPetResponse.getResponseBody().getId();

        System.out.println(recievedAddPetResponse.getResponseBody().toString());
        // asserting the created pet's details with the payload sent

        assertNotNull(recievedAddPetResponse.getResponseBody());
        assertNotNull(Objects.requireNonNull(recievedAddPetResponse.getResponseBody()).getId());
        assertEquals(requestPayload.getName(), Objects.requireNonNull(recievedAddPetResponse.getResponseBody()).getName());
        assertEquals(requestPayload.getAge(), Objects.requireNonNull(recievedAddPetResponse.getResponseBody()).getAge());
        assertEquals(requestPayload.getCategory().toUpperCase(), Objects.requireNonNull(recievedAddPetResponse.getResponseBody()).getCategory());
        assertEquals(requestPayload.getGender().toUpperCase(), Objects.requireNonNull(recievedAddPetResponse.getResponseBody()).getGender());
        assertEquals(ClinicCardStatus.NOT_APPLIED.toString(), Objects.requireNonNull(recievedAddPetResponse.getResponseBody()).getClinicCardStatus().toUpperCase());
        assertEquals(ClinicCardStatus.NOT_APPLIED.getMessage(), Objects.requireNonNull(recievedAddPetResponse.getResponseBody()).getClinicCardStatusMessage());
        assertEquals(HttpStatus.CREATED, recievedAddPetResponse.getStatus());
        assertEquals(HttpStatus.CREATED, recievedAddPetResponse.getStatus());

        // retrieving the pet to assert the flow for saving the details

        petWebClient.get()
                .uri(String.format("%s/get/pet/%s", baseURL, petId))
                .exchange()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectStatus()
                .isOk()
                .expectBody(PetDTO.class)
                .consumeWith(receivedPetResponse -> {
                    assertEquals(recievedAddPetResponse.getResponseBody().getName(), Objects.requireNonNull(receivedPetResponse.getResponseBody()).getName());
                    assertEquals(recievedAddPetResponse.getResponseBody().getAge(), Objects.requireNonNull(receivedPetResponse.getResponseBody()).getAge());
                    assertEquals(recievedAddPetResponse.getResponseBody().getCategory().toString(), Objects.requireNonNull(receivedPetResponse.getResponseBody()).getCategory().toUpperCase());
                    assertEquals(recievedAddPetResponse.getResponseBody().getGender().toString(), Objects.requireNonNull(receivedPetResponse.getResponseBody()).getGender().toUpperCase());
                    assertEquals(recievedAddPetResponse.getResponseBody().getId(), Objects.requireNonNull(receivedPetResponse.getResponseBody()).getId());
                    assertEquals(recievedAddPetResponse.getResponseBody().getClinicCardStatus().toString(), Objects.requireNonNull(receivedPetResponse.getResponseBody()).getClinicCardStatus().toUpperCase());
                    assertEquals(recievedAddPetResponse.getResponseBody().getClinicCardStatusMessage(), Objects.requireNonNull(receivedPetResponse.getResponseBody()).getClinicCardStatusMessage());
                });
    }

    /**
     * When a request is made to the /api-pet-details/add then the controller PetController
     * should handle the request.
     * When the request is sent with wrong Category data, an exception response should be
     * returned containing the exceptionMessage and exception genre with bad-request(400)
     * status code
     */

    @Test
    void testAddPetToDatabase_Throws_CategoryNotFoundException() {

        AddPetDTO requestPayload = TestDataGenerator.getAddPetDTO();
        requestPayload.setCategory(ServiceConstants.VALID_CATEGORY_STRING + "fgr");

        petWebClient.post()
                .uri(String.format("%s/add", baseURL))
                .bodyValue(requestPayload)
                .exchange()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectStatus()
                .isBadRequest()
                .expectBody(ExceptionResponse.class)
                .consumeWith(receivedAddPetExceptionResponse -> {
                    assertNotNull(receivedAddPetExceptionResponse);
                    assertEquals(CategoryNotFoundException.class.getSimpleName(), Objects.requireNonNull(receivedAddPetExceptionResponse.getResponseBody()).getError());
                    assertEquals(exceptionUtility.getCategoryNotFoundExceptionMessage(requestPayload.getCategory()), Objects.requireNonNull(receivedAddPetExceptionResponse.getResponseBody()).getErrorMessage());
                });
    }

    /**
     * When a request is made to the /api-pet-details/add then the controller PetController
     * should handle the request.
     * When the request is sent with wrong Gender data, an exception response should be
     * returned containing the exceptionMessage and exception genre with bad-request(400)
     * status code
     */

    @Test
    void testAddPetToDatabase_Throws_GenderNotFoundException() {

        AddPetDTO requestPayload = TestDataGenerator.getAddPetDTO();
        requestPayload.setGender(ServiceConstants.VALID_GENDER_CATEGORY + "fgr");

        petWebClient.post()
                .uri(String.format("%s/add", baseURL))
                .bodyValue(requestPayload)
                .exchange()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectStatus()
                .isBadRequest()
                .expectBody(ExceptionResponse.class)
                .consumeWith(receivedAddPetExceptionResponse -> {
                    assertNotNull(receivedAddPetExceptionResponse);
                    assertEquals(GenderNotFoundException.class.getSimpleName(), Objects.requireNonNull(receivedAddPetExceptionResponse.getResponseBody()).getError());
                    assertEquals(exceptionUtility.getGenderNotFoundExceptionMessage(), Objects.requireNonNull(receivedAddPetExceptionResponse.getResponseBody()).getErrorMessage());
                });
    }

}
