package com.myreflectionthoughts.apigateway.gateway.dataprovider;

import com.myreflectionthoughts.apigateway.core.constant.ServiceConstant;
import com.myreflectionthoughts.apigateway.core.utils.LogUtility;
import com.myreflectionthoughts.library.contract.IGetAll;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import io.micrometer.context.ContextRegistry;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class StreamDataProvider extends DataProvider implements IGetAll<PetDTO> {

    private final Logger logger;

    public StreamDataProvider(
            @Qualifier(ServiceConstant.masterServiceQualifier) WebClient masterServiceClient,
            @Qualifier(ServiceConstant.petServiceQualifier) WebClient petServiceClient) {
        super(masterServiceClient, petServiceClient);
        logger = Logger.getLogger(StreamDataProvider.class.getName());
    }

    @Override
    public Flux<PetDTO> getAll() {
        LogUtility.loggerUtility.logEntry(logger, "Initiating get-all-pets call to pet-service...");
        return petServiceClient.get()
                .uri("/get/all")
                .header("traceId", Optional.ofNullable(
                                (String)ContextRegistry.getInstance()
                                .getThreadLocalAccessors()
                                .stream()
                                .filter(threadLocalAccessor -> threadLocalAccessor.key().equals("traceId"))
                                .toList()
                                .get(0)
                                .getValue())
                .orElse("custom-trace-ID"))
                .retrieve()
                .bodyToFlux(PetDTO.class)
                .doOnNext(retrievedPet->LogUtility.loggerUtility.log(logger, "Received Pet:- "+retrievedPet.getId(), Level.INFO))
                .doOnComplete(()->  LogUtility.loggerUtility.log(logger, "All Pets retrieved successfully...", Level.INFO));
    }
}
