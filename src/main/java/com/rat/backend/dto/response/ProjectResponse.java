package com.rat.backend.dto.response;

import java.time.Instant;

public record ProjectResponse(
        Long id,
        String code,
        String name,
        String description,
        String baseUrl,
        String status,
        Instant createdAt,
        Instant updatedAt
) {
}
