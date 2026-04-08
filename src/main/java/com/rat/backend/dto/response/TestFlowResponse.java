package com.rat.backend.dto.response;

import java.time.Instant;

public record TestFlowResponse(
        Long id,
        Long projectId,
        String code,
        String name,
        String description,
        String type,
        String status,
        Instant createdAt,
        Instant updatedAt
) {
}
