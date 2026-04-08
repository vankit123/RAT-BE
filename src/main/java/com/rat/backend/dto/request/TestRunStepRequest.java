package com.rat.backend.dto.request;

import java.time.Instant;

public record TestRunStepRequest(
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
        String errorMessage
) {
}
