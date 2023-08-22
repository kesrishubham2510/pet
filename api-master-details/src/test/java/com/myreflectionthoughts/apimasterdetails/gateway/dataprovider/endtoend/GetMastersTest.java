package com.myreflectionthoughts.apimasterdetails.gateway.dataprovider.endtoend;

import com.myreflectionthoughts.apimasterdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.library.dto.response.MasterDTO;
import org.junit.jupiter.api.Test;

public class GetMastersTest extends TestSetup {

    @Test
    void testGetMasters(){

        webTestClient.get()
                .uri(String.format("%s/get/all", ServiceConstants.API_QUALIFIER))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(MasterDTO.class)
                .hasSize(4);
    }
}