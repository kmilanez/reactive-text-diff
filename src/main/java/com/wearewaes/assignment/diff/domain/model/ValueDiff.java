package com.wearewaes.assignment.diff.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class abstracts a basic difference pair between two values,
 * where the offset represents the position of first occurrence and
 * the length of the difference
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValueDiff {
    private int offset;
    private int length;
}
