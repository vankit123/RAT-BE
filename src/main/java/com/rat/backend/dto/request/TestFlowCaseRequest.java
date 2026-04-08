package com.rat.backend.dto.request;

public record TestFlowCaseRequest(
        Long testFlowId,
        Long testCaseId,
        Integer runOrder,
        Boolean required,
        Boolean stopOnFailure
) {
}
