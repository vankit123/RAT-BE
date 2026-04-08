package com.rat.backend.dto.response;

import java.time.Instant;

public record TestRunCaseResponse(
        Long id,
        Long testRunId,
        Long testCaseId,
        Long testDataSetId,
        String status,
        Instant startedAt,
        Instant endedAt,
        Long durationMs,
        String errorMessage,
        Instant createdAt,
        Instant updatedAt
) {
}
