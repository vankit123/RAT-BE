package com.rat.backend.dto.response;

import java.time.Instant;
import java.util.List;

public record TestRunCaseDetailResponse(
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
        Instant updatedAt,
        List<TestRunStepResponse> steps
) {
}
