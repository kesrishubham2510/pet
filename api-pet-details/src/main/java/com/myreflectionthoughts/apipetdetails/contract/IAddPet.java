package com.myreflectionthoughts.apipetdetails.contract;

import reactor.core.publisher.Mono;

/**
 * @param <T> :- Type of the parameter accepted
 * @param <K> :- The type of the response returned
 */

@FunctionalInterface
public interface IAddPet<T, K> {
    Mono<K> addPet(Mono<T> requestPayload);
}
