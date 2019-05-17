package com.wearewaes.assignment.diff.service;

import com.wearewaes.assignment.diff.domain.model.DiffRequest;
import com.wearewaes.assignment.diff.domain.model.DiffResponse;
import reactor.core.publisher.Mono;

/**
 * This interface defines the contract for difference evaluator service
 */
public interface DiffService {
    Mono<DiffResponse> evaluateDifferences(DiffRequest request);
}
