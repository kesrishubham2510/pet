package com.myreflectionthoughts.library.contract;

import reactor.core.publisher.Mono;

/**
 * @param <T> :- Type of response
 * @param <K> :- Type of parameter accepted
 */
@FunctionalInterface
public interface IUpdate<T, K> {
    Mono<T> updateInfo(Mono<K> latestInformation);
}
