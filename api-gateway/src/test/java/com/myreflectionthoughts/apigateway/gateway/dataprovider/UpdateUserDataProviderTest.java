package com.myreflectionthoughts.apigateway.gateway.dataprovider;

import com.myreflectionthoughts.apigateway.core.constant.ServiceConstant;
import com.myreflectionthoughts.apigateway.gateway.dataprovider.UpdateUserDataProvider;
import com.myreflectionthoughts.library.dto.request.UpdateMasterDTO;
import com.myreflectionthoughts.library.dto.request.UpdatePetDTO;
import com.myreflectionthoughts.library.dto.request.UpdateUserDTO;
import com.myreflectionthoughts.library.dto.response.MasterDTO;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import com.myreflectionthoughts.library.dto.response.UserDTO;
import com.myreflectionthoughts.library.exception.ParameterMissingException;
import io.micrometer.context.ContextRegistry;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.slf4j.MDC;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@SpringBootTest
public class UpdateUserDataProviderTest {

    private final UpdateUserDataProvider updateUserDataProvider;
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

    public UpdateUserDataProviderTest() {
        this.masterServiceClient = mock(WebClient.class, ServiceConstant.masterServiceQualifier);
        this.petServiceClient =    mock(WebClient.class, ServiceConstant.petServiceQualifier);
        ContextRegistry.getInstance().registerThreadLocalAccessor("traceId", ()-> MDC.get("traceId"), traceId-> MDC.put("traceId","traceId"), ()->MDC.remove(("traceId")));
        this.updateUserDataProvider = new UpdateUserDataProvider(masterServiceClient, petServiceClient);
    }

