package com.myreflectionthoughts.library.contract;

import reactor.core.publisher.Mono;

/**
 * @param <T>      :- Type of the response returned
 */

@FunctionalInterface
public interface IDelete<T> {
    Mono<T> delete(Mono<String> id);
}
