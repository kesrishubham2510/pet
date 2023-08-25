package com.myreflectionthoughts.apipetdetails.gateway.endtoend;

import com.myreflectionthoughts.apipetdetails.core.entity.Pet;
import com.myreflectionthoughts.apipetdetails.gateway.dataprovider.TestDataGenerator;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class GetPetsTest extends TestSetup{


    /**
     *  When a request is made to the /api-pet-details/get/pet/all then the controller PetController
     should handle the request.
     *  The response should contain 4 documents

     */

    @BeforeEach
    public void TestDataSetUp(){
        Consumer<Pet> petConsumer = document-> log.info("Document inserted:- "+document);
        petRepository.saveAll(TestDataGenerator.generateDummyPets()).subscribe(petConsumer);
    }

    @Test
    void testGetAllPets(){
        petWebClient.get()
                .uri(String.format("%s/get/all",baseURL))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(PetDTO.class)
                .consumeWith(petsResponse->{
                    assertTrue(Objects.requireNonNull(petsResponse.getResponseBody()).size()>0);
                });
    }
}
