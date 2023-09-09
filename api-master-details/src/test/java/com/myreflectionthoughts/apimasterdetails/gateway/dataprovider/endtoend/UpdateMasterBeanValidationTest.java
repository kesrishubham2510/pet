package com.myreflectionthoughts.apimasterdetails.gateway.dataprovider.endtoend;

import com.myreflectionthoughts.apimasterdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apimasterdetails.gateway.dataprovider.TestDataGenerator;
import com.myreflectionthoughts.library.dto.request.AddMasterDTO;
import com.myreflectionthoughts.library.dto.request.UpdateMasterDTO;
import com.myreflectionthoughts.library.dto.response.ExceptionResponse;
import com.myreflectionthoughts.library.dto.response.MasterDTO;
import com.myreflectionthoughts.library.exception.InputDataException;
import com.myreflectionthoughts.library.utils.ValidationUtils;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UpdateMasterBeanValidationTest extends TestSetup {

    @Test
    void testUpdateMasterDetails_should_throw_InputDataException_for_empty_masterId() {

        AddMasterDTO requestPayload = TestDataGenerator.generateAddMasterDTO();
        requestPayload.setEmail("newMaster@gmail.com");

        EntityExchangeResult<MasterDTO> addMasterResponse = webTestClient.post()
                .uri(String.format("%s/add", ServiceConstants.API_QUALIFIER))
                .bodyValue(requestPayload)
                .exchange()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectStatus()
                .isCreated()
                .expectBody(MasterDTO.class)
                .returnResult();


        assertNotNull(addMasterResponse);
        assertNotNull(addMasterResponse.getResponseBody().getId());
        assertEquals(requestPayload.getName(), Objects.requireNonNull(addMasterResponse.getResponseBody()).getName());
        assertEquals(requestPayload.getEmail(),Objects.requireNonNull(addMasterResponse.getResponseBody()).getEmail());
        assertEquals(requestPayload.getAge(),addMasterResponse.getResponseBody().getAge());
        assertEquals(requestPayload.getAddress(),Objects.requireNonNull(addMasterResponse.getResponseBody()).getAddress());


        // updating the previously added master with new details
        String masterId = addMasterResponse.getResponseBody().getId();

        UpdateMasterDTO updateMasterDTO = TestDataGenerator.generateUpdateMasterDTO();
        updateMasterDTO.setId("  ");

        MasterDTO expectedMasterResponse = TestDataGenerator.generateUpdatedMasterDTO();

        webTestClient.put()
                .uri(String.format("%s/update/master/%s", ServiceConstants.API_QUALIFIER, masterId))
                .bodyValue(updateMasterDTO)
                .exchange()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectStatus()
                .isBadRequest()
                .expectBody(ExceptionResponse.class)
                .consumeWith(exceptionResponse -> {
                    System.out.println(exceptionResponse.getResponseBody().getErrorMessage());
                    assertEquals(InputDataException.class.getSimpleName(), exceptionResponse.getResponseBody().getError());
                    assertEquals("Master ID is required, it can't null or empty or whitespaces", exceptionResponse.getResponseBody().getErrorMessage());
                });
    }

    @Test
    void testUpdateMasterDetails_should_throw_InputDataException_for_empty_email() {

        AddMasterDTO requestPayload = TestDataGenerator.generateAddMasterDTO();
        requestPayload.setEmail("newMaster@gmail.com");

        EntityExchangeResult<MasterDTO> addMasterResponse = webTestClient.post()
                .uri(String.format("%s/add", ServiceConstants.API_QUALIFIER))
                .bodyValue(requestPayload)
                .exchange()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectStatus()
                .isCreated()
                .expectBody(MasterDTO.class)
                .returnResult();


        assertNotNull(addMasterResponse);
        assertNotNull(addMasterResponse.getResponseBody().getId());
        assertEquals(requestPayload.getName(), Objects.requireNonNull(addMasterResponse.getResponseBody()).getName());
        assertEquals(requestPayload.getEmail(),Objects.requireNonNull(addMasterResponse.getResponseBody()).getEmail());
        assertEquals(requestPayload.getAge(),addMasterResponse.getResponseBody().getAge());
        assertEquals(requestPayload.getAddress(),Objects.requireNonNull(addMasterResponse.getResponseBody()).getAddress());


        // updating the previously added master with new details
        String masterId = addMasterResponse.getResponseBody().getId();

        UpdateMasterDTO updateMasterDTO = TestDataGenerator.generateUpdateMasterDTO();
        updateMasterDTO.setId(masterId);
        updateMasterDTO.setEmail("");

        MasterDTO expectedMasterResponse = TestDataGenerator.generateUpdatedMasterDTO();

        webTestClient.put()
                .uri(String.format("%s/update/master/%s", ServiceConstants.API_QUALIFIER, masterId))
                .bodyValue(updateMasterDTO)
                .exchange()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectStatus()
                .isBadRequest()
                .expectBody(ExceptionResponse.class)
                .consumeWith(exceptionResponse -> {
                    assertEquals(InputDataException.class.getSimpleName(), exceptionResponse.getResponseBody().getError());
                    assertEquals("Email ID is required, it can't null or empty or whitespaces", exceptionResponse.getResponseBody().getErrorMessage());
                });
    }

    @Test
    void testUpdateMaster_should_throw_InputDataException_for_emailDomain(){

        AddMasterDTO requestPayload = TestDataGenerator.generateAddMasterDTO();
        requestPayload.setEmail("newMaster@gmail.com");

        EntityExchangeResult<MasterDTO> addMasterResponse = webTestClient.post()
                .uri(String.format("%s/add", ServiceConstants.API_QUALIFIER))
                .bodyValue(requestPayload)
                .exchange()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectStatus()
                .isCreated()
                .expectBody(MasterDTO.class)
                .returnResult();


        assertNotNull(addMasterResponse);
        assertNotNull(addMasterResponse.getResponseBody().getId());
        assertEquals(requestPayload.getName(), Objects.requireNonNull(addMasterResponse.getResponseBody()).getName());
        assertEquals(requestPayload.getEmail(),Objects.requireNonNull(addMasterResponse.getResponseBody()).getEmail());
        assertEquals(requestPayload.getAge(),addMasterResponse.getResponseBody().getAge());
        assertEquals(requestPayload.getAddress(),Objects.requireNonNull(addMasterResponse.getResponseBody()).getAddress());


        // updating the previously added master with new details
        String masterId = addMasterResponse.getResponseBody().getId();

        UpdateMasterDTO updateMasterDTO = TestDataGenerator.generateUpdateMasterDTO();
       updateMasterDTO.setId(masterId);
       updateMasterDTO.setEmail("newUser@reddit.com");

       webTestClient.put()
                .uri(String.format("%s/update/master/%s", ServiceConstants.API_QUALIFIER, masterId))
               .bodyValue(updateMasterDTO)
                .exchange()
               .expectHeader()
               .contentType(MediaType.APPLICATION_JSON_VALUE)
               .expectStatus()
                .isBadRequest()
                .expectBody(ExceptionResponse.class)
                .consumeWith(exceptionResponse->{
                    assertEquals(InputDataException.class.getSimpleName(), exceptionResponse.getResponseBody().getError());
                    assertEquals("Email must contain one of these domains [gmail.com, facebook.com, yopmail.com]", exceptionResponse.getResponseBody().getErrorMessage());
                });
    }

    @Test
    void testUpdateMaster_should_throw_InputDataException_for_null_email(){

        AddMasterDTO requestPayload = TestDataGenerator.generateAddMasterDTO();
        requestPayload.setEmail("newMaster@gmail.com");

        EntityExchangeResult<MasterDTO> addMasterResponse = webTestClient.post()
                .uri(String.format("%s/add", ServiceConstants.API_QUALIFIER))
                .bodyValue(requestPayload)
                .exchange()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectStatus()
                .isCreated()
                .expectBody(MasterDTO.class)
                .returnResult();


        assertNotNull(addMasterResponse);
        assertNotNull(addMasterResponse.getResponseBody().getId());
        assertEquals(requestPayload.getName(), Objects.requireNonNull(addMasterResponse.getResponseBody()).getName());
        assertEquals(requestPayload.getEmail(),Objects.requireNonNull(addMasterResponse.getResponseBody()).getEmail());
        assertEquals(requestPayload.getAge(),addMasterResponse.getResponseBody().getAge());
        assertEquals(requestPayload.getAddress(),Objects.requireNonNull(addMasterResponse.getResponseBody()).getAddress());


        // updating the previously added master with new details
        String masterId = addMasterResponse.getResponseBody().getId();

        UpdateMasterDTO updateMasterDTO = TestDataGenerator.generateUpdateMasterDTO();
       updateMasterDTO.setId(masterId);
       updateMasterDTO.setEmail(null);

       webTestClient.put()
                .uri(String.format("%s/update/master/%s", ServiceConstants.API_QUALIFIER, masterId))
               .bodyValue(updateMasterDTO)
                .exchange()
               .expectHeader()
               .contentType(MediaType.APPLICATION_JSON_VALUE)
               .expectStatus()
                .isBadRequest()
                .expectBody(ExceptionResponse.class)
                .consumeWith(exceptionResponse->{
                    assertEquals(InputDataException.class.getSimpleName(), exceptionResponse.getResponseBody().getError());
                    assertEquals("Email ID is required, it can't null or empty or whitespaces", exceptionResponse.getResponseBody().getErrorMessage());
                });
    }

    @Test
    void testUpdateMaster_should_throw_InputDataException_for_passwordLength(){

        AddMasterDTO requestPayload = TestDataGenerator.generateAddMasterDTO();
        requestPayload.setEmail("newMaster@gmail.com");

        EntityExchangeResult<MasterDTO> addMasterResponse = webTestClient.post()
                .uri(String.format("%s/add", ServiceConstants.API_QUALIFIER))
                .bodyValue(requestPayload)
                .exchange()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectStatus()
                .isCreated()
                .expectBody(MasterDTO.class)
                .returnResult();


        assertNotNull(addMasterResponse);
        assertNotNull(addMasterResponse.getResponseBody().getId());
        assertEquals(requestPayload.getName(), Objects.requireNonNull(addMasterResponse.getResponseBody()).getName());
        assertEquals(requestPayload.getEmail(),Objects.requireNonNull(addMasterResponse.getResponseBody()).getEmail());
        assertEquals(requestPayload.getAge(),addMasterResponse.getResponseBody().getAge());
        assertEquals(requestPayload.getAddress(),Objects.requireNonNull(addMasterResponse.getResponseBody()).getAddress());


        // updating the previously added master with new details
        String masterId = addMasterResponse.getResponseBody().getId();

        UpdateMasterDTO updateMasterDTO = TestDataGenerator.generateUpdateMasterDTO();
       updateMasterDTO.setId(masterId);
       updateMasterDTO.setPassword("news");

       webTestClient.put()
                .uri(String.format("%s/update/master/%s", ServiceConstants.API_QUALIFIER, masterId))
               .bodyValue(updateMasterDTO)
                .exchange()
               .expectHeader()
               .contentType(MediaType.APPLICATION_JSON_VALUE)
               .expectStatus()
                .isBadRequest()
                .expectBody(ExceptionResponse.class)
                .consumeWith(exceptionResponse->{
                    assertEquals(InputDataException.class.getSimpleName(), exceptionResponse.getResponseBody().getError());
                    assertEquals(String.format("Password should be atleast %s characters long", ValidationUtils.passwordLength), exceptionResponse.getResponseBody().getErrorMessage());
                });
    }
    @Test
    void testUpdateMaster_should_throw_InputDataException_for_age(){

        AddMasterDTO requestPayload = TestDataGenerator.generateAddMasterDTO();
        requestPayload.setEmail("newMaster@gmail.com");

        EntityExchangeResult<MasterDTO> addMasterResponse = webTestClient.post()
                .uri(String.format("%s/add", ServiceConstants.API_QUALIFIER))
                .bodyValue(requestPayload)
                .exchange()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectStatus()
                .isCreated()
                .expectBody(MasterDTO.class)
                .returnResult();


        assertNotNull(addMasterResponse);
        assertNotNull(addMasterResponse.getResponseBody().getId());
        assertEquals(requestPayload.getName(), Objects.requireNonNull(addMasterResponse.getResponseBody()).getName());
        assertEquals(requestPayload.getEmail(),Objects.requireNonNull(addMasterResponse.getResponseBody()).getEmail());
        assertEquals(requestPayload.getAge(),addMasterResponse.getResponseBody().getAge());
        assertEquals(requestPayload.getAddress(),Objects.requireNonNull(addMasterResponse.getResponseBody()).getAddress());


        // updating the previously added master with new details
        String masterId = addMasterResponse.getResponseBody().getId();

        UpdateMasterDTO updateMasterDTO = TestDataGenerator.generateUpdateMasterDTO();
       updateMasterDTO.setId(masterId);
       updateMasterDTO.setAge(-1);

       webTestClient.put()
                .uri(String.format("%s/update/master/%s", ServiceConstants.API_QUALIFIER, masterId))
               .bodyValue(updateMasterDTO)
                .exchange()
               .expectHeader()
               .contentType(MediaType.APPLICATION_JSON_VALUE)
               .expectStatus()
                .isBadRequest()
                .expectBody(ExceptionResponse.class)
                .consumeWith(exceptionResponse->{
                    assertEquals(InputDataException.class.getSimpleName(), exceptionResponse.getResponseBody().getError());
                    assertEquals(String.format("Please provide a Valid age should be atleast %s years old",ValidationUtils.ageThreshold), exceptionResponse.getResponseBody().getErrorMessage());
                });
    }

    @Test
    void testUpdateMaster_should_throw_InputDataException_for_address(){

        AddMasterDTO requestPayload = TestDataGenerator.generateAddMasterDTO();
        requestPayload.setEmail("newMaster@gmail.com");

        EntityExchangeResult<MasterDTO> addMasterResponse = webTestClient.post()
                .uri(String.format("%s/add", ServiceConstants.API_QUALIFIER))
                .bodyValue(requestPayload)
                .exchange()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectStatus()
                .isCreated()
                .expectBody(MasterDTO.class)
                .returnResult();


        assertNotNull(addMasterResponse);
        assertNotNull(addMasterResponse.getResponseBody().getId());
        assertEquals(requestPayload.getName(), Objects.requireNonNull(addMasterResponse.getResponseBody()).getName());
        assertEquals(requestPayload.getEmail(),Objects.requireNonNull(addMasterResponse.getResponseBody()).getEmail());
        assertEquals(requestPayload.getAge(),addMasterResponse.getResponseBody().getAge());
        assertEquals(requestPayload.getAddress(),Objects.requireNonNull(addMasterResponse.getResponseBody()).getAddress());


        // updating the previously added master with new details
        String masterId = addMasterResponse.getResponseBody().getId();

        UpdateMasterDTO updateMasterDTO = TestDataGenerator.generateUpdateMasterDTO();
       updateMasterDTO.setId(masterId);
       updateMasterDTO.setAddress(null);

       webTestClient.put()
                .uri(String.format("%s/update/master/%s", ServiceConstants.API_QUALIFIER, masterId))
               .bodyValue(updateMasterDTO)
                .exchange()
               .expectHeader()
               .contentType(MediaType.APPLICATION_JSON_VALUE)
               .expectStatus()
                .isBadRequest()
                .expectBody(ExceptionResponse.class)
                .consumeWith(exceptionResponse->{
                    assertEquals(InputDataException.class.getSimpleName(), exceptionResponse.getResponseBody().getError());
                    assertEquals("address is required, it can't null or empty or whitespaces", exceptionResponse.getResponseBody().getErrorMessage());
                });
    }


}
