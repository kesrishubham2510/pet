package com.myreflectionthoughts.library.contract;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @param <T>      :- The response type returned by the function
 */

public interface IGet<T> {
    Mono<T> getInfo(Mono<String> id);
}
