package com.rat.backend.dto.request;

import java.time.Instant;

public record TestRunRequest(
        Long projectId,
        Long testFlowId,
        Long testCaseId,
        Long testDataSetId,
        String runType,
        String status,
        Instant startedAt,
        Instant endedAt,
        Long durationMs,
        String videoPath,
        String videoUrl
) {
}
