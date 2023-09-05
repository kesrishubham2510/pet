package com.myreflectionthoughts.apigateway.gateway.dataprovider;

import com.myreflectionthoughts.apigateway.core.constant.ServiceConstant;
import com.myreflectionthoughts.apigateway.gateway.dataprovider.RetrievePetsOfUserDataProvider;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
public class RetrievePetsOfUserDataProviderTest {

    private final RetrievePetsOfUserDataProvider retrievePetsOfUserDataProvider;
    private final WebClient masterServiceClient;
    private final WebClient petServiceClient;

    @Mock
    private WebClient.RequestBodyUriSpec requestBodyUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;


    public RetrievePetsOfUserDataProviderTest() {
        this.masterServiceClient = mock(WebClient.class, ServiceConstant.masterServiceQualifier);
        this.petServiceClient = mock(WebClient.class, ServiceConstant.petServiceQualifier);
        this.retrievePetsOfUserDataProvider = new RetrievePetsOfUserDataProvider(masterServiceClient, petServiceClient);
    }

    @Test
    void testGetAllPets(){

        String masterId = ServiceConstant.VALID_MASTER_ID;

        PetDTO expectedPet = generatePetDTO();


        when(petServiceClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToFlux(eq(PetDTO.class))).thenReturn(Flux.just(expectedPet));

        Flux<PetDTO> actualPetsFlux = retrievePetsOfUserDataProvider.retrieveByAttribute(Mono.fromSupplier(()-> masterId));

        StepVerifier.create(actualPetsFlux).expectNextCount(1).verifyComplete();

        verify(petServiceClient,times(1)).get();
        verify(requestHeadersUriSpec,times(1)).uri(anyString());
        verify(requestHeadersSpec,times(1)).retrieve();
        verify(responseSpec,times(1)).bodyToFlux(eq(PetDTO.class));
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
