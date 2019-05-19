package com.wearewaes.assignment.diff.domain.decoder.base64;


import reactor.core.publisher.Mono;


/**
 * Defines the contract for a base64 decoder
 * @param <R> input value type
 * @param <S> output value type
 */
public interface Base64Decoder<R, S> {
    Mono<S> decode(final R value);
}
