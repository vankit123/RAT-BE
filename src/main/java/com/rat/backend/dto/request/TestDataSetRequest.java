package com.rat.backend.dto.request;

import java.util.Map;

public record TestDataSetRequest(
        Long projectId,
        String code,
        String name,
        String description,
        Map<String, Object> dataJson,
        Map<String, Object> expectedJson,
        String status
) {
}
