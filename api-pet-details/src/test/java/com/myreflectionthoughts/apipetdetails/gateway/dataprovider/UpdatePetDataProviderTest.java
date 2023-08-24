package com.myreflectionthoughts.apipetdetails.gateway.dataprovider;

import com.myreflectionthoughts.apipetdetails.core.entity.Pet;
import com.myreflectionthoughts.apipetdetails.core.exception.PetNotFoundException;
import com.myreflectionthoughts.apipetdetails.gateway.dataprovider.repository.PetRepository;
import com.myreflectionthoughts.apipetdetails.gateway.dataprovider.utility.MappingUtility;
import com.myreflectionthoughts.library.dto.request.UpdatePetDTO;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.MappingException;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@SpringBootTest
public class UpdatePetDataProviderTest{
    
    @InjectMocks
    private UpdatePetDataProvider updatePetDataProvider;

    @Mock
    private PetRepository petRepository;

    @Mock
    private MappingUtility mappingUtility;

    TestDataGenerator testDataGenerator;

    public UpdatePetDataProviderTest() {
        testDataGenerator = new TestDataGenerator();
    }


    @Test
    void testUpdatePetInfo(){

        UpdatePetDTO updatePetDTO = TestDataGenerator.getUpdatePetDTO();
        Pet expectedUpdatedPet = TestDataGenerator.getUpdatedPet();
        PetDTO expectedUpdatedPetDTO = TestDataGenerator.getUpdatedPetDTO();

        when(petRepository.existsById(anyString())).thenReturn(Mono.just(true));
        when(mappingUtility.mapToPet(any(UpdatePetDTO.class))).thenReturn(expectedUpdatedPet);
        when(petRepository.save(any(Pet.class))).thenReturn(Mono.just(expectedUpdatedPet));
        when(mappingUtility.mapToPetDTO(any(Pet.class))).thenReturn(expectedUpdatedPetDTO);

        Mono<PetDTO> updatedPetDTOMono = updatePetDataProvider.updateInfo(Mono.just(updatePetDTO));

        StepVerifier.create(updatedPetDTOMono).consumeNextWith(updatedPetDTO-> assertEquals(expectedUpdatedPetDTO, updatedPetDTO)).
                verifyComplete();

        verify(petRepository,times(1)).existsById(anyString());
        verify(mappingUtility,times(1)).mapToPet(any(UpdatePetDTO.class));
        verify(petRepository,times(1)).save(any(Pet.class));
        verify(mappingUtility,times(1)).mapToPetDTO(any(Pet.class));

    }

    @Test
    void testUpdatePetInfo_ThrowsPetNotFoundException(){

        UpdatePetDTO updatePetDTO = TestDataGenerator.getUpdatePetDTO();

        when(petRepository.existsById(anyString())).thenReturn(Mono.just(false));

        StepVerifier.create(updatePetDataProvider.updateInfo(Mono.just(updatePetDTO)))
                .expectError(PetNotFoundException.class).verify();

        verify(petRepository,times(1)).existsById(anyString());
        verify(mappingUtility,times(0)).mapToPet(any(UpdatePetDTO.class));
        verify(petRepository,times(0)).save(any(Pet.class));
        verify(mappingUtility,times(0)).mapToPetDTO(any(Pet.class));

    }

    @Test
    void testUpdatePetInfo_Throws_ClinicCardStatusNotFoundException(){

        UpdatePetDTO updatePetDTO = TestDataGenerator.getUpdatePetDTO();
        Pet expectedUpdatedPet = TestDataGenerator.getUpdatedPet();
        PetDTO expectedUpdatedPetDTO = TestDataGenerator.getUpdatedPetDTO();

        when(petRepository.existsById(anyString())).thenReturn(Mono.just(true));
      /*
         * the modelMapper.map() throws custom exception wrapped under cause property of MappingException
           hence, asserting for MappingException.class

         * for the same reason avoided writing failed mapping scenarios for Category and Gender

      */

        when(mappingUtility.mapToPet(any(UpdatePetDTO.class))).thenThrow(MappingException.class);
        when(petRepository.save(any(Pet.class))).thenReturn(Mono.just(expectedUpdatedPet));
        when(mappingUtility.mapToPetDTO(any(Pet.class))).thenReturn(expectedUpdatedPetDTO);

        StepVerifier.create(updatePetDataProvider.updateInfo(Mono.just(updatePetDTO))).expectError(MappingException.class).verify();

        verify(petRepository,times(1)).existsById(anyString());
        verify(mappingUtility,times(1)).mapToPet(any(UpdatePetDTO.class));
        verify(petRepository,times(0)).save(any(Pet.class));
        verify(mappingUtility,times(0)).mapToPetDTO(any(Pet.class));
    }
}
