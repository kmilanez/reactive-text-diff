package com.wearewaes.assignment.diff.domain.decoder.base64.impl;

import com.google.common.io.BaseEncoding;
import com.wearewaes.assignment.diff.domain.decoder.base64.Base64Decoder;
import com.wearewaes.assignment.diff.domain.exception.MissingValues;
import com.wearewaes.assignment.diff.domain.model.ValuePair;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import reactor.core.publisher.Mono;

/**
 * Implements a asynchronous base64 decoder that receive value pair with encoded base64 hashes and return
 * a new pair with decoded values
 * Using Guava for DRY purposes, but we could also use java.util.Base64 (or
 * even implement a distributed decoding algorithm)
 */
@Component
public class ValuePairBase64Decoder implements Base64Decoder<ValuePair, ValuePair> {
    @Override
    public Mono<ValuePair> decode(final ValuePair values) {
        return Mono.just(values).handle((pair, sink) -> {
            if (ObjectUtils.isEmpty(values) || checkValueIsEmpty(pair)) {
                sink.error(new MissingValues());
            } else {
                sink.next(new ValuePair(decodeValue(pair.getLeftValue()),
                        decodeValue(pair.getRightValue())));
            }
        });
    }

    private boolean checkValueIsEmpty(final ValuePair values) {
        return ObjectUtils.isEmpty(values) || ObjectUtils.isEmpty(values.getLeftValue())
                || ObjectUtils.isEmpty(values.getRightValue());
    }

    private String decodeValue(final String encodedValue) {
        return new String(BaseEncoding.base64().decode(encodedValue));
    }
}
