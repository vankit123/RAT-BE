package com.rat.backend.dto.request;

import java.time.Instant;

public record TestRunCaseRequest(
        Long testRunId,
        Long testCaseId,
        Long testDataSetId,
        String status,
        Instant startedAt,
        Instant endedAt,
        Long durationMs,
        String errorMessage
) {
}
