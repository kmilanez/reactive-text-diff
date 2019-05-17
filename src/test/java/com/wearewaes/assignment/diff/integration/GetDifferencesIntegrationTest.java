package com.wearewaes.assignment.diff.integration;

import com.wearewaes.assignment.diff.controller.DiffController;
import com.wearewaes.assignment.diff.domain.comparator.impl.ValuePairDiffComparator;
import com.wearewaes.assignment.diff.domain.decoder.base64.impl.ValuePairBase64Decoder;
import com.wearewaes.assignment.diff.domain.exception.MissingValues;
import com.wearewaes.assignment.diff.domain.exception.ValuesAreEqual;
import com.wearewaes.assignment.diff.domain.model.*;
import com.wearewaes.assignment.diff.service.impl.DiffServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Collections;
import java.util.List;


@RunWith(SpringRunner.class)
@AutoConfigureWebTestClient
@SpringBootTest(properties = {"server.port=0"}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GetDifferencesIntegrationTest {

    @Autowired
    private WebTestClient webClient;

    private static final String ENDPOINT = "/v1/diff";

    @Test
    public void shouldThrowMissingValuesWhenValuesAreNull() {
        // when
        DiffRequest request = new DiffRequest();

        // expect
        webClient.post().uri(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(request))
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody(MissingValues.class);
    }

    @Test
    public void shouldThrowMissingValuesWhenValuesAreEmpty() {
        // when
        DiffRequest request = new DiffRequest("", "");

        // expect
        webClient.post().uri(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(request))
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody(MissingValues.class);
    }

    @Test
    public void shouldThrowValuesAreEqualWhenValuesHasSameValue() {
        // when
        DiffRequest request = new DiffRequest("SGFsbG8=", "SGFsbG8=");

        // expect
        webClient.post().uri(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(request))
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody(ValuesAreEqual.class);
    }

    @Test
    public void shouldThrowValuesHaveDifferentSizeWhenValuesHaveDifferentSizes() {
        // when
        DiffRequest request = new DiffRequest("SGFsbG8=", "SGFVsbG8=");

        // expect
        webClient.post().uri(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(request))
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody(ValuesAreEqual.class);
    }

    @Test
    public void shouldReturnDecodedValuesAndTheirDifferencesWhenThereAreDifferences() {
        // given
        DiffRequest request = new DiffRequest("SGFsbG8=", "SGVsbG8=");
        ValuePair decodedPair = new ValuePair("Hallo", "Hello");
        List<ValueDiff> pairDiffs = Collections.singletonList(new ValueDiff(1, 1));
        ValuePairDiffs diffs = new ValuePairDiffs(decodedPair, pairDiffs);
        DiffResponse response = new DiffResponse(diffs);
        // expect
        webClient.post().uri(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(request))
                .exchange()
                .expectStatus().isOk()
                .expectBody(DiffResponse.class)
                .isEqualTo(response);
    }

}
