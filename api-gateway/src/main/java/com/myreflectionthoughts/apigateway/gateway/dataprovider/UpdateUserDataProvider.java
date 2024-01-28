package com.myreflectionthoughts.apigateway.gateway.dataprovider;

import com.myreflectionthoughts.apigateway.core.constant.ServiceConstant;
import com.myreflectionthoughts.apigateway.core.utils.LogUtility;
import com.myreflectionthoughts.library.contract.IUpdate;
import com.myreflectionthoughts.library.dto.request.UpdateMasterDTO;
import com.myreflectionthoughts.library.dto.request.UpdatePetDTO;
import com.myreflectionthoughts.library.dto.request.UpdateUserDTO;
import com.myreflectionthoughts.library.dto.response.MasterDTO;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import com.myreflectionthoughts.library.dto.response.UserDTO;
import com.myreflectionthoughts.library.exception.ParameterMissingException;
import io.micrometer.context.ContextRegistry;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class UpdateUserDataProvider extends DataProvider implements IUpdate<UserDTO, UpdateUserDTO> {

    private final Logger logger;

    public UpdateUserDataProvider(
            @Qualifier(ServiceConstant.masterServiceQualifier) WebClient masterServiceClient,
            @Qualifier(ServiceConstant.petServiceQualifier) WebClient petServiceClient) {
        super(masterServiceClient, petServiceClient);
        logger = Logger.getLogger(UpdateUserDataProvider.class.getName());
    }

    @Override
    public Mono<UserDTO> updateInfo(Mono<UpdateUserDTO> latestInformation) {
        return updateUser(latestInformation);
    }

    protected Mono<UserDTO> updateUser(Mono<UpdateUserDTO> updateUserDTOMono) {
        return updateUserDTOMono
                .flatMap(updateUserDTO -> {

                    if (updateUserDTO.getLatestUserInfo() == null)
                        return Mono.error(new ParameterMissingException("Can't process request because the parameter 'latestMasterInfo' is missing"));

                    UserDTO userDTO = new UserDTO();

                    return handleMasterUpdate(updateUserDTO.getLatestUserInfo())
                            .doOnNext(updatedMaster -> LogUtility.loggerUtility.log(logger, "Master:- " + updatedMaster.getId() + " updated successfully...", Level.INFO))
                            .flatMap(updatedMaster -> {
                                userDTO.setMaster(updatedMaster);

                                if (updateUserDTO.getLatestPetsInfo() == null || updateUserDTO.getLatestPetsInfo().isEmpty()) {
                                    LogUtility.loggerUtility.log(logger, "No pet provided to update",Level.INFO);
                                    userDTO.setPets(new ArrayList<>());
                                } else {
                                    return handlePetUpdates(updateUserDTO.getLatestPetsInfo()).map(updatedPets -> {
                                        userDTO.setPets(updatedPets);
                                        return userDTO;
                                    });
                                }
                                return Mono.fromSupplier(() -> userDTO);
                            }).doOnNext(updatedUser-> LogUtility.loggerUtility.log(logger, "User updated successfully",Level.INFO));
                });
    }

    private Mono<MasterDTO> handleMasterUpdate(UpdateMasterDTO updateMasterDTO) {

        LogUtility.loggerUtility.logEntry(logger, "Initiating update-master call to master-service");
        return masterServiceClient.put()
                .uri(String.format("/update/master/%s", updateMasterDTO.getId()))
                .header("traceId", Optional.ofNullable(
                        (String) ContextRegistry
                                .getInstance()
                                .getThreadLocalAccessors()
                                .stream()
                                .filter(threadLocalAccessor -> threadLocalAccessor.key().equals("traceId"))
                                .toList()
                                .get(0)
                                .getValue())
                        .orElse("custom-trace-ID"))
                .bodyValue(updateMasterDTO)
                .retrieve()
                .bodyToMono(MasterDTO.class)
                .doOnNext(updateMaster-> LogUtility.loggerUtility.log(logger, "update-master call completed", Level.INFO));
    }

    private Mono<List<PetDTO>> handlePetUpdates(List<UpdatePetDTO> updatePetDTOS) {
        return Flux.fromIterable(updatePetDTOS)
                .flatMap(this::handlePetUpdate)
                .doOnEach(updatePetSignal->{

                    if(!updatePetSignal.getContextView().isEmpty()) {
                        MDC.put("traceId", updatePetSignal.getContextView().get("traceId").toString());
                        MDC.put("spanId", updatePetSignal.getContextView().get("spanId").toString());
                    }
                    
                    if(updatePetSignal.isOnNext())
                        LogUtility.loggerUtility.log(logger, "Updated Pet:- "+updatePetSignal.get().getId(), Level.INFO);
                    else if(updatePetSignal.isOnComplete())
                        LogUtility.loggerUtility.log(logger, "All Pets updated successfully...", Level.INFO);

                    MDC.clear();
                }).collectList();
    }
}