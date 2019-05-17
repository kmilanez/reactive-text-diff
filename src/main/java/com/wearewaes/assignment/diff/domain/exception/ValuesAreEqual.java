package com.wearewaes.assignment.diff.domain.exception;


/**
 * This exception abstract a scenario where both values in the pair
 * are the same
 */
public class ValuesAreEqual extends RuntimeException {
    public ValuesAreEqual() {
        super("Values are equal");
    }
}
