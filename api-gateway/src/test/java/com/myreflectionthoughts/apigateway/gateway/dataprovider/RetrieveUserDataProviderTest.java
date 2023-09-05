package com.myreflectionthoughts.apigateway.gateway.dataprovider;

import com.myreflectionthoughts.apigateway.core.constant.ServiceConstant;
import com.myreflectionthoughts.apigateway.gateway.dataprovider.RetrieveUserDataProvider;
import com.myreflectionthoughts.library.dto.response.MasterDTO;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import com.myreflectionthoughts.library.dto.response.UserDTO;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
public class RetrieveUserDataProviderTest {

    private final RetrieveUserDataProvider retrieveUserDataProvider;

    private final WebClient masterServiceClient;
    private final WebClient petServiceClient;

    @Mock
    WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    WebClient.ResponseSpec responseSpec;


    public RetrieveUserDataProviderTest() {
        this.masterServiceClient = mock(WebClient.class, ServiceConstant.masterServiceQualifier);
        this.petServiceClient =    mock(WebClient.class, ServiceConstant.petServiceQualifier);
        this.retrieveUserDataProvider = new RetrieveUserDataProvider(masterServiceClient, petServiceClient);
    }

    @Test
    void testGetInfo(){

        String masterId = ServiceConstant.VALID_MASTER_ID;
        PetDTO expectedPetDTO = generatePetDTO();
        MasterDTO expectedMasterDTO = generateMasterDTO();

        when(masterServiceClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(eq(MasterDTO.class))).thenReturn(Mono.fromSupplier(()->expectedMasterDTO));

        when(petServiceClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToFlux(eq(PetDTO.class))).thenReturn(Flux.just(expectedPetDTO));

        Mono<UserDTO> actualUserInfoMono = retrieveUserDataProvider.getInfo(Mono.fromSupplier(()-> masterId));

        StepVerifier.create(actualUserInfoMono).consumeNextWith(actualUserInfo->{
            assertEquals(expectedMasterDTO, actualUserInfo.getMaster());
            assertEquals(expectedPetDTO, actualUserInfo.getPets().get(0));
        }).verifyComplete();

        verify(masterServiceClient,times(1)).get();
        verify(requestHeadersUriSpec,times(2)).uri(anyString());
        verify(requestHeadersSpec,times(2)).retrieve();
        verify(responseSpec,times(1)).bodyToMono(eq(MasterDTO.class));

        verify(petServiceClient,times(1)).get();
        verify(responseSpec,times(1)).bodyToFlux(eq(PetDTO.class));
    }

    private MasterDTO generateMasterDTO(){
        MasterDTO masterDTO = new MasterDTO();
        masterDTO.setId(ServiceConstant.VALID_MASTER_ID);
        masterDTO.setName(ServiceConstant.VALID_MASTER_NAME);
        masterDTO.setEmail(ServiceConstant.VALID_MASTER_EMAIL);
        masterDTO.setAddress(ServiceConstant.VALID_MASTER_ADDRESS);
        masterDTO.setAge(ServiceConstant.VALID_MASTER_AGE);
        return masterDTO;
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
