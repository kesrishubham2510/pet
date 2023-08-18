package com.myreflectionthoughts.apipetdetails.gateway.dataprovider;

import com.myreflectionthoughts.apipetdetails.configuration.TestDataGenerator;
import com.myreflectionthoughts.apipetdetails.core.exception.PetNotFoundException;
import com.myreflectionthoughts.apipetdetails.gateway.dataprovider.repository.PetRepository;
import com.myreflectionthoughts.apipetdetails.gateway.dataprovider.utility.MappingUtility;
import com.myreflectionthoughts.library.dto.response.DeletePetDTO;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@SpringBootTest
public class DeletePetDataProviderTest {

    @InjectMocks
    private DeletePetDataProvider deletePetDataProvider;

    @Mock
    private PetRepository petRepository;

    @Mock
    private MappingUtility mappingUtility;

    TestDataGenerator testDataGenerator;



    public DeletePetDataProviderTest() {
        this.testDataGenerator = new TestDataGenerator();
    }

    @Test
    void testDeletePet_SuccessfulDeletion(){

        String petId = this.testDataGenerator.getPetId();

        DeletePetDTO deletePetDTOExpected = testDataGenerator.getDeletePetDTO();

        when(petRepository.existsById(anyString())).thenReturn(Mono.just(true));
        when(petRepository.deleteById(anyString())).thenReturn(Mono.empty());
        when(mappingUtility.createDeletePetDTO(anyString())).thenReturn(deletePetDTOExpected);

        Mono<DeletePetDTO> responseDTO = deletePetDataProvider.delete(Mono.just(petId));

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

        StepVerifier.create(deletePetDataProvider.delete(Mono.just(petId))).expectError(PetNotFoundException.class).verify();

        verify(petRepository,times(1)).existsById(anyString());
        verify(petRepository,times(0)).deleteById(anyString());
        verify(mappingUtility,times(0)).createDeletePetDTO(anyString());
    }

}
