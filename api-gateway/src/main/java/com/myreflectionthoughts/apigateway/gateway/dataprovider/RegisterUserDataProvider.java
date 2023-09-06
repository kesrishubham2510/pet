package com.myreflectionthoughts.apigateway.gateway.dataprovider;

import com.myreflectionthoughts.apigateway.core.constant.ServiceConstant;
import com.myreflectionthoughts.library.contract.IAdd;
import com.myreflectionthoughts.library.dto.request.AddMasterDTO;
import com.myreflectionthoughts.library.dto.request.AddPetDTO;
import com.myreflectionthoughts.library.dto.request.AddUserDTO;
import com.myreflectionthoughts.library.dto.response.MasterDTO;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import com.myreflectionthoughts.library.dto.response.UserDTO;
import com.myreflectionthoughts.library.exception.ParameterMissingException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class RegisterUserDataProvider extends DataProvider implements IAdd<AddUserDTO, UserDTO> {

    public RegisterUserDataProvider(
            @Qualifier(ServiceConstant.masterServiceQualifier) WebClient masterServiceClient,
            @Qualifier(ServiceConstant.petServiceQualifier) WebClient petServiceClient) {
        super(masterServiceClient, petServiceClient);
    }

    @Override
    public Mono<UserDTO> add(Mono<AddUserDTO> addUserDTOMono) {
        return addUser(addUserDTOMono);
    }

    private Mono<UserDTO> addUser(Mono<AddUserDTO> addUserDTOMono) {


        return addUserDTOMono.flatMap(addUserDTO -> {

            if (addUserDTO.getMaster() == null)
                return Mono.error(new ParameterMissingException("Can't process request because the parameter 'master' is missing"));

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

}