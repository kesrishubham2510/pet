package com.myreflectionthoughts.library.contract;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 *
 * @param <AttributeDataType>:- attribute like String, enum, etc
 * @param <res>
 */

@FunctionalInterface
public interface IGetByCommonAttribute<AttributeDataType, res> {

    public Flux<res> retrieveByAttribute(Mono<AttributeDataType> attribute);
}
