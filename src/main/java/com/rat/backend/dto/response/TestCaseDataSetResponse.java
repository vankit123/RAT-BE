package com.rat.backend.dto.response;

import java.time.Instant;

public record TestCaseDataSetResponse(
        Long id,
        Long testCaseId,
        Long testDataSetId,
        Instant createdAt,
        Instant updatedAt
) {
}
