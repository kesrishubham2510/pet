package com.myreflectionthoughts.apipetdetails.contract;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @param <T>      :- The response type returned by the function
 */

public interface IGetPet<T> {
    Mono<T> getPetInfo(Mono<String> petId);

    Flux<T> getAllPets();
}
