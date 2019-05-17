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
    public Mono<ValuePair> decode(ValuePair values) {
        if (ObjectUtils.isEmpty(values)) {
            return Mono.error(MissingValues::new);
        }
        return Mono.just(values).handle((vals, sink) -> {
            if (checkValueIsEmpty(vals)) {
                sink.error(new MissingValues());
            } else {
                String decodedLeftValue = decodeValue(vals.getLeftValue());
                String decodedRightValue = decodeValue(vals.getRightValue());
                sink.next(new ValuePair(decodedLeftValue, decodedRightValue));
            }
        });
    }

    private boolean checkValueIsEmpty(ValuePair values) {
        return ObjectUtils.isEmpty(values) || ObjectUtils.isEmpty(values.getLeftValue())
                || ObjectUtils.isEmpty(values.getRightValue());
    }

    private String decodeValue(String encodedValue) {
        return new String(BaseEncoding.base64().decode(encodedValue));
    }
}
