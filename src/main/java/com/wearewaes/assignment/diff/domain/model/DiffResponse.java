package com.wearewaes.assignment.diff.domain.model;

import lombok.*;

/**
 * This class abstracts the response that will be sent with the diff pairs
 */
@Getter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DiffResponse {
    private ValuePairDiffs diffs;
}
