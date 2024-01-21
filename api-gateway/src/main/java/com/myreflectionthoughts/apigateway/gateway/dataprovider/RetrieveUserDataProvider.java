package com.myreflectionthoughts.apigateway.gateway.dataprovider;

import com.myreflectionthoughts.apigateway.core.constant.ServiceConstant;
import com.myreflectionthoughts.apigateway.core.utils.LogUtility;
import com.myreflectionthoughts.library.contract.IGet;
import com.myreflectionthoughts.library.dto.response.MasterDTO;
import com.myreflectionthoughts.library.dto.response.UserDTO;
import io.micrometer.context.ContextRegistry;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class RetrieveUserDataProvider extends DataProvider implements IGet<UserDTO> {

    private final Logger logger;

    public RetrieveUserDataProvider(
            @Qualifier(ServiceConstant.masterServiceQualifier) WebClient masterServiceClient,
            @Qualifier(ServiceConstant.petServiceQualifier) WebClient petServiceClient) {
        super(masterServiceClient, petServiceClient);
        logger = Logger.getLogger(RetrieveUserDataProvider.class.getName());
    }

    @Override
    public Mono<UserDTO> getInfo(Mono<String> id) {
        return getUserInfo(id);
    }

    private Mono<UserDTO> getUserInfo(Mono<String> masterIdMono) {
        return masterIdMono
                .doOnNext(masterId-> LogUtility.loggerUtility.log(logger, "Initiating master:- "+masterId+" info retrieval", Level.INFO))
                .flatMap(masterId ->
                    handleMasterInfoRetrieval(masterId)
                        .doOnNext(retrievedMaster-> LogUtility.loggerUtility.log(logger, "Master:- "+retrievedMaster.getId()+" retrieved successfully",Level.INFO))
                        .flatMap(masterDTO ->
                                    this.handleAllPetsOfUserRetrieval(masterId).collectList().map(
                                        pets -> {
                                            UserDTO userDTO = new UserDTO();
                                            userDTO.setPets(pets);
                                            userDTO.setMaster(masterDTO);
                                            return userDTO;
                                        }
                                )
                ).doOnNext(retrievedUser-> LogUtility.loggerUtility.log(logger, "User:- "+retrievedUser.getMaster().getId()+" has "+retrievedUser.getPets().size()+" pets",Level.INFO))

        );
    }

    private Mono<MasterDTO> handleMasterInfoRetrieval(String masterId) {

        LogUtility.loggerUtility.logEntry(logger,"Initiating retrieve user-info call to master-service");

        return masterServiceClient.get()
                .uri(String.format("/get/master/%s", masterId))
                .header("traceId", Optional.ofNullable(
                                (String) ContextRegistry.getInstance()
                                        .getThreadLocalAccessors()
                                        .stream()
                                        .filter(threadLocalAccessor -> threadLocalAccessor.key().equals("traceId"))
                                        .toList()
                                        .get(0)
                                        .getValue())
                        .orElse("Custom-traceId"))
                .retrieve()
                .bodyToMono(MasterDTO.class)
                .doOnNext(retrievedMaster-> LogUtility.loggerUtility.log(logger, "retrieve user-info call to master-service completed successfully", Level.INFO));
    }


}