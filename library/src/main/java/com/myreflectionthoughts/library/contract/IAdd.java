package com.myreflectionthoughts.library.contract;


import reactor.core.publisher.Mono;

/**
 * @param <T> :- Type of the parameter accepted
 * @param <K> :- The type of the response returned
 */

@FunctionalInterface
public interface IAdd<T, K> {
    Mono<K> add(Mono<T> requestPayload);
}
