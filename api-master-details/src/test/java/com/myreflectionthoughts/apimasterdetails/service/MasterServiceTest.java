package com.myreflectionthoughts.apimasterdetails.service;

import com.myreflectionthoughts.apimasterdetails.data.TestDataGenerator;
import com.myreflectionthoughts.apimasterdetails.entity.Master;
import com.myreflectionthoughts.apimasterdetails.repository.MasterRepository;
import com.myreflectionthoughts.apimasterdetails.utility.MappingUtility;
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
public class MasterServiceTest {

    @InjectMocks
    private MasterService masterService;

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

        Mono<MasterDTO> actualMasterDTOMono = masterService.add(Mono.just(addMasterDTO));

        StepVerifier.create(actualMasterDTOMono).consumeNextWith(actualMasterDTO -> {
            assertEquals(expectedMasterDTO, actualMasterDTO);
        }).verifyComplete();

        verify(mappingUtility, times(1)).mapToMaster(any(AddMasterDTO.class));
        verify(masterRepository, times(1)).save(any(Master.class));
        verify(mappingUtility, times(1)).mapToMasterDTO(any(Master.class));
    }
}
