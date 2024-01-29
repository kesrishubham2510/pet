/*
    BDD test to validate the InputDataExceptionScenarios
 */
package com.myreflectionthoughts.apimasterdetails.gateway.dataprovider.endtoend;

import com.myreflectionthoughts.apimasterdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apimasterdetails.gateway.dataprovider.TestDataGenerator;
import com.myreflectionthoughts.library.dto.request.AddMasterDTO;
import com.myreflectionthoughts.library.dto.response.ExceptionResponse;
import com.myreflectionthoughts.library.exception.InputDataException;
import com.myreflectionthoughts.library.exception.ParameterMissingException;
import com.myreflectionthoughts.library.utils.ValidationUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class AddMasterBeanValidationTest extends TestSetup{

    @Test
    void testAddMaster_should_throw_InputDataException_for_empty_email(){

        AddMasterDTO requestPayload = TestDataGenerator.generateAddMasterDTO();
        requestPayload.setEmail("");

        webTestClient.post()
                .uri(String.format("%s/add", ServiceConstants.API_QUALIFIER))
                .bodyValue(requestPayload)
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody(ExceptionResponse.class)
                .consumeWith(exceptionResponse->{
                    assertEquals(InputDataException.class.getSimpleName(), exceptionResponse.getResponseBody().getError());
                    assertEquals("Email ID is required, it can't null or empty or whitespaces", exceptionResponse.getResponseBody().getErrorMessage());
                });


    }

    @Test
    void testAddMaster_should_throw_InputDataException_for_emailDomain(){

        AddMasterDTO requestPayload = TestDataGenerator.generateAddMasterDTO();
        requestPayload.setEmail("newUser@reddit.com");

        webTestClient.post()
                .uri(String.format("%s/add", ServiceConstants.API_QUALIFIER))
                .bodyValue(requestPayload)
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody(ExceptionResponse.class)
                .consumeWith(exceptionResponse->{
                    assertEquals(InputDataException.class.getSimpleName(), exceptionResponse.getResponseBody().getError());
                    assertEquals("Email must contain one of these domains [gmail.com, facebook.com, yopmail.com]", exceptionResponse.getResponseBody().getErrorMessage());
                });
    }

    @Test
    void testAddMaster_should_throw_ParameterMissingException_for_null_email(){

        AddMasterDTO requestPayload = TestDataGenerator.generateAddMasterDTO();
        requestPayload.setEmail(null);

        webTestClient.post()
                .uri(String.format("%s/add", ServiceConstants.API_QUALIFIER))
                .bodyValue(requestPayload)
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody(ExceptionResponse.class)
                .consumeWith(exceptionResponse->{
                    assertEquals(ParameterMissingException.class.getSimpleName(), exceptionResponse.getResponseBody().getError());
                    assertEquals("Email ID is required, it can't be null", exceptionResponse.getResponseBody().getErrorMessage());
                });


    }

    @Test
    void testAddMaster_should_throw_InputDataException_for_passwordLength(){

        AddMasterDTO requestPayload = TestDataGenerator.generateAddMasterDTO();
        requestPayload.setPassword("news");

        webTestClient.post()
                .uri(String.format("%s/add", ServiceConstants.API_QUALIFIER))
                .bodyValue(requestPayload)
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody(ExceptionResponse.class)
                .consumeWith(exceptionResponse->{
                    assertEquals(InputDataException.class.getSimpleName(), exceptionResponse.getResponseBody().getError());
                    assertEquals(String.format("Password should be atleast %s characters long", ValidationUtils.passwordLength), exceptionResponse.getResponseBody().getErrorMessage());
                });
    }
    @Test
    void testAddMaster_should_throw_InputDataException_for_age(){

        AddMasterDTO requestPayload = TestDataGenerator.generateAddMasterDTO();
        requestPayload.setAge(-1);

        webTestClient.post()
                .uri(String.format("%s/add", ServiceConstants.API_QUALIFIER))
                .bodyValue(requestPayload)
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody(ExceptionResponse.class)
                .consumeWith(exceptionResponse->{
                    assertEquals(InputDataException.class.getSimpleName(), exceptionResponse.getResponseBody().getError());
                    assertEquals("Age can't be zero or negative, Please provide a valid age", exceptionResponse.getResponseBody().getErrorMessage());
                });
    }

    @Test
    void testAddMaster_should_throw_InputDataException_for_address(){

        AddMasterDTO requestPayload = TestDataGenerator.generateAddMasterDTO();
        requestPayload.setAddress("");

        webTestClient.post()
                .uri(String.format("%s/add", ServiceConstants.API_QUALIFIER))
                .bodyValue(requestPayload)
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody(ExceptionResponse.class)
                .consumeWith(exceptionResponse->{
                    assertEquals(InputDataException.class.getSimpleName(), exceptionResponse.getResponseBody().getError());
                    assertEquals("address is required, it can't null or empty or whitespaces", exceptionResponse.getResponseBody().getErrorMessage());
                });
    }

}
