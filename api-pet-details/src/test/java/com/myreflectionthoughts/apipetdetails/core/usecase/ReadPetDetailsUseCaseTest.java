package com.myreflectionthoughts.apipetdetails.core.usecase;

import com.myreflectionthoughts.apipetdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.library.contract.IGet;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ReadPetDetailsUseCaseTest {

    private final String petId;
    private final String masterId;
    @InjectMocks
    private ReadPetDetailsUseCase readPetDetailsUseCase;
    @Mock
    private IGet<PetDTO> iGet;

    public ReadPetDetailsUseCaseTest() {
        petId = ServiceConstants.DUMMY_PET_ID;
        masterId = ServiceConstants.DUMMY_MASTER_ID;
    }

    @Test
    void testReadPetDetailsUseCase() {

        String petId = ServiceConstants.DUMMY_PET_ID;
        PetDTO expectedPetDTO = getPetDTO();

        when(iGet.getInfo(any(Mono.class))).thenReturn(Mono.just(expectedPetDTO));

        Mono<PetDTO> actualPetDTOMono = readPetDetailsUseCase.getDetails(Mono.just(petId));

        StepVerifier.create(actualPetDTOMono).consumeNextWith(actualPetDTO -> {
            assertEquals(expectedPetDTO, actualPetDTO);
        }).verifyComplete();

        verify(iGet, times(1)).getInfo(any(Mono.class));
    }

    private PetDTO getPetDTO() {
        PetDTO pet = new PetDTO();
        pet.setMaster(masterId);
        pet.setId(petId);
        pet.setAge(1.0);
        pet.setCategory("DOG");
        pet.setGender("FEMALE");
        pet.setName("pet-name");
        pet.setClinicCardStatus("NOT_APPLIED");
        return pet;
    }
}