package com.myreflectionthoughts.apigateway.gateway.dataprovider;

import com.myreflectionthoughts.apigateway.core.constant.ServiceConstant;
import com.myreflectionthoughts.library.contract.IUpdate;
import com.myreflectionthoughts.library.dto.request.UpdateMasterDTO;
import com.myreflectionthoughts.library.dto.request.UpdatePetDTO;
import com.myreflectionthoughts.library.dto.request.UpdateUserDTO;
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
public class UpdateUserDataProvider extends DataProvider implements IUpdate<UserDTO, UpdateUserDTO> {

    public UpdateUserDataProvider(
            @Qualifier(ServiceConstant.masterServiceQualifier) WebClient masterServiceClient,
            @Qualifier(ServiceConstant.petServiceQualifier) WebClient petServiceClient) {
        super(masterServiceClient, petServiceClient);
    }

    @Override
    public Mono<UserDTO> updateInfo(Mono<UpdateUserDTO> latestInformation) {
        return updateUser(latestInformation);
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
}