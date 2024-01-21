package com.myreflectionthoughts.apigateway.gateway.dataprovider;

import com.myreflectionthoughts.apigateway.core.utils.LogUtility;
import com.myreflectionthoughts.library.dto.request.UpdatePetDTO;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import io.micrometer.context.ContextRegistry;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataProvider {
    protected final WebClient masterServiceClient;
    protected final WebClient petServiceClient;
    private final Logger logger;

    public DataProvider(WebClient masterServiceClient, WebClient petServiceClient) {
        this.masterServiceClient = masterServiceClient;
        this.petServiceClient = petServiceClient;
        logger = Logger.getLogger(DataProvider.class.getName());
    }

    protected Flux<PetDTO> handleAllPetsOfUserRetrieval(String masterId) {

        LogUtility.loggerUtility.log(logger, "Initiating retrieve-all-pets-of-master call to pet-service", Level.INFO);

        return petServiceClient.get()
                .uri(String.format("/get/pets/%s", masterId))
                .header("traceId", Optional.ofNullable(
                                (String)ContextRegistry.getInstance()
                                .getThreadLocalAccessors()
                                .stream()
                                .filter(threadLocalAccessor -> threadLocalAccessor.key().equals("traceId"))
                                .toList()
                                .get(0)
                                .getValue())
                .orElse("Custom-traceId"))
                .retrieve()
                .bodyToFlux(PetDTO.class)
                .doOnEach(receivedPetSignal-> {
                    if(receivedPetSignal.isOnNext())
                        LogUtility.loggerUtility.log(logger, "Received pet:- "+receivedPetSignal.get().getId(), Level.INFO);

                    else if (receivedPetSignal.isOnComplete())
                        LogUtility.loggerUtility.log(logger, "Call for retrieve-all-pets-of-master to pet-service completed successfully", Level.INFO);

                    });
    }

    protected Mono<PetDTO> handlePetUpdate(UpdatePetDTO updatePetDTO) {

        LogUtility.loggerUtility.log(logger, "Initiating update-pet call to pet-service", Level.INFO);

        return petServiceClient.put()
                .uri(String.format("/update/pet/%s", updatePetDTO.getId()))
                .bodyValue(updatePetDTO)
                .retrieve()
                .bodyToMono(PetDTO.class)
                .doOnNext(updatedPet-> LogUtility.loggerUtility.log(logger, "update-pet call completed successfully...",Level.INFO));
    }
}

