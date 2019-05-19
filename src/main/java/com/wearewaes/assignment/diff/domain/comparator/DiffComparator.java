package com.wearewaes.assignment.diff.domain.comparator;


import reactor.core.publisher.Mono;

/**
 * This interface defines a common contract for a difference comparator
 * for a given sequence of values and return then into a reduced value
 * or container
 */
public interface DiffComparator<R, T> {
    Mono<T> compare(final R values);
}

