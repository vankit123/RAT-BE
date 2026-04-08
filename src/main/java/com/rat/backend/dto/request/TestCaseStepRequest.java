package com.rat.backend.dto.request;

public record TestCaseStepRequest(
        Long testCaseId,
        Integer stepOrder,
        String actionType,
        String target,
        String value,
        String expectedValue,
        String description
) {
}
