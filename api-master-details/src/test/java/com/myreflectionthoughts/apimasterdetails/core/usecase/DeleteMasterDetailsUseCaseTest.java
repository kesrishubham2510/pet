package com.myreflectionthoughts.apimasterdetails.core.usecase;

import com.myreflectionthoughts.apimasterdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.library.contract.IDelete;
import com.myreflectionthoughts.library.dto.response.DeleteMasterDTO;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static com.myreflectionthoughts.apimasterdetails.core.constant.ServiceConstants.MASTER_DELETION_MESSAGE_TEMPLATE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class DeleteMasterDetailsUseCaseTest {

     @InjectMocks
     private DeleteMasterDetailsUseCase deleteMasterDetailsUseCase;

     @Mock
     private IDelete<DeleteMasterDTO> iDelete;

     @Test
     void testDeleteMasterDetails(){

         String masterId = ServiceConstants.VALID_MASTER_ID;
         DeleteMasterDTO expectedMasterDeletionResponse = new DeleteMasterDTO();

         expectedMasterDeletionResponse.setId(masterId);
         expectedMasterDeletionResponse.setMessage(String.format(MASTER_DELETION_MESSAGE_TEMPLATE,masterId));

         when(iDelete.delete(any(Mono.class))).thenReturn(Mono.just(expectedMasterDeletionResponse));

         Mono<DeleteMasterDTO> deleteMasterResponseMono = deleteMasterDetailsUseCase.deleteMasterDetails(Mono.just(masterId));

         StepVerifier.create(deleteMasterResponseMono).consumeNextWith(deleteMasterResponse->{
             assertEquals(expectedMasterDeletionResponse, deleteMasterResponse);
         }).verifyComplete();

         verify(iDelete,times(1)).delete(any(Mono.class));
     }
}