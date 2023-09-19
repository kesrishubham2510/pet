package com.myreflectionthoughts.apigateway.gateway.dataprovider;

import com.myreflectionthoughts.apigateway.core.constant.ServiceConstant;
import com.myreflectionthoughts.apigateway.core.utils.LogUtility;
import com.myreflectionthoughts.library.contract.IUpdate;
import com.myreflectionthoughts.library.dto.request.UpdatePetDTO;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class UpdatePetDetailsDataProvider extends DataProvider implements IUpdate<PetDTO, UpdatePetDTO> {

    private final Logger logger;
    public UpdatePetDetailsDataProvider(
            @Qualifier(ServiceConstant.masterServiceQualifier) WebClient masterServiceClient,
            @Qualifier(ServiceConstant.petServiceQualifier) WebClient petServiceClient) {
        super(masterServiceClient, petServiceClient);
        logger = Logger.getLogger(UpdatePetDetailsDataProvider.class.getName());
    }

    @Override
    public Mono<PetDTO> updateInfo(Mono<UpdatePetDTO> latestInformation) {
        return updatePetDetails(latestInformation);
    }

    private Mono<PetDTO> updatePetDetails(Mono<UpdatePetDTO> updatePetDTOMono) {
        return updatePetDTOMono
                .doOnNext(updatePetDTO -> LogUtility.loggerUtility.log(logger, "Initiating Pet:- "+updatePetDTO.getId()+" update...", Level.INFO))
                .flatMap(this::handlePetUpdate)
                .doOnNext(updatedPet-> LogUtility.loggerUtility.log(logger,"Updated for Pet:- "+updatedPet.getId()+" done",Level.INFO));
    }
}
