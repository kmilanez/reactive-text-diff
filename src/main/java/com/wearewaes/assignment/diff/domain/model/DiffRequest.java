package com.wearewaes.assignment.diff.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class abstracts the diff request from upstream systems
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiffRequest {
    private ValuePair values;

    public DiffRequest(String leftValue, String rightValue) {
        this.values = new ValuePair(leftValue, rightValue);
    }
}
