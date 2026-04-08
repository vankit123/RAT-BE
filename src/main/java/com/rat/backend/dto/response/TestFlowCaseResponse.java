package com.rat.backend.dto.response;

import java.time.Instant;

public record TestFlowCaseResponse(
        Long id,
        Long testFlowId,
        Long testCaseId,
        Integer runOrder,
        Boolean required,
        Boolean stopOnFailure,
        Instant createdAt,
        Instant updatedAt
) {
}
