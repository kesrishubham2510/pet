package com.myreflectionthoughts.apimasterdetails.gateway.dataprovider.endtoend;

import com.myreflectionthoughts.apimasterdetails.configuration.TestDataGenerator;
import com.myreflectionthoughts.apimasterdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apimasterdetails.core.entity.Master;
import com.myreflectionthoughts.apimasterdetails.core.exception.MasterNotFoundException;
import com.myreflectionthoughts.library.dto.response.ExceptionResponse;
import com.myreflectionthoughts.library.dto.response.MasterDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public  class GetMasterDetailsTest extends TestSetup {


    @BeforeEach
    void setUp(){
        Consumer<Master> masterConsumer = (master)-> log.info("Inserted master:- "+master);
        masterRepository.saveAll(TestDataGenerator.generateDummyMasters()).subscribe(masterConsumer);
    }


    @Test
    void testGetMasterDetails(){

        Master expectedMaster = TestDataGenerator.generateMasterWithMongoId();

        webTestClient.get()
                .uri(String.format("%s/get/master/%s", ServiceConstants.API_QUALIFIER,ServiceConstants.DUMMY_MONGO_DB_ID))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(MasterDTO.class)
                .consumeWith(masterDetailsResponse->{
                   assertEquals(expectedMaster.getId(), Objects.requireNonNull(masterDetailsResponse.getResponseBody()).getId());
                   assertEquals(expectedMaster.getName(), Objects.requireNonNull(masterDetailsResponse.getResponseBody()).getName());
                   assertEquals(expectedMaster.getEmail(), Objects.requireNonNull(masterDetailsResponse.getResponseBody()).getEmail());
                   assertEquals(expectedMaster.getAddress(), Objects.requireNonNull(masterDetailsResponse.getResponseBody()).getAddress());
                   assertEquals(expectedMaster.getAge(), Objects.requireNonNull(masterDetailsResponse.getResponseBody()).getAge());
                });
    }

    @Test
    void testGetMasterDetails_Throws_MasterNotFoundException(){

        String masterId = "123456";

        webTestClient.get()
                .uri(String.format("%s/get/master/%s", ServiceConstants.API_QUALIFIER,masterId))
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody(ExceptionResponse.class)
                .consumeWith(exceptionResponse->{
                   assertEquals(MasterNotFoundException.class.getSimpleName(), Objects.requireNonNull(exceptionResponse.getResponseBody()).getError());
                   assertEquals(ServiceConstants.MASTER_NOT_FOUND_EXCEPTION, Objects.requireNonNull(exceptionResponse.getResponseBody()).getErrorMessage());
                });
    }


}