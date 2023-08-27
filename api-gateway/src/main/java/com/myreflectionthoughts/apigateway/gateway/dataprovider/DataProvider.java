package com.myreflectionthoughts.apigateway.gateway.dataprovider;

import com.myreflectionthoughts.apigateway.core.exception.ReceivedException;
import com.myreflectionthoughts.library.dto.request.AddMasterDTO;
import com.myreflectionthoughts.library.dto.request.AddPetDTO;
import com.myreflectionthoughts.library.dto.request.AddUserDTO;
import com.myreflectionthoughts.library.dto.response.MasterDTO;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import com.myreflectionthoughts.library.dto.response.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class DataProvider {
     protected final WebClient masterServiceClient;
     protected final WebClient petServiceClient;

     public DataProvider(WebClient masterServiceClient, WebClient petServiceClient) {
          this.masterServiceClient = masterServiceClient;
          this.petServiceClient = petServiceClient;
     }

    protected Mono<UserDTO> addUser(Mono<AddUserDTO> addUserDTOMono) {


        return addUserDTOMono.flatMap(addUserDTO -> {
            return addMaster(addUserDTO.getAddMasterDTO()).flatMap(addedMaster -> {

                return handlePets(addedMaster.getId(), addedMaster.getName(), addUserDTO.getAddPetDTO()).map(addedPets -> {
                    UserDTO userDTO = new UserDTO();
                    userDTO.setMasterDTO(addedMaster);
                    userDTO.setPetDTO(addedPets);

                    return userDTO;
                });
            });
        });
    }


     private Mono<MasterDTO> addMaster(AddMasterDTO addMasterDTO){
          return masterServiceClient.post()
                  .uri("/add")
                  .bodyValue(addMasterDTO)
                  .retrieve()
                  .onStatus(HttpStatus::is4xxClientError, clientResponse ->  clientResponse.bodyToMono(ReceivedException.class))
                  .bodyToMono(MasterDTO.class);
     }

    private Mono<List<PetDTO>> handlePets(String masterId,String masterName, List<AddPetDTO> addPetDTOs) {
        return Flux.fromIterable(addPetDTOs)
                .map(addPetDTO -> {
                    addPetDTO.setMasterId(masterId);
                    addPetDTO.setMaster(masterName);
                    return addPetDTO;
                }).flatMap(this::addPet).collectList();
    }

    private Mono<PetDTO> addPet(AddPetDTO addPetDTO){
          return petServiceClient.post()
                  .uri("/add")
                  .bodyValue(addPetDTO)
                  .retrieve()
                  .onStatus(HttpStatus::is4xxClientError, clientResponse -> clientResponse.bodyToMono(ReceivedException.class))
                  .bodyToMono(PetDTO.class);
     }
}

