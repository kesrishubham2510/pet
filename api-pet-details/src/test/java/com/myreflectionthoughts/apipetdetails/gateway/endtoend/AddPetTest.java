package com.myreflectionthoughts.apipetdetails.gateway.endtoend;

import com.myreflectionthoughts.apipetdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apipetdetails.gateway.dataprovider.TestDataGenerator;
import com.myreflectionthoughts.apipetdetails.core.enums.ClinicCardStatus;
import com.myreflectionthoughts.apipetdetails.core.exception.CategoryNotFoundException;
import com.myreflectionthoughts.apipetdetails.core.exception.GenderNotFoundException;
import com.myreflectionthoughts.library.dto.request.AddPetDTO;
import com.myreflectionthoughts.library.dto.response.ExceptionResponse;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class AddPetTest extends  TestSetup{

    /**
     *  When a request is made to the /api-pet-details/add then the controller should
        handle the request.
     *  This should to lead to creation of a Pet Document in the
        database.
     *  The status of the response should be 201, as a new resource is created due to
        the request
     *  The body of the response should contain the same data as sent in the request
        payload.

     */
    @Test
    void testAddPetToDatabase(){

        AddPetDTO requestPayload = TestDataGenerator.getAddPetDTO();

        petWebClient.post()
                    .uri(String.format("%s/add",baseURL))
                    .bodyValue(requestPayload)
                    .exchange()
                    .expectStatus()
                    .isCreated()
                    .expectBody(PetDTO.class)
                    .consumeWith(receivedAddPetResponse->{
                        assertNotNull(receivedAddPetResponse);
                        assertNotNull(Objects.requireNonNull(receivedAddPetResponse.getResponseBody()).getId());
                        assertEquals(requestPayload.getName(), Objects.requireNonNull(receivedAddPetResponse.getResponseBody()).getName());
                        assertEquals(requestPayload.getAge(), Objects.requireNonNull(receivedAddPetResponse.getResponseBody()).getAge());
                        assertEquals(requestPayload.getCategory().toUpperCase(), Objects.requireNonNull(receivedAddPetResponse.getResponseBody()).getCategory());
                        assertEquals(requestPayload.getMaster(), Objects.requireNonNull(receivedAddPetResponse.getResponseBody()).getMaster());
                        assertEquals(requestPayload.getGender().toUpperCase(), Objects.requireNonNull(receivedAddPetResponse.getResponseBody()).getGender());
                        assertEquals(ClinicCardStatus.NOT_APPLIED.toString(), Objects.requireNonNull(receivedAddPetResponse.getResponseBody()).getClinicCardStatus().toUpperCase());
                        assertEquals(ClinicCardStatus.NOT_APPLIED.getMessage(), Objects.requireNonNull(receivedAddPetResponse.getResponseBody()).getClinicCardStatusMessage());
                        assertEquals(HttpStatus.CREATED,receivedAddPetResponse.getStatus());
                        assertEquals(HttpStatus.CREATED,receivedAddPetResponse.getStatus());
                    });
    }

    /**
     *  When a request is made to the /api-pet-details/add then the controller PetController
        should handle the request.
     *  When the request is sent with wrong Category data, an exception response should be
        returned containing the exceptionMessage and exception genre with bad-request(400)
        status code

     */

    @Test
    void testAddPetToDatabase_Throws_CategoryNotFoundException(){

        AddPetDTO requestPayload = TestDataGenerator.getAddPetDTO();
        requestPayload.setCategory(ServiceConstants.VALID_CATEGORY_STRING+"fgr");

        petWebClient.post()
                    .uri(String.format("%s/add",baseURL))
                    .bodyValue(requestPayload)
                    .exchange()
                    .expectStatus()
                    .isBadRequest()
                    .expectBody(ExceptionResponse.class)
                    .consumeWith(receivedAddPetExceptionResponse->{
                        assertNotNull(receivedAddPetExceptionResponse);
                        assertEquals(CategoryNotFoundException.class.getSimpleName(), Objects.requireNonNull(receivedAddPetExceptionResponse.getResponseBody()).getError());
                        assertEquals(exceptionUtility.getCategoryNotFoundExceptionMessage(requestPayload.getCategory()), Objects.requireNonNull(receivedAddPetExceptionResponse.getResponseBody()).getErrorMessage());
                    });
    }

    /**
     *  When a request is made to the /api-pet-details/add then the controller PetController
        should handle the request.
     *  When the request is sent with wrong Gender data, an exception response should be
        returned containing the exceptionMessage and exception genre with bad-request(400)
        status code

     */

    @Test
    void testAddPetToDatabase_Throws_GenderNotFoundException(){

        AddPetDTO requestPayload = TestDataGenerator.getAddPetDTO();
        requestPayload.setGender(ServiceConstants.VALID_GENDER_CATEGORY+"fgr");

        petWebClient.post()
                    .uri(String.format("%s/add",baseURL))
                    .bodyValue(requestPayload)
                    .exchange()
                    .expectStatus()
                    .isBadRequest()
                    .expectBody(ExceptionResponse.class)
                    .consumeWith(receivedAddPetExceptionResponse->{
                        assertNotNull(receivedAddPetExceptionResponse);
                        assertEquals(GenderNotFoundException.class.getSimpleName(), Objects.requireNonNull(receivedAddPetExceptionResponse.getResponseBody()).getError());
                        assertEquals(exceptionUtility.getGenderNotFoundExceptionMessage(), Objects.requireNonNull(receivedAddPetExceptionResponse.getResponseBody()).getErrorMessage());
                    });
    }

}
