package com.rat.backend.dto.request;

public record ProjectRequest(
        String code,
        String name,
        String description,
        String baseUrl,
        String status
) {
}
