package com.myreflectionthoughts.apimasterdetails.gateway.dataprovider;

import com.myreflectionthoughts.apimasterdetails.configuration.TestDataGenerator;
import com.myreflectionthoughts.apimasterdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apimasterdetails.core.entity.Master;
import com.myreflectionthoughts.apimasterdetails.core.exception.MasterNotFoundException;
import com.myreflectionthoughts.apimasterdetails.gateway.dataprovider.repository.MasterRepository;
import com.myreflectionthoughts.apimasterdetails.gateway.dataprovider.utility.MappingUtility;
import com.myreflectionthoughts.library.dto.response.MasterDTO;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@SpringBootTest
public class MasterDataProviderTest {

    @InjectMocks
    private MasterDataProvider masterDataProvider;
    @Mock
    private MasterRepository masterRepository;

    @Mock
    private MappingUtility mappingUtility;


    @Test
    void testGetMasterInfo() {

        String masterId = ServiceConstants.VALID_MASTER_ID;

        Master expectedMaster = TestDataGenerator.generateMaster();
        MasterDTO expectedMasterDTO = TestDataGenerator.generateMasterDTO();

        when(masterRepository.findById(any(String.class))).thenReturn(Mono.just(expectedMaster));
        when(mappingUtility.mapToMasterDTO(any(Master.class))).thenReturn(expectedMasterDTO);

        Mono<MasterDTO> actualMasterDTOMono = masterDataProvider.getInfo(Mono.just(masterId));

        StepVerifier.create(actualMasterDTOMono).consumeNextWith(actualMasterDTO -> {
            assertEquals(expectedMasterDTO, actualMasterDTO);
        }).verifyComplete();

        verify(masterRepository, times(1)).findById(any(String.class));
        verify(mappingUtility, times(1)).mapToMasterDTO(any(Master.class));
    }

    @Test
    void testGetMasterInfo_Throws_MasterNotFoundException() {
        String masterId = ServiceConstants.VALID_MASTER_ID;

        when(masterRepository.findById(any(String.class))).thenThrow(MasterNotFoundException.class);

        StepVerifier.create(masterDataProvider.getInfo(Mono.just(masterId))).expectError(MasterNotFoundException.class).verify();

        verify(masterRepository, times(1)).findById(any(String.class));
        verify(mappingUtility, times(0)).mapToMasterDTO(any(Master.class));
    }

}