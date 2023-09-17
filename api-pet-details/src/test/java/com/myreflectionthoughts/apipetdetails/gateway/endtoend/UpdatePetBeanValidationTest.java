/*
    BDD test to test the UpdatePetDTO validation scenarios
*/

package com.myreflectionthoughts.apipetdetails.gateway.endtoend;

import com.myreflectionthoughts.apipetdetails.gateway.dataprovider.TestDataGenerator;
import com.myreflectionthoughts.library.dto.logs.LoggerUtility;
import com.myreflectionthoughts.library.dto.request.AddPetDTO;
import com.myreflectionthoughts.library.dto.request.UpdatePetDTO;
import com.myreflectionthoughts.library.dto.response.ExceptionResponse;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import com.myreflectionthoughts.library.exception.InputDataException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UpdatePetBeanValidationTest extends TestSetup{

    public UpdatePetBeanValidationTest(LoggerUtility loggerUtility) {
        super(loggerUtility);
    }

    @Test
    void testUpdatePetInfo_should_throw_InputDataException_for_emptyId() {

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

        // updating the pet with new details
        UpdatePetDTO updatePetRequestPayload = TestDataGenerator.getUpdatePetDTO();

        updatePetRequestPayload.setId("");
        updatePetRequestPayload.setAge(2.5);

        petWebClient.put()
                .uri(String.format("%s/update/pet/%s", baseURL, petId))
                .bodyValue(updatePetRequestPayload)
                .exchange()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectStatus()
                .isBadRequest()
                .expectBody(ExceptionResponse.class)
                .consumeWith(exceptionResponse -> {
                    assertEquals(InputDataException.class.getSimpleName(), exceptionResponse.getResponseBody().getError());
                    assertEquals("Pet ID is required, it can't null or empty or whitespaces", exceptionResponse.getResponseBody().getErrorMessage());
                });
    }


    @Test
    void testUpdatePetInfo_should_throw_InputDataException_for_emptyName() {

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

        // updating the pet with new details
        UpdatePetDTO updatePetRequestPayload = TestDataGenerator.getUpdatePetDTO();

        updatePetRequestPayload.setName("");
        updatePetRequestPayload.setId(petId);
        updatePetRequestPayload.setAge(2.5);

        petWebClient.put()
                .uri(String.format("%s/update/pet/%s", baseURL, petId))
                .bodyValue(updatePetRequestPayload)
                .exchange()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectStatus()
                .isBadRequest()
                .expectBody(ExceptionResponse.class)
                .consumeWith(exceptionResponse -> {
                    assertEquals(InputDataException.class.getSimpleName(), exceptionResponse.getResponseBody().getError());
                    assertEquals("Pet name is required, it can't null or empty or whitespaces", exceptionResponse.getResponseBody().getErrorMessage());
                });
    }


    @Test
    void testAddPetToDatabase_should_throw_InputDataException_empty_category(){

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

        UpdatePetDTO updatePetRequestPayload = TestDataGenerator.getUpdatePetDTO();

        updatePetRequestPayload.setCategory("");
        updatePetRequestPayload.setId(petId);
        updatePetRequestPayload.setAge(2.5);

        // Adding a pet to the database
        petWebClient.put()
                .uri(String.format("%s/update/pet/%s", baseURL, petId))
                .bodyValue(updatePetRequestPayload)
                .exchange()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectStatus()
                .isBadRequest()
                .expectBody(ExceptionResponse.class)
                .consumeWith(exceptionResponse->{
                    assertEquals(InputDataException.class.getSimpleName(), exceptionResponse.getResponseBody().getError());
                    assertEquals("Category is required, it can't null or empty or whitespaces", exceptionResponse.getResponseBody().getErrorMessage());
                });
    }

    @Test
    void testAddPetToDatabase_should_throw_InputDataException_empty_gender(){

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


        UpdatePetDTO updatePetRequestPayload = TestDataGenerator.getUpdatePetDTO();

        updatePetRequestPayload.setGender("");
        updatePetRequestPayload.setId(petId);
        updatePetRequestPayload.setAge(2.5);

        // Adding a pet to the database
        petWebClient.put()
                .uri(String.format("%s/update/pet/%s", baseURL, petId))
                .bodyValue(updatePetRequestPayload)
                .exchange()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectStatus()
                .isBadRequest()
                .expectBody(ExceptionResponse.class)
                .consumeWith(exceptionResponse->{
                    assertEquals(InputDataException.class.getSimpleName(), exceptionResponse.getResponseBody().getError());
                    assertEquals("Gender is required, it can't null or empty or whitespaces", exceptionResponse.getResponseBody().getErrorMessage());
                });
    }

    @Test
    void testUpdatePet_should_throw_InputDataException_forAge(){

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


        UpdatePetDTO updatePetRequestPayload = TestDataGenerator.getUpdatePetDTO();

        updatePetRequestPayload.setId(petId);
        updatePetRequestPayload.setAge(-1);

        // Adding a pet to the database
        petWebClient.put()
                .uri(String.format("%s/update/pet/%s", baseURL, petId))
                .bodyValue(updatePetRequestPayload)
                .exchange()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectStatus()
                .isBadRequest()
                .expectBody(ExceptionResponse.class)
                .consumeWith(exceptionResponse->{
                    assertEquals(InputDataException.class.getSimpleName(), exceptionResponse.getResponseBody().getError());
                    assertEquals("Age can't be zero or negative, Please provide a Valid age for your pet", exceptionResponse.getResponseBody().getErrorMessage());
                });
    }

}
