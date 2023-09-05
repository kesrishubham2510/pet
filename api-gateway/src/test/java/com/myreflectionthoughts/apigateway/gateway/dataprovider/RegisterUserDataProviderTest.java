package com.myreflectionthoughts.apigateway.gateway.dataprovider;

import com.myreflectionthoughts.apigateway.core.constant.ServiceConstant;
import com.myreflectionthoughts.apigateway.gateway.dataprovider.RegisterUserDataProvider;
import com.myreflectionthoughts.library.dto.request.AddMasterDTO;
import com.myreflectionthoughts.library.dto.request.AddPetDTO;
import com.myreflectionthoughts.library.dto.request.AddUserDTO;
import com.myreflectionthoughts.library.dto.response.MasterDTO;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import com.myreflectionthoughts.library.dto.response.UserDTO;
import com.myreflectionthoughts.library.exception.ParameterMissingException;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestBodyUriSpec;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@SpringBootTest
public class RegisterUserDataProviderTest {

    private final RegisterUserDataProvider registerUserDataProvider;

    private  final WebClient masterServiceClient;

    private  final WebClient petServiceClient;

    @Mock
    RequestBodyUriSpec requestBodyUriSpec;

    @Mock
    WebClient.RequestBodySpec requestBodySpec;

    @Mock
    WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    WebClient.ResponseSpec responseSpec;

    public RegisterUserDataProviderTest() {
        this.masterServiceClient = mock(WebClient.class, ServiceConstant.masterServiceQualifier);
        this.petServiceClient = mock(WebClient.class, ServiceConstant.petServiceQualifier);
        registerUserDataProvider = new RegisterUserDataProvider(masterServiceClient, petServiceClient);
    }


