package com.myreflectionthoughts.apimasterdetails.core.usecase;

import com.myreflectionthoughts.apimasterdetails.configuration.TestDataGenerator;
import com.myreflectionthoughts.library.contract.IUpdate;
import com.myreflectionthoughts.library.dto.request.UpdateMasterDTO;
import com.myreflectionthoughts.library.dto.response.MasterDTO;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UpdateMasterDetailsUseCaseTest {

    @InjectMocks
    private UpdateMasterDetailsUseCase updateMasterDetailsUseCase;

    @Mock
    private IUpdate<MasterDTO, UpdateMasterDTO> iUpdate;

    @Test
    void testUpdateMasterDetails(){

        UpdateMasterDTO updateMasterPayload = TestDataGenerator.generateUpdateMasterDTO();
        MasterDTO updatedMasterDTO = TestDataGenerator.generateUpdatedMasterDTO();

        when(iUpdate.updateInfo(any(Mono.class))).thenReturn(Mono.just(updatedMasterDTO));

        Mono<MasterDTO> updateMasterResponseMono = updateMasterDetailsUseCase.updateMasterDetails(Mono.just(updateMasterPayload));

        StepVerifier.create(updateMasterResponseMono).consumeNextWith(updateMasterResponse->{
            assertEquals(updatedMasterDTO, updateMasterResponse);
        }).verifyComplete();

        verify(iUpdate,times(1)).updateInfo(any(Mono.class));
    }
}