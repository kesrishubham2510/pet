package com.myreflectionthoughts.apipetdetails.contract;

import reactor.core.publisher.Mono;

/**
 * @param <T>      :- Type of the response returned
 */

@FunctionalInterface
public interface IDeletePet<T> {
    Mono<T> deletePet(Mono<String> petId);
}
