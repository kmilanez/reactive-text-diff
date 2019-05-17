package com.wearewaes.assignment.diff.unit.controller;

import com.wearewaes.assignment.diff.controller.DiffController;
import com.wearewaes.assignment.diff.domain.exception.MissingValues;
import com.wearewaes.assignment.diff.domain.exception.ValuesAreEqual;
import com.wearewaes.assignment.diff.domain.exception.ValuesHaveDifferentSize;
import com.wearewaes.assignment.diff.domain.model.*;
import com.wearewaes.assignment.diff.service.DiffService;
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
public class DiffControllerTest {

    @Mock
    private DiffService service;

    @InjectMocks
    private DiffController controller;

    @Test
    public void shouldThrowMissingValuesExceptionWhenRequestHasEmptyValuePair() {
        // given
        DiffRequest request = new DiffRequest();
        // when
        when(service.evaluateDifferences(request)).thenReturn(Mono.error(new MissingValues()));
        //then
        StepVerifier.create(controller.evaluateDifferences(request))
                .expectError(MissingValues.class)
                .verify();
    }

    @Test
    public void shouldThrowMissingValuesExceptionWhenRequestHasValuePairLeftValueEmpty() {
        // given
        DiffRequest request = new DiffRequest("SGVsbG8=", "");
        // when
        when(service.evaluateDifferences(request)).thenReturn(Mono.error(new MissingValues()));
        //then
        StepVerifier.create(controller.evaluateDifferences(request))
                .expectError(MissingValues.class)
                .verify();
    }

    @Test
    public void shouldThrowMissingValuesExceptionWhenRequestHasValuePairRightValueEmpty() {
        // given
        DiffRequest request = new DiffRequest("", "SGFsbG8=");
        // when
        when(service.evaluateDifferences(request)).thenReturn(Mono.error(new MissingValues()));
        //then
        StepVerifier.create(controller.evaluateDifferences(request))
                .expectError(MissingValues.class)
                .verify();
    }

    @Test
    public void shouldThrowValuesAreEqualWhenRequestHasValuePairWithEqualValues() {
        // given
        DiffRequest request = new DiffRequest("SGFsbG8=", "SGFsbG8=");
        // when
        when(service.evaluateDifferences(request))
                .thenReturn(Mono.error(new ValuesAreEqual()));
        //then
        StepVerifier.create(controller.evaluateDifferences(request))
                .expectError(ValuesAreEqual.class)
                .verify();
    }

    @Test
    public void shouldThrowValuesAreEqualWhenRequestHasValuePairWithValuesOfDifferentSizes() {
        // given
        DiffRequest request = new DiffRequest("SGFsbG8=", "SGVsbG8hIQ==");
        // when
        when(service.evaluateDifferences(request))
                .thenReturn(Mono.error(new ValuesHaveDifferentSize()));
        //then
        StepVerifier.create(controller.evaluateDifferences(request))
                .expectError(ValuesHaveDifferentSize.class)
                .verify();
    }

    @Test
    public void shouldReturnDecodedValuesAndTheirDifferencesWhenThereAreDifferences() {
        // given
        DiffRequest request = new DiffRequest("SGFsbG8=", "SGVsbG8=");
        ValuePair decodedPair = new ValuePair("Hello", "Hallo");
        List<ValueDiff> pairDiffs = Collections.singletonList(new ValueDiff(2, 1));
        ValuePairDiffs diffs = new ValuePairDiffs(decodedPair, pairDiffs);
        DiffResponse response = new DiffResponse(diffs);
        // when
        when(service.evaluateDifferences(request)).thenReturn(Mono.just(response));
        // then
        StepVerifier.create(controller.evaluateDifferences(request))
                .expectNext(response)
                .verifyComplete();
    }
}
