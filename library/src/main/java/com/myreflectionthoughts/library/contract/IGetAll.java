package com.myreflectionthoughts.library.contract;

import reactor.core.publisher.Flux;

@FunctionalInterface
public interface IGetAll<T> {
    Flux<T> getAll();
}
