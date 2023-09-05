package com.myreflectionthoughts.apigateway.gateway.dataprovider;

import com.myreflectionthoughts.library.dto.request.UpdatePetDTO;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class DataProvider {
    protected final WebClient masterServiceClient;
    protected final WebClient petServiceClient;

    public DataProvider(WebClient masterServiceClient, WebClient petServiceClient) {
        this.masterServiceClient = masterServiceClient;
        this.petServiceClient = petServiceClient;
    }

    protected Flux<PetDTO> handleAllPetsOfUserRetrieval(String masterId) {
        return petServiceClient.get()
                .uri(String.format("/get/pets/%s", masterId))
                .retrieve()
                .bodyToFlux(PetDTO.class);
    }

    protected Mono<PetDTO> handlePetUpdate(UpdatePetDTO updatePetDTO) {

        return petServiceClient.put()
                .uri(String.format("/update/pet/%s", updatePetDTO.getId()))
                .bodyValue(updatePetDTO)
                .retrieve()
                .bodyToMono(PetDTO.class);
    }
}

