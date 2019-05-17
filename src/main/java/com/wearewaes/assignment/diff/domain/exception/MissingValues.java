package com.wearewaes.assignment.diff.domain.exception;

public class MissingValues extends RuntimeException {
    public MissingValues() {
        super("Either left or right value are missing, please check your request and try again");
    }
}
