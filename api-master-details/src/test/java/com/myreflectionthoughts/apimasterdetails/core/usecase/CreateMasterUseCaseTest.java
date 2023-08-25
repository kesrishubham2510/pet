package com.myreflectionthoughts.apimasterdetails.core.usecase;

import com.myreflectionthoughts.apimasterdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.library.contract.IAdd;
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
public class CreateMasterUseCaseTest {

    @InjectMocks
    private CreateMasterUseCase createMasterUseCase;
    @Mock
    private IAdd<AddMasterDTO, MasterDTO> iAdd;

    @Test
    void testCreateMaster(){

        AddMasterDTO addMasterDTO = generateAddMasterDTO();
        MasterDTO expectedMasterDTO = generateMasterDTO();

        when(iAdd.add(any(Mono.class))).thenReturn(Mono.just(expectedMasterDTO));
        Mono<MasterDTO> actualResponseMono = createMasterUseCase.createMaster(Mono.just(addMasterDTO));

        StepVerifier.create(actualResponseMono).consumeNextWith(actualResponse->{
            assertEquals(expectedMasterDTO, actualResponse);
        }).verifyComplete();

        verify(iAdd,times(1)).add(any());
    }

    private AddMasterDTO generateAddMasterDTO(){
        AddMasterDTO addMasterDTO = new AddMasterDTO();
        addMasterDTO.setName(ServiceConstants.VALID_MASTER_NAME);
        addMasterDTO.setEmail(ServiceConstants.VALID_MASTER_EMAIL);
        addMasterDTO.setAddress(ServiceConstants.VALID_MASTER_ADDRESS);
        addMasterDTO.setAge(ServiceConstants.VALID_MASTER_AGE);
        addMasterDTO.setPassword(ServiceConstants.VALID_MASTER_PASSWORD);
        return addMasterDTO;
    }

    private MasterDTO generateMasterDTO(){
        MasterDTO masterDTO = new MasterDTO();
        masterDTO.setId(ServiceConstants.VALID_MASTER_ID);
        masterDTO.setName(ServiceConstants.VALID_MASTER_NAME);
        masterDTO.setEmail(ServiceConstants.VALID_MASTER_EMAIL);
        masterDTO.setAddress(ServiceConstants.VALID_MASTER_ADDRESS);
        masterDTO.setAge(ServiceConstants.VALID_MASTER_AGE);
        return masterDTO;
    }

}