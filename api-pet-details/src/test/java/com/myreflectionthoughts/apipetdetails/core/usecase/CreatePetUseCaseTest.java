package com.myreflectionthoughts.apipetdetails.core.usecase;

import com.myreflectionthoughts.apipetdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.library.contract.IAdd;
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
public class CreatePetUseCaseTest {

    private final String petId;
    private final String masterId;
    @InjectMocks
    private CreatePetUseCase createPetUseCase;
    @Mock
    private IAdd<AddPetDTO, PetDTO> iAdd;

    public CreatePetUseCaseTest() {
        petId = ServiceConstants.DUMMY_PET_ID;
        masterId = ServiceConstants.DUMMY_MASTER_ID;
    }

    @Test
    void testAddPet() {

        AddPetDTO addPetDTO = getAddPetDTO();
        PetDTO expectedPetDTO = getPetDTO();

        when(iAdd.add(any(Mono.class))).thenReturn(Mono.just(expectedPetDTO));

        Mono<PetDTO> actualPetDTOMono = createPetUseCase.addPet(Mono.just(addPetDTO));

        StepVerifier.create(actualPetDTOMono).consumeNextWith(actualPetDTO -> {
            assertEquals(expectedPetDTO, actualPetDTO);
        }).verifyComplete();

        verify(iAdd, times(1)).add(any(Mono.class));
    }

    private AddPetDTO getAddPetDTO() {
        AddPetDTO addPetDTO = new AddPetDTO();
        addPetDTO.setMasterId(masterId);
        addPetDTO.setAge(1.0);
        addPetDTO.setCategory("dog");
        addPetDTO.setGender("female");
        addPetDTO.setName("pet-name");
        return addPetDTO;
    }

    private PetDTO getPetDTO() {
        PetDTO pet = new PetDTO();
        pet.setId(petId);
        pet.setMasterId(masterId);
        pet.setAge(1.0);
        pet.setCategory("DOG");
        pet.setGender("FEMALE");
        pet.setName("pet-name");
        pet.setClinicCardStatus("NOT_APPLIED");
        return pet;
    }
}
