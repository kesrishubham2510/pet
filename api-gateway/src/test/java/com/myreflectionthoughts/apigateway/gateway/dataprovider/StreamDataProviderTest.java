package com.myreflectionthoughts.apigateway.gateway.dataprovider;

import com.myreflectionthoughts.apigateway.core.constant.ServiceConstant;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class StreamDataProviderTest {

    private final WebClient masterService;
    private final WebClient petService;
    private final StreamDataProvider streamDataProvider;

    @Mock
    WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    WebClient.ResponseSpec responseSpec;

    public StreamDataProviderTest() {
        this.petService = mock(WebClient.class, ServiceConstant.petServiceQualifier);
        this.masterService = mock(WebClient.class, ServiceConstant.masterServiceQualifier);
        streamDataProvider = new StreamDataProvider(masterService, petService);
    }

    @Test
    void testGetAll(){
        when(petService.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToFlux(eq(PetDTO.class))).thenReturn(Flux.fromStream(()-> Stream.of(generatePetDTO())));

        StepVerifier.create(streamDataProvider.getAll()).expectNextCount(1).verifyComplete();
    }

    private PetDTO generatePetDTO() {
        PetDTO pet = new PetDTO();
        pet.setMaster(ServiceConstant.VALID_MASTER_ID);
        pet.setId(ServiceConstant.DUMMY_PET_ID);
        pet.setAge(1.0);
        pet.setCategory("DOG");
        pet.setGender("FEMALE");
        pet.setName("pet-name");
        pet.setClinicCardStatus("NOT_APPLIED");
        return pet;
    }
}
