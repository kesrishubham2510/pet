package com.myreflectionthoughts.apipetdetails.gateway.dataprovider;

import com.myreflectionthoughts.apipetdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apipetdetails.core.entity.Pet;
import com.myreflectionthoughts.apipetdetails.core.exception.PetNotFoundException;
import com.myreflectionthoughts.apipetdetails.gateway.dataprovider.repository.PetRepository;
import com.myreflectionthoughts.apipetdetails.gateway.dataprovider.utility.MappingUtility;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@SpringBootTest
public class PetDataProviderTest {

    @InjectMocks
    private PetDataProvider petDataProvider;

    @Mock
    private PetRepository petRepository;

    @Mock
    private MappingUtility mappingUtility;

    TestDataGenerator testDataGenerator;


    public PetDataProviderTest() {
        this.testDataGenerator = new TestDataGenerator();
    }
    @Test
    void getPetInfo_Successful(){

        String petId = ServiceConstants.DUMMY_PET_ID;

        Pet expectedPet = TestDataGenerator.getPet();
        PetDTO expectedPetDTO = TestDataGenerator.getPetDTO();

        when(petRepository.findById(anyString())).thenReturn(Mono.just(expectedPet));
        when(mappingUtility.mapToPetDTO(any(Pet.class))).thenReturn(expectedPetDTO);

        Mono<PetDTO> responseMono = petDataProvider.getInfo(Mono.just(petId));

        StepVerifier.create(responseMono).consumeNextWith(response-> {
            assertEquals(expectedPetDTO.getId(), response.getId());
            assertEquals(expectedPetDTO.getName(), response.getName());
            assertEquals(expectedPetDTO.getMaster(), response.getMaster());
            assertEquals(expectedPetDTO.getCategory(), response.getCategory());
            assertEquals(expectedPetDTO.getGender(), response.getGender());
            assertEquals(expectedPetDTO.getClinicCardStatus(), response.getClinicCardStatus());
            assertEquals(expectedPetDTO.getClinicCardStatusMessage(), response.getClinicCardStatusMessage());
            assertEquals(expectedPetDTO.getAge(), response.getAge());
        }).verifyComplete();

        verify(petRepository,times(1)).findById(anyString());
        verify(mappingUtility,times(1)).mapToPetDTO(any(Pet.class));
    }


    @Test
    void getPetInfo_ThrowsPetNotFoundException(){

        String petId = ServiceConstants.DUMMY_PET_ID;

        PetDTO expectedPetDTO = TestDataGenerator.getPetDTO();

        when(petRepository.findById(anyString())).thenReturn(Mono.empty());
        when(mappingUtility.mapToPetDTO(any(Pet.class))).thenReturn(expectedPetDTO);

        StepVerifier.create(petDataProvider.getInfo(Mono.just(petId))).expectError(PetNotFoundException.class).verify();

        verify(petRepository,times(1)).findById(anyString());
        verify(mappingUtility,times(0)).mapToPetDTO(any(Pet.class));
    }

}
