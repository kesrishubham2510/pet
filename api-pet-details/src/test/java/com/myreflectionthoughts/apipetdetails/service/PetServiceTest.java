/**
     * Unit test for PetService.java
*/
package com.myreflectionthoughts.apipetdetails.service;

import com.myreflectionthoughts.apipetdetails.constant.ServiceConstants;
import com.myreflectionthoughts.apipetdetails.data.TestDataGenerator;
import com.myreflectionthoughts.apipetdetails.entity.Pet;
import com.myreflectionthoughts.apipetdetails.exception.PetNotFoundException;
import com.myreflectionthoughts.apipetdetails.repository.PetRepository;
import com.myreflectionthoughts.apipetdetails.utility.MappingUtility;
import com.myreflectionthoughts.library.dto.request.AddPetDTO;
import com.myreflectionthoughts.library.dto.request.UpdatePetDTO;
import com.myreflectionthoughts.library.dto.response.DeletePetDTO;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.MappingException;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PetServiceTest{

    @InjectMocks
    private PetService petService;

    @Mock
    private PetRepository petRepository;

    @Mock
    private MappingUtility mappingUtility;

    private final TestDataGenerator testDataGenerator;

    public PetServiceTest(){
        testDataGenerator = new TestDataGenerator();
    }

    @Test
    void testAddPet(){

        Pet expectedPetFromAddPetDTO = TestDataGenerator.getPet();
        PetDTO expectedPetDTO = TestDataGenerator.getPetDTO();

        AddPetDTO addPetDTO = TestDataGenerator.getAddPetDTO();

        when(mappingUtility.mapToPet(any(AddPetDTO.class))).thenReturn(expectedPetFromAddPetDTO);
        when(petRepository.save(any(Pet.class))).thenReturn(Mono.just(expectedPetFromAddPetDTO));
        when(mappingUtility.mapToPetDTO(any(Pet.class))).thenReturn(expectedPetDTO);

        Mono<PetDTO> responseMono = petService.add(Mono.just(addPetDTO));

        StepVerifier.create(responseMono).consumeNextWith(response->{
            assertEquals(expectedPetDTO.getId(), response.getId());
            assertEquals(expectedPetDTO.getName(), response.getName());
            assertEquals(expectedPetDTO.getMaster(), response.getMaster());
            assertEquals(expectedPetDTO.getCategory(), response.getCategory());
            assertEquals(expectedPetDTO.getGender(), response.getGender());
            assertEquals(expectedPetDTO.getClinicCardStatus(), response.getClinicCardStatus());
            assertEquals(expectedPetDTO.getClinicCardStatusMessage(), response.getClinicCardStatusMessage());
            assertEquals(expectedPetDTO.getAge(), response.getAge());
        }).verifyComplete();

        verify(mappingUtility,times(1)).mapToPet(any(AddPetDTO.class));
        verify(petRepository,times(1)).save(any(Pet.class));
        verify(mappingUtility,times(1)).mapToPetDTO(any(Pet.class));
    }

    @Test
    void getPetInfo_Successful(){

        String petId = ServiceConstants.DUMMY_PET_ID;

        Pet expectedPet = TestDataGenerator.getPet();
        PetDTO expectedPetDTO = TestDataGenerator.getPetDTO();

        when(petRepository.findById(anyString())).thenReturn(Mono.just(expectedPet));
        when(mappingUtility.mapToPetDTO(any(Pet.class))).thenReturn(expectedPetDTO);

        Mono<PetDTO> responseMono = petService.getInfo(Mono.just(petId));

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

        StepVerifier.create(petService.getInfo(Mono.just(petId))).expectError(PetNotFoundException.class).verify();

        verify(petRepository,times(1)).findById(anyString());
        verify(mappingUtility,times(0)).mapToPetDTO(any(Pet.class));
    }

    @Test
    void testGetAllPets(){

        Pet expectedPet = TestDataGenerator.getPet();
        PetDTO expectedPetDTO = TestDataGenerator.getPetDTO();

        when(petRepository.findAll()).thenReturn(Flux.just(expectedPet));
        when(mappingUtility.mapToPetDTO(any(Pet.class))).thenReturn(expectedPetDTO);

        StepVerifier.create(petService.getAll()).expectNextCount(1).verifyComplete();

        verify(petRepository,times(1)).findAll();
        verify(mappingUtility,times(1)).mapToPetDTO(any(Pet.class));
    }


    @Test
    void testDeletePet_SuccessfulDeletion(){

        String petId = testDataGenerator.getPetId();

        DeletePetDTO deletePetDTOExpected = testDataGenerator.getDeletePetDTO();

        when(petRepository.existsById(anyString())).thenReturn(Mono.just(true));
        when(petRepository.deleteById(anyString())).thenReturn(Mono.empty());
        when(mappingUtility.createDeletePetDTO(anyString())).thenReturn(deletePetDTOExpected);

        Mono<DeletePetDTO> responseDTO = petService.delete(Mono.just(petId));

        StepVerifier.create(responseDTO).consumeNextWith((response->{
            assertEquals(petId, response.getId());
            assertEquals(deletePetDTOExpected.getMessage(), response.getMessage());
        })).verifyComplete();

        verify(petRepository,times(1)).existsById(anyString());
        verify(petRepository,times(1)).deleteById(anyString());
        verify(mappingUtility,times(1)).createDeletePetDTO(anyString());
    }

    @Test
    void testDeletePet_ThrowsPetNotFoundException(){

        String petId = testDataGenerator.getPetId();

        when(petRepository.existsById(anyString())).thenReturn(Mono.just(false));

        StepVerifier.create(petService.delete(Mono.just(petId))).expectError(PetNotFoundException.class).verify();

        verify(petRepository,times(1)).existsById(anyString());
        verify(petRepository,times(0)).deleteById(anyString());
        verify(mappingUtility,times(0)).createDeletePetDTO(anyString());
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

        Mono<PetDTO> updatedPetDTOMono = petService.updateInfo(Mono.just(updatePetDTO));

        StepVerifier.create(updatedPetDTOMono).consumeNextWith(updatedPetDTO-> assertEquals(expectedUpdatedPetDTO, updatedPetDTO)).verifyComplete();

        verify(petRepository,times(1)).existsById(anyString());
        verify(mappingUtility,times(1)).mapToPet(any(UpdatePetDTO.class));
        verify(petRepository,times(1)).save(any(Pet.class));
        verify(mappingUtility,times(1)).mapToPetDTO(any(Pet.class));

    }

    @Test
    void testUpdatePetInfo_ThrowsPetNotFoundException(){

        UpdatePetDTO updatePetDTO = TestDataGenerator.getUpdatePetDTO();

        when(petRepository.existsById(anyString())).thenReturn(Mono.just(false));

        StepVerifier.create(petService.updateInfo(Mono.just(updatePetDTO)))
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

        StepVerifier.create(petService.updateInfo(Mono.just(updatePetDTO))).expectError(MappingException.class).verify();

        verify(petRepository,times(1)).existsById(anyString());
        verify(mappingUtility,times(1)).mapToPet(any(UpdatePetDTO.class));
        verify(petRepository,times(0)).save(any(Pet.class));
        verify(mappingUtility,times(0)).mapToPetDTO(any(Pet.class));
    }
}
