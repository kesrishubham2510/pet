package com.myreflectionthoughts.apimasterdetails.core.usecase;

import com.myreflectionthoughts.apimasterdetails.configuration.TestDataGenerator;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ReadMasterDetailsUseCaseTest {

    @InjectMocks
    private ReadMasterDetailsUseCase readMasterDetailsUseCase;

    @Mock
    private IGet<MasterDTO> iGet;

    @Test
    void testReadMasterDetails(){

        String masterId = ServiceConstants.VALID_MASTER_ID;
        MasterDTO expectedMasterDTO = TestDataGenerator.generateMasterDTO();

        when(iGet.getInfo(any(Mono.class))).thenReturn(Mono.just(expectedMasterDTO));
        Mono<MasterDTO> actualMasterDTOResponseMono = readMasterDetailsUseCase.readMasterDetails(Mono.just(masterId));

        StepVerifier.create(actualMasterDTOResponseMono).consumeNextWith(actualMasterDTOResponse->{
            assertEquals(expectedMasterDTO, actualMasterDTOResponse);
        }).verifyComplete();
        verify(iGet,times(1)).getInfo(any(Mono.class));
    }
}