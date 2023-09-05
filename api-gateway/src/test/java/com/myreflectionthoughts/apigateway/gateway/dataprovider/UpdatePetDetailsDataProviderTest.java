package com.myreflectionthoughts.apigateway.gateway.dataprovider;

import com.myreflectionthoughts.apigateway.core.constant.ServiceConstant;
import com.myreflectionthoughts.apigateway.gateway.dataprovider.UpdatePetDetailsDataProvider;
import com.myreflectionthoughts.library.dto.request.UpdatePetDTO;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UpdatePetDetailsDataProviderTest {

    private final UpdatePetDetailsDataProvider updatePetDetailsDataProvider;
    private final WebClient masterServiceClient;
    private final WebClient petServiceClient;

    @Mock
    WebClient.RequestBodyUriSpec requestBodyUriSpec;

    @Mock
    WebClient.RequestBodySpec requestBodySpec;

    @Mock
    WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    WebClient.ResponseSpec responseSpec;

    public UpdatePetDetailsDataProviderTest() {
        this.masterServiceClient = mock(WebClient.class, ServiceConstant.masterServiceQualifier);
        this.petServiceClient = mock(WebClient.class, ServiceConstant.petServiceQualifier);
        updatePetDetailsDataProvider = new UpdatePetDetailsDataProvider(masterServiceClient,petServiceClient);
    }

    @Test
    void testUpdateInfo() {

        UpdatePetDTO updatePetDTO = getUpdatePetDTO();
        PetDTO expectedUpdatedPetDTO = getUpdatedPetDTO();

        when(petServiceClient.put()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.bodyValue(any(UpdatePetDTO.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(eq(PetDTO.class))).thenReturn(Mono.fromSupplier(() -> expectedUpdatedPetDTO));

        Mono<PetDTO> actualUpdatedPetDTOMono = updatePetDetailsDataProvider.updateInfo(Mono.fromSupplier(() -> updatePetDTO));

        StepVerifier.create(actualUpdatedPetDTOMono).consumeNextWith(actualUpdatedPetDTO -> {
            assertEquals(expectedUpdatedPetDTO, actualUpdatedPetDTO);
        }).verifyComplete();

        verify(petServiceClient, times(1)).put();
        verify(requestBodyUriSpec, times(1)).uri(anyString());
        verify(requestBodySpec, times(1)).bodyValue(any(UpdatePetDTO.class));
        verify(requestHeadersSpec, times(1)).retrieve();
        verify(responseSpec, times(1)).bodyToMono(eq(PetDTO.class));
    }


    private UpdatePetDTO getUpdatePetDTO() {
        UpdatePetDTO updatePetDTO = new UpdatePetDTO();
        updatePetDTO.setMaster(ServiceConstant.VALID_MASTER_ID);
        updatePetDTO.setId(ServiceConstant.DUMMY_PET_ID);
        updatePetDTO.setAge(1.0);
        updatePetDTO.setCategory("DOG");
        updatePetDTO.setGender("FEMALE");
        updatePetDTO.setName("pet-name-updated");
        updatePetDTO.setClinicCardStatus("UNDER_PRogrESS");
        return updatePetDTO;
    }

    private PetDTO getUpdatedPetDTO() {
        PetDTO updatedPetDTO = new PetDTO();
        updatedPetDTO.setMaster(ServiceConstant.VALID_MASTER_ID);
        updatedPetDTO.setId(ServiceConstant.DUMMY_PET_ID);
        updatedPetDTO.setAge(1.0);
        updatedPetDTO.setCategory("DOG");
        updatedPetDTO.setGender("FEMALE");
        updatedPetDTO.setName("pet-name-updated");
        updatedPetDTO.setClinicCardStatus("UNDER_PROGRESS");
        updatedPetDTO.setClinicCardStatusMessage("The information has been collected, card making is under progress");
        return updatedPetDTO;
    }
}

