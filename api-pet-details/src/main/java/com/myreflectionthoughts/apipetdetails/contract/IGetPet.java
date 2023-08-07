package com.myreflectionthoughts.apipetdetails.contract;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @param <T>      :- The response type returned by the function
 * @param <String>
 */

public interface IGetPet<T, String> {
    Mono<T> getPetInfo(Mono<String> petId);

    Flux<T> getAllPets();
}
