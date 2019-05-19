package com.wearewaes.assignment.diff.unit.domain.comparator;

import com.wearewaes.assignment.diff.domain.comparator.impl.ValuePairDiffComparator;
import com.wearewaes.assignment.diff.domain.exception.ValuesAreEqual;
import com.wearewaes.assignment.diff.domain.exception.ValuesHaveDifferentSize;
import com.wearewaes.assignment.diff.domain.model.ValueDiff;
import com.wearewaes.assignment.diff.domain.model.ValuePair;
import com.wearewaes.assignment.diff.domain.model.ValuePairDiffs;
import org.junit.Before;
import org.junit.Test;
import reactor.test.StepVerifier;

import java.util.Collections;
import java.util.List;

public class ValuePairDiffComparatorTest {

    private ValuePairDiffComparator comparator;

    @Before
    public void setup() {
        this.comparator = new ValuePairDiffComparator();
    }


    @Test
    public void shouldThrowValuesAreEqualWhenValuesHaveTheSameValue() {
        // given
        ValuePair values = new ValuePair("SGVsbG8=", "SGVsbG8=");
        // expect
        StepVerifier.create(comparator.compare(values))
                .expectError(ValuesAreEqual.class)
                .verify();
    }

    @Test
    public void shouldThrowValuesHaveDifferentSizeWhenValuesHaveTheDifferentSizes() {
        // given
        ValuePair values = new ValuePair("SGVsbG8=", "SGVFsbG8=");
        // expect
        StepVerifier.create(comparator.compare(values))
                .expectError(ValuesHaveDifferentSize.class)
                .verify();
    }

    @Test
    public void shouldReturnDecodedValuesAndTheirDifferencesWhenThereAreDifferences() {
        // given
        ValuePair values = new ValuePair("SGFsbG8=", "SGVsbG8=");
        List<ValueDiff> diffs = Collections.singletonList(new ValueDiff(2, 1));
        // expect
        StepVerifier.create(comparator.compare(values))
                .expectNext(diffs)
                .verifyComplete();
    }
}
