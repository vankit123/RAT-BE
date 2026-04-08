package com.rat.backend.dto.response;

import java.time.Instant;

public record TestCaseStepResponse(
        Long id,
        Long testCaseId,
        Integer stepOrder,
        String actionType,
        String target,
        String value,
        String expectedValue,
        String description,
        Instant createdAt,
        Instant updatedAt
) {
}
