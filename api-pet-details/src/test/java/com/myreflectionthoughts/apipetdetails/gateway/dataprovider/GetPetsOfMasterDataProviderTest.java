package com.myreflectionthoughts.apipetdetails.gateway.dataprovider;

import com.myreflectionthoughts.apipetdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apipetdetails.gateway.dataprovider.repository.PetRepository;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@SpringBootTest
public class GetPetsOfMasterDataProviderTest {

    private final String petId;
    private final String masterId;
    @InjectMocks
    private GetPetsOfMasterDataProvider getPetsOfMasterDataProvider;
    @Mock
    private PetRepository petRepository;

    public GetPetsOfMasterDataProviderTest() {
        this.petId = ServiceConstants.DUMMY_PET_ID;
        this.masterId = ServiceConstants.DUMMY_MASTER_ID;
    }

    @Test
    void testRetrieveByAttribute() {
        when(petRepository.findAllByMaster(anyString())).thenReturn(Flux.just(getPetDTO()));
        Flux<PetDTO> petsOfMasterFlux = getPetsOfMasterDataProvider.retrieveByAttribute(Mono.just(masterId));

        StepVerifier.create(petsOfMasterFlux).expectNextCount(1).verifyComplete();
        verify(petRepository, times(1)).findAllByMaster(anyString());
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