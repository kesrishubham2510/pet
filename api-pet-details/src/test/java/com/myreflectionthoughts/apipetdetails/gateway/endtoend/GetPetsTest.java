package com.myreflectionthoughts.apipetdetails.gateway.endtoend;

import com.myreflectionthoughts.apipetdetails.core.entity.Pet;
import com.myreflectionthoughts.apipetdetails.gateway.dataprovider.TestDataGenerator;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

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


    //  cleans the DB records inserted/updated
    @AfterEach
    public void cleanUp(){
        Consumer<Void> documentConsumer = x-> log.info("Documents deleted:- ");
        petRepository.deleteAll().subscribe(documentConsumer);
    }
    @Test
    void testGetAllPets(){
        petWebClient.get()
                .uri(String.format("%s/get/all",baseURL))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(PetDTO.class)
                .hasSize(4);
    }
}
