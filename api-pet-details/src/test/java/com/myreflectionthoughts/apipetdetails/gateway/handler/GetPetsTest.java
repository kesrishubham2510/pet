package com.myreflectionthoughts.apipetdetails.gateway.handler;

import com.myreflectionthoughts.library.dto.response.PetDTO;
import org.junit.jupiter.api.Test;


public class GetPetsTest extends TestSetup{


    /**
     *  When a request is made to the /api-pet-details/get/pet/all then the controller PetController
     should handle the request.
     *  The response should contain 4 documents

     */

    @Test
    void testGetAllPets(){
        petWebClient.get()
                .uri(String.format("/%s/get/all",baseURL))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(PetDTO.class)
                .hasSize(4);
    }
}
