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

    @InjectMocks
    private CreatePetUseCase createPetUseCase;

    @Mock
    private IAdd<AddPetDTO, PetDTO> iAdd;

    private final String petId;
    public CreatePetUseCaseTest() {
        petId = ServiceConstants.DUMMY_PET_ID;
    }

    @Test
    void testAddPet(){

        AddPetDTO addPetDTO = getAddPetDTO();
        PetDTO expectedPetDTO = getPetDTO();

        when(iAdd.add(any(Mono.class))).thenReturn(Mono.just(expectedPetDTO));

        Mono<PetDTO> actualPetDTOMono = createPetUseCase.addPet(Mono.just(addPetDTO));

        StepVerifier.create(actualPetDTOMono).consumeNextWith(actualPetDTO->{
            assertEquals(expectedPetDTO, actualPetDTO);
        }).verifyComplete();

        verify(iAdd,times(1)).add(any(Mono.class));
    }

    private AddPetDTO getAddPetDTO() {
        AddPetDTO addPetDTO = new AddPetDTO();
        addPetDTO.setAge(1.0);
        addPetDTO.setCategory("dog");
        addPetDTO.setGender("female");
        addPetDTO.setName("pet-name");
        addPetDTO.setMaster("master");
        return addPetDTO;
    }

    private PetDTO getPetDTO() {
        PetDTO pet = new PetDTO();
        pet.setId(petId);
        pet.setAge(1.0);
        pet.setCategory("DOG");
        pet.setGender("FEMALE");
        pet.setName("pet-name");
        pet.setMaster("master");
        pet.setClinicCardStatus("NOT_APPLIED");
        return pet;
    }
}
