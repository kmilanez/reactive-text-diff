package com.wearewaes.assignment.diff.service.impl;

import com.wearewaes.assignment.diff.domain.comparator.DiffComparator;
import com.wearewaes.assignment.diff.domain.decoder.base64.Base64Decoder;
import com.wearewaes.assignment.diff.domain.exception.ValuesAreEqual;
import com.wearewaes.assignment.diff.domain.exception.ValuesHaveDifferentSize;
import com.wearewaes.assignment.diff.domain.model.DiffRequest;
import com.wearewaes.assignment.diff.domain.model.DiffResponse;
import com.wearewaes.assignment.diff.domain.model.ValuePair;
import com.wearewaes.assignment.diff.domain.model.ValuePairDiffs;
import com.wearewaes.assignment.diff.service.DiffService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Implementation of the diff evaluator service. This implementation applies
 * async processing to decode and compare the values
 */
@Service
public class DiffServiceImpl implements DiffService {

    private DiffComparator<ValuePair, ValuePairDiffs> comparator;
    private Base64Decoder<ValuePair, ValuePair> decoder;

    public DiffServiceImpl(DiffComparator<ValuePair, ValuePairDiffs> comparator,
                           Base64Decoder<ValuePair, ValuePair> decoder) {
        this.comparator = comparator;
        this.decoder = decoder;
    }

    /**
     * Evaluate request value pair and return if they are equal, have different size
     * or their differences in (offset, length) pairs
     * @param request diff request with value pair
     * @return response with value pair differences
     * @throws ValuesAreEqual if value pair have two identical values
     * @throws ValuesHaveDifferentSize if value pair have two values with different sizes
     */
    @Override
    public Mono<DiffResponse> evaluateDifferences(final DiffRequest request) {
        return decoder.decode(request.getValues())
                .flatMap(pair -> comparator.compare(pair))
                .map(DiffResponse::new)
                .doOnError(Mono::error);
    }
}
