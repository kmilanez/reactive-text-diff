package com.wearewaes.assignment.diff.unit.service;

import com.wearewaes.assignment.diff.domain.comparator.DiffComparator;
import com.wearewaes.assignment.diff.domain.decoder.base64.Base64Decoder;
import com.wearewaes.assignment.diff.domain.exception.MissingValues;
import com.wearewaes.assignment.diff.domain.exception.ValuesAreEqual;
import com.wearewaes.assignment.diff.domain.exception.ValuesHaveDifferentSize;
import com.wearewaes.assignment.diff.domain.model.*;
import com.wearewaes.assignment.diff.service.impl.DiffServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DiffServiceImplTest {

    @Mock
    private Base64Decoder<ValuePair, ValuePair> decoder;

    @Mock
    private DiffComparator<ValuePair, List<ValueDiff>> comparator;

    @InjectMocks
    private DiffServiceImpl service;

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenRequestHasValuePairLeftValueEmpty() {
        // given
        DiffRequest request = new DiffRequest("", "SGFsbG8=");
        // when
        when(decoder.decode(request.getValues())).thenReturn(Mono.error(new MissingValues()));
        // then
        StepVerifier.create(service.evaluateDifferences(request))
                .expectError(MissingValues.class)
                .verify();
    }

    @Test
    public void shouldThrowValuesAreEqualWhenRequestHasValuePairWithEqualValues() {
        // given
        DiffRequest request = new DiffRequest("SGFsbG8=", "SGFsbG8=");
        // when
        when(decoder.decode(request.getValues())).thenReturn(Mono.error(new ValuesAreEqual()));
        // expect
        StepVerifier.create(service.evaluateDifferences(request))
                .expectError(ValuesAreEqual.class)
                .verify();
    }

    @Test
    public void shouldThrowValuesHaveDifferentSizeWhenRequestHasValuePairWithValuesOfDifferentSizes() {
        // given
        DiffRequest request = new DiffRequest("SGFsbG8=", "SGVsbG8hIQ==");
        // when
        when(decoder.decode(request.getValues())).thenReturn(Mono.error(new ValuesHaveDifferentSize()));
        //then
        StepVerifier.create(service.evaluateDifferences(request))
                .expectError(ValuesHaveDifferentSize.class)
                .verify();
    }

    @Test
    public void shouldReturnDecodedValuesAndTheirDifferencesWhenThereAreDifferences() {
        // given
        DiffRequest request = new DiffRequest("SGFsbG8=", "SGVsbG8=");
        ValuePair decodedPair = new ValuePair("Hello", "Hallo");
        List<ValueDiff> pairDiffs = Collections.singletonList(new ValueDiff(2, 1));
        DiffResponse expectedResponse = new DiffResponse(request.getValues(), pairDiffs);
        // when
        when(decoder.decode(request.getValues())).thenReturn(Mono.just(decodedPair));
        when(comparator.compare(decodedPair)).thenReturn(Mono.just(pairDiffs));
        // then
        StepVerifier.create(service.evaluateDifferences(request))
                .expectNext(expectedResponse)
                .verifyComplete();
    }

}
