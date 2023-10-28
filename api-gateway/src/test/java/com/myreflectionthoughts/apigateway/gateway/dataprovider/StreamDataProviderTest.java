package com.myreflectionthoughts.apigateway.gateway.dataprovider;

import com.myreflectionthoughts.apigateway.core.constant.ServiceConstant;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.slf4j.MDC;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
public class StreamDataProviderTest {

    private final WebClient masterService;
    private final WebClient petService;
    private final StreamDataProvider streamDataProvider;
    private MockedStatic<MDC> mdc;

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

        mdc.when(()->MDC.get(anyString())).thenReturn("traceId");

        when(requestHeadersSpec.header(anyString(),anyString())).thenReturn(requestHeadersSpec);

        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToFlux(eq(PetDTO.class))).thenReturn(Flux.fromStream(()-> Stream.of(generatePetDTO())));

        StepVerifier.create(streamDataProvider.getAll()).expectNextCount(1).verifyComplete();

        verify(petService,times(1)).get();
        verify(requestHeadersUriSpec,times(1)).uri(anyString());
        verify(requestHeadersSpec,times(1)).header(anyString(),anyString());
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

    @BeforeEach
    private void initStaticMDC(){
        mdc = Mockito.mockStatic(MDC.class);
    }
    @AfterEach
    private void closeStatic(){
        mdc.close();
    }
}
