package com.rat.backend.mapper;

import com.rat.backend.dto.response.ProjectResponse;
import com.rat.backend.dto.response.TestAssetResponse;
import com.rat.backend.dto.response.TestCaseDataSetResponse;
import com.rat.backend.dto.response.TestCaseResponse;
import com.rat.backend.dto.response.TestCaseStepResponse;
import com.rat.backend.dto.response.TestDataSetResponse;
import com.rat.backend.dto.response.TestFlowCaseResponse;
import com.rat.backend.dto.response.TestFlowResponse;
import com.rat.backend.dto.response.TestRunCaseResponse;
import com.rat.backend.dto.response.TestRunResponse;
import com.rat.backend.dto.response.TestRunStepResponse;
import com.rat.backend.entity.Project;
import com.rat.backend.entity.TestAsset;
import com.rat.backend.entity.TestCase;
import com.rat.backend.entity.TestCaseDataSet;
import com.rat.backend.entity.TestCaseStep;
import com.rat.backend.entity.TestDataSet;
import com.rat.backend.entity.TestFlow;
import com.rat.backend.entity.TestFlowCase;
import com.rat.backend.entity.TestRun;
import com.rat.backend.entity.TestRunCase;
import com.rat.backend.entity.TestRunStep;

public final class EntityMapper {

    private EntityMapper() {
    }

    public static ProjectResponse toResponse(Project entity) {
        return new ProjectResponse(entity.getId(), entity.getCode(), entity.getName(), entity.getDescription(),
                entity.getBaseUrl(), entity.getStatus(), entity.getCreatedAt(), entity.getUpdatedAt());
    }

    public static TestCaseResponse toResponse(TestCase entity) {
        return new TestCaseResponse(entity.getId(), entity.getProject().getId(), entity.getCode(), entity.getName(),
                entity.getDescription(), entity.getType(), entity.getStatus(), entity.getCreatedAt(), entity.getUpdatedAt());
    }

    public static TestCaseStepResponse toResponse(TestCaseStep entity) {
        return new TestCaseStepResponse(entity.getId(), entity.getTestCase().getId(), entity.getStepOrder(),
                entity.getActionType(), entity.getTarget(), entity.getValue(), entity.getExpectedValue(),
                entity.getDescription(), entity.getCreatedAt(), entity.getUpdatedAt());
    }

    public static TestFlowResponse toResponse(TestFlow entity) {
        return new TestFlowResponse(entity.getId(), entity.getProject().getId(), entity.getCode(), entity.getName(),
                entity.getDescription(), entity.getType(), entity.getStatus(), entity.getCreatedAt(), entity.getUpdatedAt());
    }

    public static TestFlowCaseResponse toResponse(TestFlowCase entity) {
        return new TestFlowCaseResponse(entity.getId(), entity.getTestFlow().getId(), entity.getTestCase().getId(),
                entity.getRunOrder(), entity.getRequired(), entity.getStopOnFailure(), entity.getCreatedAt(),
                entity.getUpdatedAt());
    }

    public static TestDataSetResponse toResponse(TestDataSet entity) {
        return new TestDataSetResponse(entity.getId(), entity.getProject().getId(), entity.getCode(), entity.getName(),
                entity.getDescription(), entity.getDataJson(), entity.getExpectedJson(), entity.getStatus(),
                entity.getCreatedAt(), entity.getUpdatedAt());
    }

    public static TestAssetResponse toResponse(TestAsset entity) {
        return new TestAssetResponse(entity.getId(), entity.getProject().getId(), entity.getOriginalName(),
                entity.getStoredName(), entity.getStoredPath(), entity.getMimeType(), entity.getSizeBytes(),
                entity.getChecksum(), entity.getCreatedAt(), entity.getUpdatedAt());
    }

    public static TestCaseDataSetResponse toResponse(TestCaseDataSet entity) {
        return new TestCaseDataSetResponse(entity.getId(), entity.getTestCase().getId(),
                entity.getTestDataSet().getId(), entity.getCreatedAt(), entity.getUpdatedAt());
    }

    public static TestRunResponse toResponse(TestRun entity) {
        return new TestRunResponse(entity.getId(), entity.getProject().getId(), idOf(entity.getTestFlow()),
                idOf(entity.getTestCase()), idOf(entity.getTestDataSet()), entity.getRunType(), entity.getStatus(),
                entity.getStartedAt(), entity.getEndedAt(), entity.getDurationMs(), entity.getVideoPath(),
                entity.getVideoUrl(), entity.getCreatedAt(), entity.getUpdatedAt());
    }

    public static TestRunCaseResponse toResponse(TestRunCase entity) {
        return new TestRunCaseResponse(entity.getId(), entity.getTestRun().getId(), idOf(entity.getTestCase()),
                idOf(entity.getTestDataSet()), entity.getStatus(), entity.getStartedAt(), entity.getEndedAt(),
                entity.getDurationMs(), entity.getErrorMessage(), entity.getCreatedAt(), entity.getUpdatedAt());
    }

    public static TestRunStepResponse toResponse(TestRunStep entity) {
        return new TestRunStepResponse(entity.getId(), entity.getTestRunCase().getId(), idOf(entity.getTestCaseStep()),
                entity.getStepOrder(), entity.getActionType(), entity.getTarget(), entity.getValue(),
                entity.getExpectedValue(), entity.getActualValue(), entity.getStatus(), entity.getStartedAt(),
                entity.getEndedAt(), entity.getDurationMs(), entity.getErrorMessage(), entity.getCreatedAt(),
                entity.getUpdatedAt());
    }

    private static Long idOf(Project entity) {
        return entity == null ? null : entity.getId();
    }

    private static Long idOf(TestCase entity) {
        return entity == null ? null : entity.getId();
    }

    private static Long idOf(TestCaseStep entity) {
        return entity == null ? null : entity.getId();
    }

    private static Long idOf(TestFlow entity) {
        return entity == null ? null : entity.getId();
    }

    private static Long idOf(TestDataSet entity) {
        return entity == null ? null : entity.getId();
    }
}
