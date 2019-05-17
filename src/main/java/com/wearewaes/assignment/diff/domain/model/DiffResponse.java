package com.wearewaes.assignment.diff.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class abstracts the response that will be sent with the diff pairs
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiffResponse {
    private ValuePairDiffs diffs;
}
