package com.myreflectionthoughts.apipetdetails.core.usecase;

import com.myreflectionthoughts.apipetdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.library.contract.IGetAll;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@SpringBootTest
public class ReadPetsUseCaseTest {

    @InjectMocks
    private ReadPetsUseCase readPetsUseCase;

    @Mock
    private IGetAll<PetDTO> iGetAll;

    private final String petId;
    private final String masterId;

    public ReadPetsUseCaseTest() {
        petId = ServiceConstants.DUMMY_PET_ID;
        masterId = ServiceConstants.DUMMY_MASTER_ID;
    }
    @Test
    void testGetPets() {

        when(iGetAll.getAll()).thenReturn(Flux.just(getPetDTO()));

        StepVerifier.create(readPetsUseCase.getPets()).expectNextCount(1);

        verify(iGetAll, times(1)).getAll();
    }

    private PetDTO getPetDTO() {
        PetDTO pet = new PetDTO();
        pet.setMasterId(masterId);
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