package com.myreflectionthoughts.apipetdetails.gateway.endtoend;

import com.myreflectionthoughts.apipetdetails.gateway.dataprovider.TestDataGenerator;
import com.myreflectionthoughts.library.dto.logs.LoggerUtility;
import com.myreflectionthoughts.library.dto.request.AddPetDTO;
import com.myreflectionthoughts.library.dto.response.ExceptionResponse;
import com.myreflectionthoughts.library.exception.InputDataException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AddPetBeanValidationTest extends TestSetup{

    public AddPetBeanValidationTest(LoggerUtility loggerUtility) {
        super(loggerUtility);
    }

    @Test
    void testAddPetToDatabase_should_throw_InputDataException_empty_name(){

        AddPetDTO requestPayload = TestDataGenerator.getAddPetDTO();
        requestPayload.setName("");

        // Adding a pet to the database
        petWebClient.post()
                .uri(String.format("%s/add", baseURL))
                .bodyValue(requestPayload)
                .exchange()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectStatus()
                .isBadRequest()
                .expectBody(ExceptionResponse.class)
                .consumeWith(exceptionResponse->{
                    assertEquals(InputDataException.class.getSimpleName(), exceptionResponse.getResponseBody().getError());
                    assertEquals("Pet name is required, it can't null or empty or whitespaces", exceptionResponse.getResponseBody().getErrorMessage());
                });
    }
    @Test
    void testAddPetToDatabase_should_throw_InputDataException_null_name(){

        AddPetDTO requestPayload = TestDataGenerator.getAddPetDTO();
        requestPayload.setName(null);

        // Adding a pet to the database
        petWebClient.post()
                .uri(String.format("%s/add", baseURL))
                .bodyValue(requestPayload)
                .exchange()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectStatus()
                .isBadRequest()
                .expectBody(ExceptionResponse.class)
                .consumeWith(exceptionResponse->{
                    assertEquals(InputDataException.class.getSimpleName(), exceptionResponse.getResponseBody().getError());
                    assertEquals("Pet name is required, it can't null or empty or whitespaces", exceptionResponse.getResponseBody().getErrorMessage());
                });
    }
    @Test
    void testAddPetToDatabase_should_throw_InputDataException_empty_masterId(){

        AddPetDTO requestPayload = TestDataGenerator.getAddPetDTO();
        requestPayload.setMaster("");

        // Adding a pet to the database
        petWebClient.post()
                .uri(String.format("%s/add", baseURL))
                .bodyValue(requestPayload)
                .exchange()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectStatus()
                .isBadRequest()
                .expectBody(ExceptionResponse.class)
                .consumeWith(exceptionResponse->{
                    assertEquals(InputDataException.class.getSimpleName(), exceptionResponse.getResponseBody().getError());
                    assertEquals("Master ID is required, it can't null or empty or whitespaces", exceptionResponse.getResponseBody().getErrorMessage());
                });
    }
    @Test
    void testAddPetToDatabase_should_throw_InputDataException_empty_category(){

        AddPetDTO requestPayload = TestDataGenerator.getAddPetDTO();
        requestPayload.setCategory("");

        // Adding a pet to the database
        petWebClient.post()
                .uri(String.format("%s/add", baseURL))
                .bodyValue(requestPayload)
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

        AddPetDTO requestPayload = TestDataGenerator.getAddPetDTO();
        requestPayload.setGender("");

        // Adding a pet to the database
        petWebClient.post()
                .uri(String.format("%s/add", baseURL))
                .bodyValue(requestPayload)
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
    void testAddPetToDatabase_should_throw_InputDataException_forAge(){

        AddPetDTO requestPayload = TestDataGenerator.getAddPetDTO();
        requestPayload.setAge(0);

        // Adding a pet to the database
        petWebClient.post()
                .uri(String.format("%s/add", baseURL))
                .bodyValue(requestPayload)
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
