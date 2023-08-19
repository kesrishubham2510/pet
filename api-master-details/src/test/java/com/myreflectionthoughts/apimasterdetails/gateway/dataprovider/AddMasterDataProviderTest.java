package com.myreflectionthoughts.apimasterdetails.gateway.dataprovider;

import com.myreflectionthoughts.apimasterdetails.configuration.TestDataGenerator;
import com.myreflectionthoughts.apimasterdetails.core.entity.Master;
import com.myreflectionthoughts.apimasterdetails.gateway.dataprovider.repository.MasterRepository;
import com.myreflectionthoughts.apimasterdetails.gateway.dataprovider.utility.MappingUtility;
import com.myreflectionthoughts.library.dto.request.AddMasterDTO;
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

@SpringBootTest
public class AddMasterDataProviderTest {

    @InjectMocks
    private AddMasterDataProvider addMasterDataProvider;

    @Mock
    private MasterRepository masterRepository;

    @Mock
    private MappingUtility mappingUtility;

    @Test
    void testAddMaster() {

        AddMasterDTO addMasterDTO = TestDataGenerator.generateAddMasterDTO();
        Master expectedMaster = TestDataGenerator.generateMaster();
        MasterDTO expectedMasterDTO = TestDataGenerator.generateMasterDTO();

        when(mappingUtility.mapToMaster(any(AddMasterDTO.class))).thenReturn(expectedMaster);
        when(masterRepository.save(any(Master.class))).thenReturn(Mono.just(expectedMaster));
        when(mappingUtility.mapToMasterDTO(any(Master.class))).thenReturn(expectedMasterDTO);

        Mono<MasterDTO> actualMasterDTOMono = addMasterDataProvider.add(Mono.just(addMasterDTO));

        StepVerifier.create(actualMasterDTOMono).consumeNextWith(actualMasterDTO -> {
            assertEquals(expectedMasterDTO, actualMasterDTO);
        }).verifyComplete();

        verify(mappingUtility, times(1)).mapToMaster(any(AddMasterDTO.class));
        verify(masterRepository, times(1)).save(any(Master.class));
        verify(mappingUtility, times(1)).mapToMasterDTO(any(Master.class));
    }

}