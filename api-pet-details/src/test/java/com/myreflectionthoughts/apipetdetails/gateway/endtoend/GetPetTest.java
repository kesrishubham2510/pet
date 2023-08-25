package com.myreflectionthoughts.apipetdetails.gateway.endtoend;

import com.myreflectionthoughts.apipetdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apipetdetails.core.entity.Pet;
import com.myreflectionthoughts.apipetdetails.core.exception.PetNotFoundException;
import com.myreflectionthoughts.apipetdetails.gateway.dataprovider.TestDataGenerator;
import com.myreflectionthoughts.library.dto.response.ExceptionResponse;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class GetPetTest extends TestSetup{

    @BeforeEach
    public void TestDataSetUp(){
        Consumer<Pet> petConsumer = document-> log.info("Document inserted:- "+document);
        petRepository.saveAll(TestDataGenerator.generateDummyPets()).subscribe(petConsumer);
    }


    /**
     *  When a request is made to the /api-pet-details/get/pet/{petId} then the controller should
        handle the request.
     *  This should to lead to return of a Pet Document with the id specified in the
        request-path-param
     *  The status of the response should be 200, when the request gets successfully processed
     *  The body of the response should contain the data of the pet having the specified id

     */
    @Test
    void testGetPet(){

        Pet expectedPet = TestDataGenerator.generatePetWithDummyMongoDB_ID();

        petWebClient.get()
                    .uri(String.format("%s/get/pet/%s",baseURL,expectedPet.getId()))
                    .exchange()
                    .expectStatus()
                    .isOk()
                    .expectBody(PetDTO.class)
                    .consumeWith(receivedPetResponse->{
                        assertEquals(expectedPet.getName(), Objects.requireNonNull(receivedPetResponse.getResponseBody()).getName());
                        assertEquals(expectedPet.getAge(), Objects.requireNonNull(receivedPetResponse.getResponseBody()).getAge());
                        assertEquals(expectedPet.getCategory().toString(), Objects.requireNonNull(receivedPetResponse.getResponseBody()).getCategory().toUpperCase());
                        assertEquals(expectedPet.getMaster(), Objects.requireNonNull(receivedPetResponse.getResponseBody()).getMaster());
                        assertEquals(expectedPet.getGender().toString(), Objects.requireNonNull(receivedPetResponse.getResponseBody()).getGender().toUpperCase());
                        assertEquals(expectedPet.getId(), Objects.requireNonNull(receivedPetResponse.getResponseBody()).getId());
                        assertEquals(expectedPet.getClinicCardStatus().toString(), Objects.requireNonNull(receivedPetResponse.getResponseBody()).getClinicCardStatus().toUpperCase());
                        assertEquals(expectedPet.getClinicCardStatus().getMessage(), Objects.requireNonNull(receivedPetResponse.getResponseBody()).getClinicCardStatusMessage());
                    });

    }

    /**
     *  When a request is made to the /api-pet-details/get/pet/{petId} then the controller PetController
     should handle the request.
     *  When the request is sent with id that does not exist, an exception response should be
     returned containing the exceptionMessage and exception genre with bad-request(400)
     status code

     */

    @Test
    void testGetPet_Throws_PetNotFoundException(){

        petWebClient.get()
                    .uri(String.format("%s/get/pet/%s",baseURL,ServiceConstants.DUMMY_PET_ID))
                    .exchange()
                    .expectStatus()
                    .isBadRequest()
                    .expectBody(ExceptionResponse.class)
                    .consumeWith(receivedExceptionResponse->{
                        assertEquals(PetNotFoundException.class.getSimpleName(), Objects.requireNonNull(receivedExceptionResponse.getResponseBody()).getError());
                        assertEquals(ServiceConstants.PET_NOT_FOUND_EXCEPTION, Objects.requireNonNull(receivedExceptionResponse.getResponseBody()).getErrorMessage());
                    });

    }
}
