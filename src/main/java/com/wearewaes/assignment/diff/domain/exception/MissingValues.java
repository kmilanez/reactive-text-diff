package com.wearewaes.assignment.diff.domain.exception;

/**
 * This exception represents a scenario where values are missing
 */
public class MissingValues extends RuntimeException {
    public MissingValues() {
        super("Either left or right value are missing, please check your request and try again");
    }
}
