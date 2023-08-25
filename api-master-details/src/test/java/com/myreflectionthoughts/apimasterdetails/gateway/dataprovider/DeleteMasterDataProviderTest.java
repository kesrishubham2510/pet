package com.myreflectionthoughts.apimasterdetails.gateway.dataprovider;

import com.myreflectionthoughts.apimasterdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apimasterdetails.core.entity.Master;
import com.myreflectionthoughts.apimasterdetails.core.exception.MasterNotFoundException;
import com.myreflectionthoughts.apimasterdetails.gateway.dataprovider.repository.MasterRepository;
import com.myreflectionthoughts.apimasterdetails.gateway.dataprovider.utility.MappingUtility;
import com.myreflectionthoughts.library.dto.response.DeleteMasterDTO;
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
public class DeleteMasterDataProviderTest {

    @InjectMocks
    private DeleteMasterDataProvider deleteMasterDataProvider;
    @Mock
    private MasterRepository masterRepository;

    @Mock
    private MappingUtility mappingUtility;

    @Test
    void testDeleteMaster(){

        String masterId = ServiceConstants.VALID_MASTER_ID;

        Master expectedMaster = TestDataGenerator.generateMaster();
        Master expectedMasterMarkedForDeletion = TestDataGenerator.generateMaster();
        expectedMasterMarkedForDeletion.setMarkForDelete(true);

        when(masterRepository.findById(anyString())).thenReturn(Mono.just(expectedMaster));
        when(masterRepository.save(any(Master.class))).thenReturn(Mono.just(expectedMasterMarkedForDeletion));

        Mono<DeleteMasterDTO> deleteMasterResponseMono = deleteMasterDataProvider.delete(Mono.just(masterId));

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

        StepVerifier.create(deleteMasterDataProvider.delete(Mono.just(masterId))).expectError(MasterNotFoundException.class).verify();

        verify(masterRepository,times(1)).findById(anyString());
        verify(masterRepository,times(0)).save(any(Master.class));
    }
}