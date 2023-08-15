package com.myreflectionthoughts.apimasterdetails.service;

import com.myreflectionthoughts.apimasterdetails.constant.ServiceConstants;
import com.myreflectionthoughts.apimasterdetails.data.TestDataGenerator;
import com.myreflectionthoughts.apimasterdetails.entity.Master;
import com.myreflectionthoughts.apimasterdetails.exception.MasterNotFoundException;
import com.myreflectionthoughts.apimasterdetails.repository.MasterRepository;
import com.myreflectionthoughts.apimasterdetails.utility.MappingUtility;
import com.myreflectionthoughts.library.dto.request.AddMasterDTO;
import com.myreflectionthoughts.library.dto.request.UpdateMasterDTO;
import com.myreflectionthoughts.library.dto.response.DeleteMasterDTO;
import com.myreflectionthoughts.library.dto.response.MasterDTO;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
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

    @Test
    void testGetMasterInfo() {

        String masterId = ServiceConstants.VALID_MASTER_ID;

        Master expectedMaster = TestDataGenerator.generateMaster();
        MasterDTO expectedMasterDTO = TestDataGenerator.generateMasterDTO();

        when(masterRepository.findById(any(String.class))).thenReturn(Mono.just(expectedMaster));
        when(mappingUtility.mapToMasterDTO(any(Master.class))).thenReturn(expectedMasterDTO);

        Mono<MasterDTO> actualMasterDTOMono = masterService.getInfo(Mono.just(masterId));

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

        StepVerifier.create(masterService.getInfo(Mono.just(masterId))).expectError(MasterNotFoundException.class).verify();

        verify(masterRepository, times(1)).findById(any(String.class));
        verify(mappingUtility, times(0)).mapToMasterDTO(any(Master.class));
    }

    @Test
    void testGetAllMasters() {

        Master expectedMaster = TestDataGenerator.generateMaster();
        MasterDTO expectedMasterDTO = TestDataGenerator.generateMasterDTO();

        when(masterRepository.findAll()).thenReturn(Flux.just(expectedMaster));
        when(mappingUtility.mapToMasterDTO(any(Master.class))).thenReturn(expectedMasterDTO);

        Flux<MasterDTO> actualMasters = masterService.getAll();
        StepVerifier.create(actualMasters).expectNextCount(1).verifyComplete();

        verify(masterRepository, times(1)).findAll();
        verify(mappingUtility, times(1)).mapToMasterDTO(any(Master.class));

    }

    @Test
    void testUpdateMaster() {

        UpdateMasterDTO updateMasterDTO = TestDataGenerator.generateUpdateMasterDTO();
        Master expectedUpdatedMaster = TestDataGenerator.generateUpdatedMaster();
        MasterDTO expectedUpdatedMasterDTO = TestDataGenerator.generateUpdatedMasterDTO();

        when(masterRepository.existsById(anyString())).thenReturn(Mono.just((true)));
        when(mappingUtility.mapToMaster(any(UpdateMasterDTO.class))).thenReturn(expectedUpdatedMaster);
        when(masterRepository.save(any(Master.class))).thenReturn(Mono.just(expectedUpdatedMaster));
        when(mappingUtility.mapToMasterDTO(any(Master.class))).thenReturn(expectedUpdatedMasterDTO);

        Mono<MasterDTO> actualUpdateInfoResponseMono = masterService.updateInfo(Mono.just(updateMasterDTO));

        StepVerifier.create(actualUpdateInfoResponseMono).consumeNextWith(actualUpdateInfoResponse -> {
            assertEquals(expectedUpdatedMasterDTO, actualUpdateInfoResponse);
        }).verifyComplete();

        verify(masterRepository, times(1)).existsById(anyString());
        verify(mappingUtility, times(1)).mapToMaster(any(UpdateMasterDTO.class));
        verify(masterRepository, times(1)).save(any(Master.class));
        verify(mappingUtility, times(1)).mapToMasterDTO(any(Master.class));
    }

    @Test
    void testUpdateMaster_Throws_MasterNotFoundException() {

        UpdateMasterDTO updateMasterDTO = TestDataGenerator.generateUpdateMasterDTO();
        Master expectedUpdatedMaster = TestDataGenerator.generateUpdatedMaster();
        MasterDTO expectedUpdatedMasterDTO = TestDataGenerator.generateUpdatedMasterDTO();

        when(masterRepository.existsById(anyString())).thenReturn(Mono.just((false)));
        when(mappingUtility.mapToMaster(any(UpdateMasterDTO.class))).thenReturn(expectedUpdatedMaster);
        when(masterRepository.save(any(Master.class))).thenReturn(Mono.just(expectedUpdatedMaster));
        when(mappingUtility.mapToMasterDTO(any(Master.class))).thenReturn(expectedUpdatedMasterDTO);

        StepVerifier.create(masterService.updateInfo(Mono.just(updateMasterDTO)))
                .expectError(MasterNotFoundException.class).verify();

        verify(masterRepository, times(1)).existsById(anyString());
        verify(mappingUtility, times(0)).mapToMaster(any(UpdateMasterDTO.class));
        verify(masterRepository, times(0)).save(any(Master.class));
        verify(mappingUtility, times(0)).mapToMasterDTO(any(Master.class));
    }

    @Test
    void testDeleteMaster(){

        String masterId = ServiceConstants.VALID_MASTER_ID;

        Master expectedMaster = TestDataGenerator.generateMaster();
        Master expectedMasterMarkedForDeletion = TestDataGenerator.generateMaster();
        expectedMasterMarkedForDeletion.setMarkForDelete(true);

        when(masterRepository.findById(anyString())).thenReturn(Mono.just(expectedMaster));
        when(masterRepository.save(any(Master.class))).thenReturn(Mono.just(expectedMasterMarkedForDeletion));

        Mono<DeleteMasterDTO> deleteMasterResponseMono = masterService.delete(Mono.just(masterId));

        StepVerifier.create(deleteMasterResponseMono).consumeNextWith(deleteMasterResponse->{
            assertEquals(masterId, deleteMasterResponse.getId());
            assertEquals(String.format(ServiceConstants.MASTER_DELETION_MESSAGE_TEMPLATE,masterId), deleteMasterResponse.getMessage());
        }).verifyComplete();

        verify(masterRepository,times(1)).findById(anyString());
        verify(masterRepository,times(1)).save(any(Master.class));
    }

    @Test
    void testDeleteMaster_Throws_MasterNotFoundException(){

        String masterId = ServiceConstants.VALID_MASTER_ID;

        Master expectedMaster = TestDataGenerator.generateMaster();
        Master expectedMasterMarkedForDeletion = TestDataGenerator.generateMaster();
        expectedMasterMarkedForDeletion.setMarkForDelete(true);

        when(masterRepository.findById(anyString())).thenReturn(Mono.empty());
        when(masterRepository.save(any(Master.class))).thenReturn(Mono.just(expectedMasterMarkedForDeletion));

        StepVerifier.create(masterService.delete(Mono.just(masterId))).expectError(MasterNotFoundException.class).verify();

        verify(masterRepository,times(1)).findById(anyString());
        verify(masterRepository,times(0)).save(any(Master.class));
    }
}
