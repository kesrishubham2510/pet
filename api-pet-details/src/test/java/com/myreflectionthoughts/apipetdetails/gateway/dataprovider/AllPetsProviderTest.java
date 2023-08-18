package com.myreflectionthoughts.apipetdetails.gateway.dataprovider;

import com.myreflectionthoughts.apipetdetails.configuration.TestDataGenerator;
import com.myreflectionthoughts.apipetdetails.core.entity.Pet;
import com.myreflectionthoughts.apipetdetails.gateway.dataprovider.repository.PetRepository;
import com.myreflectionthoughts.apipetdetails.gateway.dataprovider.utility.MappingUtility;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AllPetsProviderTest{

    @InjectMocks
    private AllPetsProvider allPetsProvider;

    @Mock
    private PetRepository petRepository;

    @Mock
    private MappingUtility mappingUtility;

    TestDataGenerator testDataGenerator;


    public AllPetsProviderTest() {
        this.testDataGenerator = new TestDataGenerator();
    }

    @Test
    void testGetAllPets(){

        Pet expectedPet = TestDataGenerator.getPet();
        PetDTO expectedPetDTO = TestDataGenerator.getPetDTO();

        when(petRepository.findAll()).thenReturn(Flux.just(expectedPet));
        when(mappingUtility.mapToPetDTO(any(Pet.class))).thenReturn(expectedPetDTO);

        StepVerifier.create(allPetsProvider.getAll()).expectNextCount(1).verifyComplete();

        verify(petRepository,times(1)).findAll();
        verify(mappingUtility,times(1)).mapToPetDTO(any(Pet.class));
    }

}
