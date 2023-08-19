package com.myreflectionthoughts.apimasterdetails.gateway.dataprovider;

import com.myreflectionthoughts.apimasterdetails.configuration.TestDataGenerator;
import com.myreflectionthoughts.apimasterdetails.core.entity.Master;
import com.myreflectionthoughts.apimasterdetails.core.exception.MasterNotFoundException;
import com.myreflectionthoughts.apimasterdetails.gateway.dataprovider.repository.MasterRepository;
import com.myreflectionthoughts.apimasterdetails.gateway.dataprovider.utility.MappingUtility;
import com.myreflectionthoughts.library.dto.request.UpdateMasterDTO;
import com.myreflectionthoughts.library.dto.response.MasterDTO;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@SpringBootTest
public class UpdateMasterDataProviderTest {

    @InjectMocks
    private UpdateMasterDataProvider updateMasterDataProvider;
    @Mock
    private MasterRepository masterRepository;

    @Mock
    private MappingUtility mappingUtility;

    @Test
    void testUpdateMaster() {

        UpdateMasterDTO updateMasterDTO = TestDataGenerator.generateUpdateMasterDTO();
        Master expectedUpdatedMaster = TestDataGenerator.generateUpdatedMaster();
        MasterDTO expectedUpdatedMasterDTO = TestDataGenerator.generateUpdatedMasterDTO();

        when(masterRepository.existsById(anyString())).thenReturn(Mono.just((true)));
        when(mappingUtility.mapToMaster(any(UpdateMasterDTO.class))).thenReturn(expectedUpdatedMaster);
        when(masterRepository.save(any(Master.class))).thenReturn(Mono.just(expectedUpdatedMaster));
        when(mappingUtility.mapToMasterDTO(any(Master.class))).thenReturn(expectedUpdatedMasterDTO);

        Mono<MasterDTO> actualUpdateInfoResponseMono = updateMasterDataProvider.updateInfo(Mono.just(updateMasterDTO));

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

        StepVerifier.create(updateMasterDataProvider.updateInfo(Mono.just(updateMasterDTO)))
                .expectError(MasterNotFoundException.class).verify();

        verify(masterRepository, times(1)).existsById(anyString());
        verify(mappingUtility, times(0)).mapToMaster(any(UpdateMasterDTO.class));
        verify(masterRepository, times(0)).save(any(Master.class));
        verify(mappingUtility, times(0)).mapToMasterDTO(any(Master.class));
    }


}