    @Test
    void testAdd_Success(){

        MasterDTO expectedMasterDTO = generateMasterDTO();
        PetDTO expectedPetDTO = generatePetDTO();

        AddUserDTO addUserDTO = new AddUserDTO();
        addUserDTO.setMaster(generateAddMasterDTO());
        addUserDTO.setPets(generateAddPetList());

        when(masterServiceClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.bodyValue(any(AddMasterDTO.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(eq(MasterDTO.class))).thenReturn(Mono.fromSupplier(()->expectedMasterDTO));

        when(petServiceClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.bodyValue(any(AddPetDTO.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(eq(PetDTO.class))).thenReturn(Mono.fromSupplier(()->expectedPetDTO));

        Mono<UserDTO> actualUserDTOMono = registerUserDataProvider.add(Mono.fromSupplier(()->addUserDTO));

        StepVerifier.create(actualUserDTOMono).consumeNextWith(actualUserDTO->{
            assertEquals(expectedMasterDTO,actualUserDTO.getMaster());
            assertEquals(1, actualUserDTO.getPets().size());
            assertEquals(expectedPetDTO, actualUserDTO.getPets().get(0));
        }).verifyComplete();

        verify(masterServiceClient,times(1)).post();
        verify(requestBodyUriSpec,times(2)).uri(anyString());
        verify(requestBodySpec,times(1)).bodyValue(any(AddMasterDTO.class));
        verify(requestHeadersSpec,times(2)).retrieve();
        verify(responseSpec,times(1)).bodyToMono(eq(MasterDTO.class));

        verify(petServiceClient,times(1)).post();
        verify(requestBodySpec,times(1)).bodyValue(any(AddPetDTO.class));
        verify(responseSpec,times(1)).bodyToMono(eq(PetDTO.class));
    }

    @Test
    void testAdd_Success_With_Empty_PetList(){

        MasterDTO expectedMasterDTO = generateMasterDTO();
        PetDTO expectedPetDTO = generatePetDTO();

        AddUserDTO addUserDTO = new AddUserDTO();
        addUserDTO.setMaster(generateAddMasterDTO());
        addUserDTO.setPets(new ArrayList<>());

        when(masterServiceClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.bodyValue(any(AddMasterDTO.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(eq(MasterDTO.class))).thenReturn(Mono.fromSupplier(()->expectedMasterDTO));

        when(petServiceClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.bodyValue(any(AddPetDTO.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(eq(PetDTO.class))).thenReturn(Mono.fromSupplier(()->expectedPetDTO));

        Mono<UserDTO> actualUserDTOMono = registerUserDataProvider.add(Mono.fromSupplier(()->addUserDTO));

        StepVerifier.create(actualUserDTOMono).consumeNextWith(actualUserDTO->{
            assertEquals(expectedMasterDTO,actualUserDTO.getMaster());
            assertEquals(0, actualUserDTO.getPets().size());
        }).verifyComplete();

        verify(masterServiceClient,times(1)).post();
        verify(requestBodyUriSpec,times(1)).uri(anyString());
        verify(requestBodySpec,times(1)).bodyValue(any(AddMasterDTO.class));
        verify(requestHeadersSpec,times(1)).retrieve();
        verify(responseSpec,times(1)).bodyToMono(eq(MasterDTO.class));

        verify(petServiceClient,times(0)).post();
        verify(requestBodySpec,times(0)).bodyValue(any(AddPetDTO.class));
        verify(responseSpec,times(0)).bodyToMono(eq(PetDTO.class));
    }

    @Test
    void testAdd_Throws_ParameterMissingException(){

        MasterDTO expectedMasterDTO = generateMasterDTO();
        PetDTO expectedPetDTO = generatePetDTO();

        AddUserDTO addUserDTO = new AddUserDTO();
        addUserDTO.setMaster(null);
        addUserDTO.setPets(generateAddPetList());

        when(masterServiceClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.bodyValue(any(AddMasterDTO.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(eq(MasterDTO.class))).thenReturn(Mono.fromSupplier(()->expectedMasterDTO));

        when(petServiceClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.bodyValue(any(AddPetDTO.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(eq(PetDTO.class))).thenReturn(Mono.fromSupplier(()->expectedPetDTO));

        Mono<UserDTO> actualUserDTOMono = registerUserDataProvider.add(Mono.fromSupplier(()->addUserDTO));

        StepVerifier.create(actualUserDTOMono).expectError(ParameterMissingException.class).verify();

        // TODO:- Check how to assert same calls made differently on similar mocks
        verify(masterServiceClient,times(0)).post();
        verify(requestBodyUriSpec,times(0)).uri(anyString());
        verify(requestBodySpec,times(0)).bodyValue(any(AddMasterDTO.class));
        verify(requestHeadersSpec,times(0)).retrieve();
        verify(responseSpec,times(0)).bodyToMono(eq(MasterDTO.class));

        verify(petServiceClient,times(0)).post();
        verify(requestBodySpec,times(0)).bodyValue(any(AddPetDTO.class));
        verify(responseSpec,times(0)).bodyToMono(eq(PetDTO.class));
    }

    private AddMasterDTO generateAddMasterDTO(){
        AddMasterDTO addMasterDTO = new AddMasterDTO();
        addMasterDTO.setName(ServiceConstant.VALID_MASTER_NAME);
        addMasterDTO.setEmail(ServiceConstant.VALID_MASTER_EMAIL);
        addMasterDTO.setAddress(ServiceConstant.VALID_MASTER_ADDRESS);
        addMasterDTO.setAge(ServiceConstant.VALID_MASTER_AGE);
        addMasterDTO.setPassword(ServiceConstant.VALID_MASTER_PASSWORD);
        return addMasterDTO;
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

    private List<AddPetDTO> generateAddPetList(){
        return List.of(getAddPetDTO());
    }

    private AddPetDTO getAddPetDTO() {
        AddPetDTO addPetDTO = new AddPetDTO();
        addPetDTO.setMaster("");
        addPetDTO.setAge(1.0);
        addPetDTO.setCategory("dog");
        addPetDTO.setGender("female");
        addPetDTO.setName("pet-name");
        return addPetDTO;
    }
}
