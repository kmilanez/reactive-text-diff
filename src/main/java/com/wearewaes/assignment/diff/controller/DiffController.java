package com.wearewaes.assignment.diff.controller;

import com.wearewaes.assignment.diff.domain.model.DiffRequest;
import com.wearewaes.assignment.diff.domain.model.DiffResponse;
import com.wearewaes.assignment.diff.service.DiffService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/diff")
public class DiffController {
    private DiffService service;

    public DiffController(DiffService service) {
        this.service = service;
    }

    @PostMapping
    public Mono<DiffResponse> evaluateDifferences(@RequestBody DiffRequest request) {
        return service.evaluateDifferences(request);
    }
}
