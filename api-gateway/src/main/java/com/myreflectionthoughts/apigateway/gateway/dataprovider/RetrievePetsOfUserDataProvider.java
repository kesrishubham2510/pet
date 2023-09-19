package com.myreflectionthoughts.apigateway.gateway.dataprovider;

import com.myreflectionthoughts.apigateway.core.constant.ServiceConstant;
import com.myreflectionthoughts.apigateway.core.utils.LogUtility;
import com.myreflectionthoughts.library.contract.IGetByCommonAttribute;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class RetrievePetsOfUserDataProvider extends DataProvider implements IGetByCommonAttribute<String, PetDTO> {

    private final Logger logger;

    public RetrievePetsOfUserDataProvider(
            @Qualifier(ServiceConstant.masterServiceQualifier) WebClient masterServiceClient,
            @Qualifier(ServiceConstant.petServiceQualifier) WebClient petServiceClient) {
        super(masterServiceClient, petServiceClient);
        logger = Logger.getLogger(RetrievePetsOfUserDataProvider.class.getName());
    }

    @Override
    public Flux<PetDTO> retrieveByAttribute(Mono<String> attribute) {
        return getAllPetsOfUser(attribute);
    }

    private Flux<PetDTO> getAllPetsOfUser(Mono<String> masterIdMono) {
        return masterIdMono
                .flatMapMany(this::handleAllPetsOfUserRetrieval)
                .doOnComplete(()-> LogUtility.loggerUtility.log(logger, "All pets of master retrieved successfully", Level.INFO));
    }
}
