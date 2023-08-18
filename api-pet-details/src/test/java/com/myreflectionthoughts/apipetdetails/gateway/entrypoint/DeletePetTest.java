package com.myreflectionthoughts.apipetdetails.gateway.entrypoint;

import com.myreflectionthoughts.apipetdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apipetdetails.core.exception.PetNotFoundException;
import com.myreflectionthoughts.library.dto.response.DeletePetDTO;
import com.myreflectionthoughts.library.dto.response.ExceptionResponse;
import org.junit.jupiter.api.Test;

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
    void testAddPetToDatabase(){

        ServiceConstants serviceConstants = new ServiceConstants();
        String petId = ServiceConstants.DUMMY_MONGO_DB_ID;

        petWebClient.delete()
                .uri(String.format("/%s/delete/pet/%s",baseURL,petId))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(DeletePetDTO.class)
                .consumeWith(receivedDeletePetResponse->{
                    assertNotNull(receivedDeletePetResponse);
                    assertEquals(petId, Objects.requireNonNull(receivedDeletePetResponse.getResponseBody()).getId());
                    assertEquals(String.format(serviceConstants.getPET_INFO_DELETED(), petId), Objects.requireNonNull(receivedDeletePetResponse.getResponseBody()).getMessage());
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
    void testAddPetToDatabase_Throws_GenderNotFoundException(){

        petWebClient.delete()
                .uri(String.format("/%s/delete/pet/%s",baseURL,ServiceConstants.DUMMY_PET_ID))
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
