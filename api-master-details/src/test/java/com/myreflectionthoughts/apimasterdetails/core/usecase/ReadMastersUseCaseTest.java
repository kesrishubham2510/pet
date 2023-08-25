package com.myreflectionthoughts.apimasterdetails.core.usecase;

import com.myreflectionthoughts.apimasterdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.library.contract.IGetAll;
import com.myreflectionthoughts.library.dto.response.MasterDTO;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@SpringBootTest
public class ReadMastersUseCaseTest {

    @InjectMocks
    private ReadMastersUseCase readMastersUseCase;

    @Mock
    private IGetAll<MasterDTO> iGetAll;

    @Test
    void testReadMasterDetails(){

        MasterDTO expectedMasterDTO = generateMasterDTO();

        when(iGetAll.getAll()).thenReturn(Flux.just(expectedMasterDTO));
        Flux<MasterDTO> actualMasterDTOResponseMono = readMastersUseCase.readMasters();

        StepVerifier.create(actualMasterDTOResponseMono).expectNextCount(1).verifyComplete();
        verify(iGetAll,times(1)).getAll();
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