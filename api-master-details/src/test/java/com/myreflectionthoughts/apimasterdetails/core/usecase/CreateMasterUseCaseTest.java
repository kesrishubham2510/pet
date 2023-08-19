package com.myreflectionthoughts.apimasterdetails.core.usecase;

import com.myreflectionthoughts.apimasterdetails.configuration.TestDataGenerator;
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

        AddMasterDTO addMasterDTO = TestDataGenerator.generateAddMasterDTO();
        MasterDTO expectedMasterDTO = TestDataGenerator.generateMasterDTO();

        when(iAdd.add(any(Mono.class))).thenReturn(Mono.just(expectedMasterDTO));
        Mono<MasterDTO> actualResponseMono = createMasterUseCase.createMaster(Mono.just(addMasterDTO));

        StepVerifier.create(actualResponseMono).consumeNextWith(actualResponse->{
            assertEquals(expectedMasterDTO, actualResponse);
        }).verifyComplete();

        verify(iAdd,times(1)).add(any());
    }
}