package com.rat.backend.dto.response;

import java.time.Instant;
import java.util.Map;

public record TestDataSetResponse(
        Long id,
        Long projectId,
        String code,
        String name,
        String description,
        Map<String, Object> dataJson,
        Map<String, Object> expectedJson,
        String status,
        Instant createdAt,
        Instant updatedAt
) {
}
