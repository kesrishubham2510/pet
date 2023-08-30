package com.myreflectionthoughts.apipetdetails.gateway.dataprovider;

import com.myreflectionthoughts.apipetdetails.core.entity.Pet;
import com.myreflectionthoughts.apipetdetails.gateway.dataprovider.repository.PetRepository;
import com.myreflectionthoughts.apipetdetails.gateway.dataprovider.utility.MappingUtility;
import com.myreflectionthoughts.library.dto.request.AddPetDTO;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CreatePetDataProviderTest {

    TestDataGenerator testDataGenerator;
    @InjectMocks
    private CreatePetDataProvider createPetDataProvider;
    @Mock
    private PetRepository petRepository;
    @Mock
    private MappingUtility mappingUtility;

    public CreatePetDataProviderTest() {
        this.testDataGenerator = new TestDataGenerator();
    }

    @Test
    void testAddPet() {

        Pet expectedPetFromAddPetDTO = TestDataGenerator.getPet();
        PetDTO expectedPetDTO = TestDataGenerator.getPetDTO();

        AddPetDTO addPetDTO = TestDataGenerator.getAddPetDTO();

        when(mappingUtility.mapToPet(any(AddPetDTO.class))).thenReturn(expectedPetFromAddPetDTO);
        when(petRepository.save(any(Pet.class))).thenReturn(Mono.just(expectedPetFromAddPetDTO));
        when(mappingUtility.mapToPetDTO(any(Pet.class))).thenReturn(expectedPetDTO);

        Mono<PetDTO> responseMono = createPetDataProvider.add(Mono.just(addPetDTO));

        StepVerifier.create(responseMono).consumeNextWith(response -> {
            assertEquals(expectedPetDTO.getId(), response.getId());
            assertEquals(expectedPetDTO.getName(), response.getName());
            assertEquals(expectedPetDTO.getCategory(), response.getCategory());
            assertEquals(expectedPetDTO.getGender(), response.getGender());
            assertEquals(expectedPetDTO.getClinicCardStatus(), response.getClinicCardStatus());
            assertEquals(expectedPetDTO.getClinicCardStatusMessage(), response.getClinicCardStatusMessage());
            assertEquals(expectedPetDTO.getAge(), response.getAge());
        }).verifyComplete();

        verify(mappingUtility, times(1)).mapToPet(any(AddPetDTO.class));
        verify(petRepository, times(1)).save(any(Pet.class));
        verify(mappingUtility, times(1)).mapToPetDTO(any(Pet.class));
    }


}
