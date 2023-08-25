package com.myreflectionthoughts.apimasterdetails.core.usecase;

import com.myreflectionthoughts.apimasterdetails.core.constant.ServiceConstants;
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

        UpdateMasterDTO updateMasterPayload = generateUpdateMasterDTO();
        MasterDTO updatedMasterDTO = generateUpdatedMasterDTO();

        when(iUpdate.updateInfo(any(Mono.class))).thenReturn(Mono.just(updatedMasterDTO));

        Mono<MasterDTO> updateMasterResponseMono = updateMasterDetailsUseCase.updateMasterDetails(Mono.just(updateMasterPayload));

        StepVerifier.create(updateMasterResponseMono).consumeNextWith(updateMasterResponse->{
            assertEquals(updatedMasterDTO, updateMasterResponse);
        }).verifyComplete();

        verify(iUpdate,times(1)).updateInfo(any(Mono.class));
    }

    private UpdateMasterDTO generateUpdateMasterDTO(){
        UpdateMasterDTO updateMasterDTO = new UpdateMasterDTO();
        updateMasterDTO.setId(ServiceConstants.VALID_MASTER_ID);
        updateMasterDTO.setName(ServiceConstants.VALID_MASTER_NAME+"-updated");
        updateMasterDTO.setEmail(ServiceConstants.VALID_MASTER_EMAIL+"-updated");
        updateMasterDTO.setAddress(ServiceConstants.VALID_MASTER_ADDRESS+"-updated");
        updateMasterDTO.setAge(ServiceConstants.VALID_MASTER_AGE);
        updateMasterDTO.setPassword(ServiceConstants.VALID_MASTER_PASSWORD+"-updated");
        return updateMasterDTO;
    }

    private MasterDTO generateUpdatedMasterDTO(){
        MasterDTO updatedMasterDTO = new MasterDTO();
        updatedMasterDTO.setId(ServiceConstants.VALID_MASTER_ID);
        updatedMasterDTO.setName(ServiceConstants.VALID_MASTER_NAME+"-updated");
        updatedMasterDTO.setEmail(ServiceConstants.VALID_MASTER_EMAIL+"-updated");
        updatedMasterDTO.setAddress(ServiceConstants.VALID_MASTER_ADDRESS+"-updated");
        updatedMasterDTO.setAge(ServiceConstants.VALID_MASTER_AGE);
        return updatedMasterDTO;
    }

}