package com.myreflectionthoughts.apimasterdetails.gateway.dataprovider.endtoend;

import com.myreflectionthoughts.apimasterdetails.configuration.TestDataGenerator;
import com.myreflectionthoughts.apimasterdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apimasterdetails.core.entity.Master;
import com.myreflectionthoughts.library.dto.response.MasterDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertFalse;

@Slf4j
public class GetMastersTest extends TestSetup {

    @BeforeEach
    void setUp() {
        Consumer<Master> masterConsumer = (master) -> log.info("Inserted master:- " + master);
        masterRepository.saveAll(TestDataGenerator.generateDummyMasters()).subscribe(masterConsumer);
    }


    @Test
    void testGetMasters() {

        webTestClient.get()
                .uri(String.format("%s/get/all", ServiceConstants.API_QUALIFIER))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(MasterDTO.class)
                .consumeWith(mastersResponse ->{
                    assertFalse(Objects.requireNonNull(mastersResponse.getResponseBody()).isEmpty());
                });
    }
}