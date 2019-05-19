package com.wearewaes.assignment.diff.domain.model;

import lombok.*;

/**
 * This class abstracts the diff request from upstream systems
 */
@Getter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DiffRequest {
    private ValuePair values;

    public DiffRequest(String leftValue, String rightValue) {
        this.values = new ValuePair(leftValue, rightValue);
    }
}
