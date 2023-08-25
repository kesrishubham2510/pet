package com.myreflectionthoughts.apipetdetails.core.usecase;

import com.myreflectionthoughts.apipetdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apipetdetails.core.enums.ClinicCardStatus;
import com.myreflectionthoughts.library.contract.IUpdate;
import com.myreflectionthoughts.library.dto.request.UpdatePetDTO;
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
public class UpdatePetDetailsUseCaseTest {

    @InjectMocks
    private UpdatePetDetailsUseCase updatePetDetailsUseCase;

    @Mock
    private IUpdate<PetDTO, UpdatePetDTO> iUpdate;

    private final String petId;

    public UpdatePetDetailsUseCaseTest() {
        this.petId = ServiceConstants.DUMMY_PET_ID;
    }

    @Test
    void testUpdateDetails(){

        UpdatePetDTO updatePetDTO = getUpdatePetDTO();
        PetDTO expectedUpdatedPetDTO = getUpdatedPetDTO();
        when(iUpdate.updateInfo(any(Mono.class))).thenReturn(Mono.just(expectedUpdatedPetDTO));

        Mono<PetDTO> actualUpdatedPetDTOMono = updatePetDetailsUseCase.updatePetDetails(Mono.just(updatePetDTO));

        StepVerifier.create(actualUpdatedPetDTOMono).consumeNextWith(actualUpdatedPetDTO->{
            assertEquals(expectedUpdatedPetDTO, actualUpdatedPetDTO);
        }).verifyComplete();

        verify(iUpdate,times(1)).updateInfo(any(Mono.class));
    }

    private PetDTO getUpdatedPetDTO(){
        PetDTO updatedPetDTO = new PetDTO();
        updatedPetDTO.setId(petId);
        updatedPetDTO.setAge(1.0);
        updatedPetDTO.setCategory("DOG");
        updatedPetDTO.setGender("FEMALE");
        updatedPetDTO.setName("pet-name-updated");
        updatedPetDTO.setMaster("master-updated");
        updatedPetDTO.setClinicCardStatus("UNDER_PROGRESS");
        updatedPetDTO.setClinicCardStatusMessage(ClinicCardStatus.valueOf(ServiceConstants.VALID_CLINIC_CARD_STATUS).getMessage());
        return updatedPetDTO;
    }


    private UpdatePetDTO getUpdatePetDTO(){
        UpdatePetDTO updatePetDTO = new UpdatePetDTO();
        updatePetDTO.setId(petId);
        updatePetDTO.setAge(1.0);
        updatePetDTO.setCategory("DOG");
        updatePetDTO.setGender("FEMALE");
        updatePetDTO.setName("pet-name-updated");
        updatePetDTO.setMaster("master-updated");
        updatePetDTO.setClinicCardStatus("UNDER_PRogrESS");
        return updatePetDTO;
    }
}

