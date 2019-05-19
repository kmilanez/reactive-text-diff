package com.wearewaes.assignment.diff.domain.model;

import lombok.*;

/**
 * This class abstracts a basic difference pair between two values,
 * where the offset represents the position of first occurrence and
 * the length of the difference
 */
@Getter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ValueDiff {
    private int offset;
    private int length;
}
