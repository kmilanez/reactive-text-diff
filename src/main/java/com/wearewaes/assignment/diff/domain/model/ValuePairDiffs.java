package com.wearewaes.assignment.diff.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * This class abstracts the relationship between two values and
 * their differences
 */
@Data
@NoArgsConstructor
public class ValuePairDiffs {
    private ValuePair values;
    private List<ValueDiff> diffs;

    public ValuePairDiffs(ValuePair values) {
        this.values = values;
    }

    public ValuePairDiffs(ValuePair values, List<ValueDiff> diffs) {
        this.values = values;
        this.diffs = diffs;
    }


    public void addDiff(ValueDiff diff) {
        if (CollectionUtils.isEmpty(this.diffs)) {
            this.diffs = new ArrayList<>();
        }
        this.diffs.add(diff);
    }
}