    @Test
    void testUpdateInfo_Success(){

        UpdateMasterDTO updateMasterDTO = generateUpdateMasterDTO();
        MasterDTO expectedUpdatedMasterDTO = generateUpdatedMasterDTO();
        UpdatePetDTO updatePetDTO = getUpdatePetDTO();
        PetDTO expectedUpdatedPetDTO = getUpdatedPetDTO();

        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setLatestUserInfo(updateMasterDTO);
        updateUserDTO.setLatestPetsInfo(List.of(updatePetDTO));

        when(masterServiceClient.put()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.header(anyString(),anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.bodyValue(any(UpdateMasterDTO.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(eq(MasterDTO.class))).thenReturn(Mono.fromSupplier(()-> expectedUpdatedMasterDTO));

        when(petServiceClient.put()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.bodyValue(any(UpdatePetDTO.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(eq(PetDTO.class))).thenReturn(Mono.fromSupplier(() -> expectedUpdatedPetDTO));


        Mono<UserDTO> actualUpdateUserMono = updateUserDataProvider.updateInfo(Mono.fromSupplier(()->updateUserDTO));

        StepVerifier.create(actualUpdateUserMono).consumeNextWith(actualUpdateUser->{
            assertEquals(expectedUpdatedMasterDTO, actualUpdateUser.getMaster());
            assertEquals(expectedUpdatedPetDTO, actualUpdateUser.getPets().get(0));
        }).verifyComplete();

        verify(masterServiceClient,times(1)).put();
        verify(requestBodyUriSpec,times(2)).uri(anyString());
        verify(requestBodySpec,times(1)).bodyValue(any(UpdateMasterDTO.class));
        verify(requestHeadersSpec,times(2)).retrieve();
        verify(responseSpec,times(1)).bodyToMono(eq(MasterDTO.class));
        verify(requestBodySpec,times(2)).header(anyString(),anyString());

        verify(petServiceClient, times(1)).put();
        verify(requestBodySpec, times(1)).bodyValue(any(UpdatePetDTO.class));
        verify(responseSpec, times(1)).bodyToMono(eq(PetDTO.class));
    }


    @Test
    void testUpdateInfo_Success_WithEmptyPetList(){

        UpdateMasterDTO updateMasterDTO = generateUpdateMasterDTO();
        MasterDTO expectedUpdatedMasterDTO = generateUpdatedMasterDTO();
        UpdatePetDTO updatePetDTO = getUpdatePetDTO();
        PetDTO expectedUpdatedPetDTO = getUpdatedPetDTO();

        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setLatestUserInfo(updateMasterDTO);
        updateUserDTO.setLatestPetsInfo(new ArrayList<>());

        when(masterServiceClient.put()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.header(anyString(),anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.bodyValue(any(UpdateMasterDTO.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(eq(MasterDTO.class))).thenReturn(Mono.fromSupplier(()-> expectedUpdatedMasterDTO));

        when(petServiceClient.put()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.bodyValue(any(UpdatePetDTO.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(eq(PetDTO.class))).thenReturn(Mono.fromSupplier(() -> expectedUpdatedPetDTO));


        Mono<UserDTO> actualUpdateUserMono = updateUserDataProvider.updateInfo(Mono.fromSupplier(()->updateUserDTO));

        StepVerifier.create(actualUpdateUserMono).consumeNextWith(actualUpdateUser->{
            assertEquals(expectedUpdatedMasterDTO, actualUpdateUser.getMaster());
            assertEquals(0, actualUpdateUser.getPets().size());
        }).verifyComplete();

        verify(masterServiceClient,times(1)).put();
        verify(requestBodyUriSpec,times(1)).uri(anyString());
        verify(requestBodySpec,times(1)).bodyValue(any(UpdateMasterDTO.class));
        verify(requestHeadersSpec,times(1)).retrieve();
        verify(responseSpec,times(1)).bodyToMono(eq(MasterDTO.class));
        verify(requestBodySpec,times(1)).header(anyString(),anyString());

        verify(petServiceClient, times(0)).put();
        verify(requestBodySpec, times(0)).bodyValue(any(UpdatePetDTO.class));
        verify(responseSpec, times(0)).bodyToMono(eq(PetDTO.class));
    }



    @Test
    void testUpdateInfo_Throws_ParameterMissingException(){

        MasterDTO expectedUpdatedMasterDTO = generateUpdatedMasterDTO();
        UpdatePetDTO updatePetDTO = getUpdatePetDTO();
        PetDTO expectedUpdatedPetDTO = getUpdatedPetDTO();

        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setLatestUserInfo(null);
        updateUserDTO.setLatestPetsInfo(List.of(updatePetDTO));

        when(masterServiceClient.put()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.bodyValue(any(UpdateMasterDTO.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(eq(MasterDTO.class))).thenReturn(Mono.fromSupplier(()-> expectedUpdatedMasterDTO));

        when(petServiceClient.put()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.bodyValue(any(UpdatePetDTO.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(eq(PetDTO.class))).thenReturn(Mono.fromSupplier(() -> expectedUpdatedPetDTO));


        Mono<UserDTO> actualUpdateUserMono = updateUserDataProvider.updateInfo(Mono.fromSupplier(()->updateUserDTO));

        StepVerifier.create(actualUpdateUserMono).expectError(ParameterMissingException.class).verify();

        verify(masterServiceClient,times(0)).put();
        verify(requestBodyUriSpec,times(0)).uri(anyString());
        verify(requestBodySpec,times(0)).bodyValue(any(UpdateMasterDTO.class));
        verify(requestHeadersSpec,times(0)).retrieve();
        verify(responseSpec,times(0)).bodyToMono(eq(MasterDTO.class));

        verify(petServiceClient, times(0)).put();
        verify(requestBodySpec, times(0)).bodyValue(any(UpdatePetDTO.class));
        verify(responseSpec, times(0)).bodyToMono(eq(PetDTO.class));
    }

    private UpdateMasterDTO generateUpdateMasterDTO(){
        UpdateMasterDTO updateMasterDTO = new UpdateMasterDTO();
        updateMasterDTO.setId(ServiceConstant.VALID_MASTER_ID);
        updateMasterDTO.setName(ServiceConstant.VALID_MASTER_NAME+"-updated");
        updateMasterDTO.setEmail("updated-"+ServiceConstant.VALID_MASTER_EMAIL);
        updateMasterDTO.setAddress(ServiceConstant.VALID_MASTER_ADDRESS+"-updated");
        updateMasterDTO.setAge(ServiceConstant.VALID_MASTER_AGE);
        updateMasterDTO.setPassword(ServiceConstant.VALID_MASTER_PASSWORD+"-updated");
        return updateMasterDTO;
    }

    private MasterDTO generateUpdatedMasterDTO(){
        MasterDTO updatedMasterDTO = new MasterDTO();
        updatedMasterDTO.setId(ServiceConstant.VALID_MASTER_ID);
        updatedMasterDTO.setName(ServiceConstant.VALID_MASTER_NAME+"-updated");
        updatedMasterDTO.setEmail("updated-"+ServiceConstant.VALID_MASTER_EMAIL);
        updatedMasterDTO.setAddress(ServiceConstant.VALID_MASTER_ADDRESS+"-updated");
        updatedMasterDTO.setAge(ServiceConstant.VALID_MASTER_AGE);
        return updatedMasterDTO;
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

    public static PetDTO getUpdatedPetDTO() {
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
