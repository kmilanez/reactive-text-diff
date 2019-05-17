package com.wearewaes.assignment.diff.domain.exception;

/**
 * This exception abstract a scenario where both values in the pair
 * have different size
 */
public class ValuesHaveDifferentSize extends RuntimeException {
    public ValuesHaveDifferentSize() {
        super("Values have different sizes");
    }
}
