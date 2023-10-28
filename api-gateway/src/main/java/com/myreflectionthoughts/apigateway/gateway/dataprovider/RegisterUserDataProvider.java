package com.myreflectionthoughts.apigateway.gateway.dataprovider;

import com.myreflectionthoughts.apigateway.core.constant.ServiceConstant;
import com.myreflectionthoughts.apigateway.core.utils.LogUtility;
import com.myreflectionthoughts.library.contract.IAdd;
import com.myreflectionthoughts.library.dto.request.AddMasterDTO;
import com.myreflectionthoughts.library.dto.request.AddPetDTO;
import com.myreflectionthoughts.library.dto.request.AddUserDTO;
import com.myreflectionthoughts.library.dto.response.MasterDTO;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import com.myreflectionthoughts.library.dto.response.UserDTO;
import com.myreflectionthoughts.library.exception.ParameterMissingException;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class RegisterUserDataProvider extends DataProvider implements IAdd<AddUserDTO, UserDTO> {

    private final Logger logger;

    public RegisterUserDataProvider(
            @Qualifier(ServiceConstant.masterServiceQualifier) WebClient masterServiceClient,
            @Qualifier(ServiceConstant.petServiceQualifier) WebClient petServiceClient) {
        super(masterServiceClient, petServiceClient);
        logger = Logger.getLogger(RegisterUserDataProvider.class.getName());
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

            return addMaster(addUserDTO.getMaster())
                    .doOnNext(addedMaster-> LogUtility.loggerUtility.log(logger, "Master:- "+addedMaster.getId()+" added successfully", Level.INFO))
                    .flatMap(addedMaster -> {
                userDTO.setMaster(addedMaster);

                if (addUserDTO.getPets() == null || addUserDTO.getPets().isEmpty()) {
                    userDTO.setPets(new ArrayList<>());
                    LogUtility.loggerUtility.log(logger, "User hasn't pets in the request payload",Level.INFO);
                    return Mono.fromSupplier(() -> userDTO);
                }

                return handlePets(addedMaster.getId(), addUserDTO.getPets()).map(addedPets -> {
                    userDTO.setPets(addedPets);
                    return userDTO;
                });
            }).doOnNext(addedUser-> LogUtility.loggerUtility.log(logger, "User registered successfully...",Level.INFO));
        });
    }

    private Mono<MasterDTO> addMaster(AddMasterDTO addMasterDTO) {
        LogUtility.loggerUtility.logEntry(logger, "Initiating call to master-service to add the master...");
        return  masterServiceClient
                .post()
                .uri("/add")
                .header("traceId", MDC.get("traceId"))
                .bodyValue(addMasterDTO)
                .retrieve()
                .bodyToMono(MasterDTO.class)
                .doOnNext(addedMaster-> LogUtility.loggerUtility.logExit(logger, "add-master call completed successfully..."));
    }

    private Mono<List<PetDTO>> handlePets(String masterId, List<AddPetDTO> addPetDTOs) {
        return Flux.fromIterable(addPetDTOs)
                .map(addPetDTO -> {
                    LogUtility.loggerUtility.log(logger,"Added masterId:- "+masterId+" to the add-pet ("+addPetDTO.getName()+") request payload", Level.INFO);
                    addPetDTO.setMaster(masterId);
                    return addPetDTO;
                })
                .flatMap(this::addPet)
                .doOnNext(addedPet-> LogUtility.loggerUtility.log(logger, "Pet:- "+addedPet.getId()+" added...",Level.INFO))
                .doOnComplete(()->{LogUtility.loggerUtility.log(logger, "Pets from the request payload has been added successfully...", Level.INFO);})
                .collectList();
    }

    private Mono<PetDTO> addPet(AddPetDTO addPetDTO) {

        LogUtility.loggerUtility.logEntry(logger, "Initiating add-pet call to pet-service...");

        return petServiceClient
                .post()
                .uri("/add")
                .header("traceId", MDC.get("traceId"))
                .bodyValue(addPetDTO)
                .retrieve()
                .bodyToMono(PetDTO.class)
                .doOnNext(addedMaster-> LogUtility.loggerUtility.logExit(logger, "add-pet call completed successfully..."));
    }

}