package com.wearewaes.assignment.diff.unit.domain.decoder;

import com.wearewaes.assignment.diff.domain.decoder.base64.impl.ValuePairBase64Decoder;
import com.wearewaes.assignment.diff.domain.exception.MissingValues;
import com.wearewaes.assignment.diff.domain.model.ValuePair;
import org.junit.Before;
import org.junit.Test;
import reactor.test.StepVerifier;

public class ValuePairBase64DecoderTest {

    private ValuePairBase64Decoder decoder;

    @Before
    public void setup() {
        decoder = new ValuePairBase64Decoder();
    }

    @Test
    public void shouldReturnEmptyValuePairOnInputIsEmpty() {
        // given
        ValuePair values = new ValuePair("", "");
        // expect
        StepVerifier.create(decoder.decode(values))
                .expectError(MissingValues.class)
                .verify();
    }

    @Test
    public void shouldReturnDecodedValuesWhenInputHasEncodedValues() {
        // given
        ValuePair values = new ValuePair("SGVsbG8=", "SGVsbG8=");
        ValuePair decodedValues = new ValuePair("Hello", "Hello");
        // expect
        StepVerifier.create(decoder.decode(values))
                .expectNext(decodedValues)
                .verifyComplete();
    }

}
