package com.myreflectionthoughts.apigateway.gateway.dataprovider;

import com.myreflectionthoughts.library.dto.request.*;
import com.myreflectionthoughts.library.dto.response.MasterDTO;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import com.myreflectionthoughts.library.dto.response.UserDTO;
import com.myreflectionthoughts.library.exception.ParameterMissingException;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

public class DataProvider {
    protected final WebClient masterServiceClient;
    protected final WebClient petServiceClient;

    public DataProvider(WebClient masterServiceClient, WebClient petServiceClient) {
        this.masterServiceClient = masterServiceClient;
        this.petServiceClient = petServiceClient;
    }

    protected Flux<PetDTO> getAllPetsOfUser(Mono<String> masterId) {
        return masterId.flatMapMany(this::handleAllPetsOfUserRetrieval);
    }

    protected Mono<UserDTO> getUserInfo(Mono<String> masterIdMono) {
        return masterIdMono.flatMap(masterId ->
                handleMasterInfoRetrieval(masterId).flatMap(
                        masterDTO ->
                                handleAllPetsOfUserRetrieval(masterId).collectList().map(
                                        pets -> {
                                            UserDTO userDTO = new UserDTO();
                                            userDTO.setPets(pets);
                                            userDTO.setMaster(masterDTO);
                                            return userDTO;
                                        }
                                )
                )

        );
    }


    protected Mono<UserDTO> updateUser(Mono<UpdateUserDTO> updateUserDTOMono) {
        return updateUserDTOMono.flatMap(updateUserDTO -> {

            if (updateUserDTO.getLatestUserInfo() == null)
                return Mono.error(new ParameterMissingException("Can't process request because the parameter 'latestMasterInfo' is missing"));

            UserDTO userDTO = new UserDTO();

            return handleMasterUpdate(updateUserDTO.getLatestUserInfo()).flatMap(updatedMaster -> {
                userDTO.setMaster(updatedMaster);

                if (updateUserDTO.getLatestPetsInfo() == null || updateUserDTO.getLatestPetsInfo().isEmpty()) {
                    userDTO.setPets(new ArrayList<>());
                } else {
                    return handlePetUpdates(updateUserDTO.getLatestPetsInfo()).map(updatedPets -> {
                        userDTO.setPets(updatedPets);
                        return userDTO;
                    });
                }
                return Mono.fromSupplier(() -> userDTO);
            });
        });
    }

    protected Mono<PetDTO> updatePetDetails(Mono<UpdatePetDTO> updatePetDTOMono){
        return updatePetDTOMono.flatMap(this::handlePetUpdate);
    }

    private Flux<PetDTO> handleAllPetsOfUserRetrieval(String masterId) {
        return petServiceClient.get()
                .uri(String.format("/get/pets/%s", masterId))
                .retrieve()
                .bodyToFlux(PetDTO.class);
    }


    private Mono<MasterDTO> handleMasterInfoRetrieval(String masterId) {
        return masterServiceClient.get()
                .uri(String.format("/get/master/%s", masterId))
                .retrieve()
                .bodyToMono(MasterDTO.class);
    }

    private Mono<MasterDTO> handleMasterUpdate(UpdateMasterDTO updateMasterDTO) {

        return masterServiceClient.put()
                .uri(String.format("/update/master/%s", updateMasterDTO.getId()))
                .bodyValue(updateMasterDTO)
                .retrieve()
                .bodyToMono(MasterDTO.class);
    }

    private Mono<List<PetDTO>> handlePetUpdates(List<UpdatePetDTO> updatePetDTOS) {
        return Flux.fromIterable(updatePetDTOS)
                .flatMap(this::handlePetUpdate)
                .collectList();
    }

    private Mono<PetDTO> handlePetUpdate(UpdatePetDTO updatePetDTO) {

        return petServiceClient.put()
                .uri(String.format("/update/pet/%s", updatePetDTO.getId()))
                .bodyValue(updatePetDTO)
                .retrieve()
                .bodyToMono(PetDTO.class);
    }
}

