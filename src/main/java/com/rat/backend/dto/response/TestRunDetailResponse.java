package com.rat.backend.dto.response;

import java.time.Instant;
import java.util.List;

public record TestRunDetailResponse(
        Long id,
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
        String videoUrl,
        Instant createdAt,
        Instant updatedAt,
        TestDataSetResponse testDataSet,
        List<TestRunCaseDetailResponse> cases
) {
}
