package com.myreflectionthoughts.apimasterdetails.gateway.dataprovider;

import com.myreflectionthoughts.apimasterdetails.configuration.TestDataGenerator;
import com.myreflectionthoughts.apimasterdetails.core.entity.Master;
import com.myreflectionthoughts.apimasterdetails.gateway.dataprovider.repository.MasterRepository;
import com.myreflectionthoughts.apimasterdetails.gateway.dataprovider.utility.MappingUtility;
import com.myreflectionthoughts.library.dto.response.MasterDTO;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class MastersDataProviderTest {

    @InjectMocks
    private MastersDataProvider mastersDataProvider;
    @Mock
    private MasterRepository masterRepository;

    @Mock
    private MappingUtility mappingUtility;

    @Test
    void testGetAllMasters() {

        Master expectedMaster = TestDataGenerator.generateMaster();
        MasterDTO expectedMasterDTO = TestDataGenerator.generateMasterDTO();

        when(masterRepository.findAll()).thenReturn(Flux.just(expectedMaster));
        when(mappingUtility.mapToMasterDTO(any(Master.class))).thenReturn(expectedMasterDTO);

        Flux<MasterDTO> actualMasters;
        actualMasters = mastersDataProvider.getAll();
        StepVerifier.create(actualMasters).expectNextCount(1).verifyComplete();

        verify(masterRepository, times(1)).findAll();
        verify(mappingUtility, times(1)).mapToMasterDTO(any(Master.class));

    }

}