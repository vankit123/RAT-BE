package com.rat.backend.dto.response;

import java.time.Instant;

public record TestAssetResponse(
        Long id,
        Long projectId,
        String originalName,
        String storedName,
        String storedPath,
        String mimeType,
        Long sizeBytes,
        String checksum,
        Instant createdAt,
        Instant updatedAt
) {
}
