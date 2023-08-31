package com.myreflectionthoughts.apigateway.gateway.dataprovider;

import com.myreflectionthoughts.library.dto.request.AddMasterDTO;
import com.myreflectionthoughts.library.dto.request.AddPetDTO;
import com.myreflectionthoughts.library.dto.request.AddUserDTO;
import com.myreflectionthoughts.library.dto.response.MasterDTO;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import com.myreflectionthoughts.library.dto.response.UserDTO;
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

    protected Mono<UserDTO> addUser(Mono<AddUserDTO> addUserDTOMono) {


        return addUserDTOMono.flatMap(addUserDTO -> {
            UserDTO userDTO = new UserDTO();

            return addMaster(addUserDTO.getMaster()).flatMap(addedMaster -> {
                userDTO.setMaster(addedMaster);

                if (addUserDTO.getMaster() == null || addUserDTO.getPets().isEmpty()) {
                    userDTO.setPets(new ArrayList<>());
                    return Mono.fromSupplier(() -> userDTO);
                }

                return handlePets(addedMaster.getId(), addUserDTO.getPets()).map(addedPets -> {
                    userDTO.setPets(addedPets);
                    return userDTO;
                });
            });
        });
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

    private Flux<PetDTO> handleAllPetsOfUserRetrieval(String masterId) {
        return petServiceClient.get()
                .uri(String.format("/get/pets/%s", masterId))
                .retrieve()
                .bodyToFlux(PetDTO.class);
    }


    private Mono<MasterDTO> addMaster(AddMasterDTO addMasterDTO) {
        return masterServiceClient.post()
                .uri("/add")
                .bodyValue(addMasterDTO)
                .retrieve()
                .bodyToMono(MasterDTO.class);
    }

    private Mono<List<PetDTO>> handlePets(String masterId, List<AddPetDTO> addPetDTOs) {
        return Flux.fromIterable(addPetDTOs)
                .map(addPetDTO -> {
                    addPetDTO.setMaster(masterId);
                    return addPetDTO;
                }).flatMap(this::addPet).collectList();
    }

    private Mono<PetDTO> addPet(AddPetDTO addPetDTO) {
        return petServiceClient.post()
                .uri("/add")
                .bodyValue(addPetDTO)
                .retrieve()
                .bodyToMono(PetDTO.class);
    }

    private Mono<MasterDTO> handleMasterInfoRetrieval(String masterId) {
        return masterServiceClient.get()
                .uri(String.format("/get/master/%s", masterId))
                .retrieve()
                .bodyToMono(MasterDTO.class);
    }
}

