package com.myreflectionthoughts.apipetdetails.contract;

import reactor.core.publisher.Mono;

/**
 * @param <T> :- Type of response
 * @param <K> :- Type of parameter accepted
 */
@FunctionalInterface
public interface IUpdatePet<T, K> {
    Mono<T> updatePetInfo(K latestInformation);
}
