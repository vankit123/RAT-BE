package com.rat.backend.dto.request;

public record TestFlowRequest(
        Long projectId,
        String code,
        String name,
        String description,
        String type,
        String status
) {
}
