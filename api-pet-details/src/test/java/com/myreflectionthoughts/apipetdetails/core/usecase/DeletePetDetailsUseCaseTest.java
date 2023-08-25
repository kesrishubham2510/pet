package com.myreflectionthoughts.apipetdetails.core.usecase;

import com.myreflectionthoughts.apipetdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.library.contract.IDelete;
import com.myreflectionthoughts.library.dto.response.DeletePetDTO;
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
public class DeletePetDetailsUseCaseTest {

    @InjectMocks
    private DeletePetDetailsUseCase deletePetDetailsUseCase;

    @Mock
    private IDelete<DeletePetDTO> iDelete;

    private final  ServiceConstants serviceConstants;

    public DeletePetDetailsUseCaseTest() {
        this.serviceConstants = new ServiceConstants();
    }

    @Test
    void testDeletePet(){

        String petId = ServiceConstants.DUMMY_PET_ID;

        DeletePetDTO expectedDeletePetDTO = new DeletePetDTO();
        expectedDeletePetDTO.setId(petId);
        expectedDeletePetDTO.setMessage(String.format(serviceConstants.getPET_INFO_DELETED(), petId));

        when(iDelete.delete(any(Mono.class))).thenReturn(Mono.just(expectedDeletePetDTO));

        Mono<DeletePetDTO> actualDeletePetDTOMono = deletePetDetailsUseCase.deletePet(Mono.just(petId));

        StepVerifier.create(actualDeletePetDTOMono).consumeNextWith(actualDeletePetDTO->{
            assertEquals(expectedDeletePetDTO, actualDeletePetDTO);
        }).verifyComplete();

        verify(iDelete,times(1)).delete(any(Mono.class));
    }

}