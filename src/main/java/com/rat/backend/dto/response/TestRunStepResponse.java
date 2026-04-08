package com.rat.backend.dto.response;

import java.time.Instant;

public record TestRunStepResponse(
        Long id,
        Long testRunCaseId,
        Long testCaseStepId,
        Integer stepOrder,
        String actionType,
        String target,
        String value,
        String expectedValue,
        String actualValue,
        String status,
        Instant startedAt,
        Instant endedAt,
        Long durationMs,
        String errorMessage,
        Instant createdAt,
        Instant updatedAt
) {
}
