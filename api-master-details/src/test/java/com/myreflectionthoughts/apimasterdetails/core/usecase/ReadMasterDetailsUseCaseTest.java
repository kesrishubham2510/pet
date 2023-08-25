package com.myreflectionthoughts.apimasterdetails.core.usecase;

import com.myreflectionthoughts.apimasterdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.library.contract.IGet;
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
public class ReadMasterDetailsUseCaseTest {

    @InjectMocks
    private ReadMasterDetailsUseCase readMasterDetailsUseCase;

    @Mock
    private IGet<MasterDTO> iGet;

    @Test
    void testReadMasterDetails(){

        String masterId = ServiceConstants.VALID_MASTER_ID;
        MasterDTO expectedMasterDTO = generateMasterDTO();

        when(iGet.getInfo(any(Mono.class))).thenReturn(Mono.just(expectedMasterDTO));
        Mono<MasterDTO> actualMasterDTOResponseMono = readMasterDetailsUseCase.readMasterDetails(Mono.just(masterId));

        StepVerifier.create(actualMasterDTOResponseMono).consumeNextWith(actualMasterDTOResponse->{
            assertEquals(expectedMasterDTO, actualMasterDTOResponse);
        }).verifyComplete();
        verify(iGet,times(1)).getInfo(any(Mono.class));
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