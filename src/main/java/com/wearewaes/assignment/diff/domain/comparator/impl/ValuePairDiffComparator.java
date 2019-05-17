package com.wearewaes.assignment.diff.domain.comparator.impl;

import com.wearewaes.assignment.diff.domain.comparator.DiffComparator;
import com.wearewaes.assignment.diff.domain.exception.ValuesAreEqual;
import com.wearewaes.assignment.diff.domain.exception.ValuesHaveDifferentSize;
import com.wearewaes.assignment.diff.domain.model.ValueDiff;
import com.wearewaes.assignment.diff.domain.model.ValuePair;
import com.wearewaes.assignment.diff.domain.model.ValuePairDiffs;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

/**
 * Implements a value pair comparator that returns a list of difference offset and
 * length
 */
@Component
public class ValuePairDiffComparator implements DiffComparator<ValuePair, ValuePairDiffs> {

    private static final int CLEAN_STATE = 0;

    /**
     * Iterate over two string values and return the difference offsets and lengths
     * of these differences
     * @param valuePair string value pair
     * @return list with differences (offset, length)
     */
    public Mono<ValuePairDiffs> compare(ValuePair valuePair) {
        return Mono.just(valuePair).handle((pair, sink) -> {
            if (pair.areEqual()) {
                sink.error(new ValuesAreEqual());
            } else if (pair.haveDifferentSize()) {
                sink.error(new ValuesHaveDifferentSize());
            } else {
                sink.next(computeDifferences(pair));
            }
        });
    }

    private boolean checkIfValuesAreNotEmpty(ValuePair valuePair) {
        return !ObjectUtils.isEmpty(valuePair)
                && !StringUtils.isEmpty(valuePair.getLeftValue())
                && !StringUtils.isEmpty(valuePair.getLeftValue()) ;
    }

    private ValuePairDiffs computeDifferences(ValuePair valuePair) {
        ValuePairDiffs valuePairDiffs = new ValuePairDiffs(valuePair);

        String leftValue = valuePair.getLeftValue();
        String rightValue = valuePair.getRightValue();

        int length = CLEAN_STATE;
        int offset = CLEAN_STATE;

        for (int pos = 0; pos < leftValue.length(); pos++) {
            if (valuesAreDifferentInPosition(leftValue, rightValue, pos)) {
                if (isCapturingDiffs(length)) {
                    length++;
                } else {
                    offset = pos;
                    length++;
                }
            } else {
                if (isCapturingDiffs(length)) {
                    valuePairDiffs.addDiff(new ValueDiff(offset, length));
                    offset = CLEAN_STATE;
                    length = CLEAN_STATE;
                }
            }
        }

        return valuePairDiffs;
    }

    /**
     * Check if the values between two string of same size, at a given index, are not the same
     * @param firstValue string value
     * @param secondValue string value
     * @param index index to compare
     * @return true if values are not the same
     */
    private boolean valuesAreDifferentInPosition(String firstValue, String secondValue, int index) {
        return firstValue.charAt(index) != secondValue.charAt(index);
    }

    /**
     * Check if length is not in clean state(0), which indicate that algorithm is capturing differences
     * for a given offset
     * @param length difference length
     * @return true if its no in clean state(0)
     */
    private boolean isCapturingDiffs(int length) {
        return length != CLEAN_STATE;
    }
}

