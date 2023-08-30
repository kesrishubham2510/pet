package com.myreflectionthoughts.apipetdetails.core.usecase;

import com.myreflectionthoughts.apipetdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.library.contract.IGetByCommonAttribute;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ReadPetsOfMasterUseCaseTest {

    private final String petId;
    private final String masterId;
    @InjectMocks
    private ReadPetsOfMasterUseCase readAllPetsOfMasterUseCase;
    @Mock
    private IGetByCommonAttribute<String, PetDTO> iGetByCommonAttribute;

    public ReadPetsOfMasterUseCaseTest() {
        this.petId = ServiceConstants.DUMMY_PET_ID;
        this.masterId = ServiceConstants.DUMMY_MASTER_ID;
    }

    @Test
    void testRetrieveAllPetsOfMaster() {
        when(iGetByCommonAttribute.retrieveByAttribute(any(Mono.class))).thenReturn(Flux.just(getPetDTO()));

        Flux<PetDTO> petsFlux = readAllPetsOfMasterUseCase.retrieveAllPetsOfMaster(Mono.just(masterId));

        StepVerifier.create(petsFlux).expectNextCount(1).verifyComplete();

        verify(iGetByCommonAttribute, times(1)).retrieveByAttribute(any(Mono.class));
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
