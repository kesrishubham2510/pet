package com.myreflectionthoughts.apipetdetails.contract;

import reactor.core.publisher.Mono;

/**
 * @param <T>      :- Type of the response returned
 * @param <String>
 */

@FunctionalInterface
public interface IDeletePet<T, String> {
    Mono<T> deletePet(String petId);
}
