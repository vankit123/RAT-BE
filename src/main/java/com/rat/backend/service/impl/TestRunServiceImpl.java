package com.rat.backend.service.impl;

import com.rat.backend.dto.request.TestRunRequest;
import com.rat.backend.dto.response.TestDataSetResponse;
import com.rat.backend.dto.response.TestRunCaseDetailResponse;
import com.rat.backend.dto.response.TestRunDetailResponse;
import com.rat.backend.dto.response.TestRunResponse;
import com.rat.backend.entity.Project;
import com.rat.backend.entity.TestCase;
import com.rat.backend.entity.TestDataSet;
import com.rat.backend.entity.TestFlow;
import com.rat.backend.entity.TestRun;
import com.rat.backend.entity.TestRunCase;
import com.rat.backend.exception.ResourceNotFoundException;
import com.rat.backend.mapper.EntityMapper;
import com.rat.backend.repository.ProjectRepository;
import com.rat.backend.repository.TestCaseRepository;
import com.rat.backend.repository.TestDataSetRepository;
import com.rat.backend.repository.TestFlowRepository;
import com.rat.backend.repository.TestRunCaseRepository;
import com.rat.backend.repository.TestRunRepository;
import com.rat.backend.repository.TestRunStepRepository;
import com.rat.backend.service.TestRunService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TestRunServiceImpl implements TestRunService {

    private final TestRunRepository testRunRepository;
    private final ProjectRepository projectRepository;
    private final TestFlowRepository testFlowRepository;
    private final TestCaseRepository testCaseRepository;
    private final TestDataSetRepository testDataSetRepository;
    private final TestRunCaseRepository testRunCaseRepository;
    private final TestRunStepRepository testRunStepRepository;

    @Override
    @Transactional(readOnly = true)
    public List<TestRunResponse> getAll() {
        return testRunRepository.findAll().stream().map(EntityMapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public TestRunResponse getById(Long id) {
        return EntityMapper.toResponse(findTestRun(id));
    }

    @Override
    @Transactional(readOnly = true)
    public TestRunResponse getLatestByProjectId(Long projectId) {
        return testRunRepository.findTopByProjectIdOrderByCreatedAtDesc(projectId)
                .map(EntityMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Latest test run not found for project: " + projectId));
    }

    @Override
    @Transactional(readOnly = true)
    public TestRunDetailResponse getLatestDetailByProjectId(Long projectId) {
        TestRun testRun = testRunRepository.findTopByProjectIdOrderByCreatedAtDesc(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Latest test run not found for project: " + projectId));
        return toDetailResponse(testRun);
    }

    @Override
    public TestRunResponse create(TestRunRequest request) {
        TestRun testRun = new TestRun();
        apply(testRun, request);
        return EntityMapper.toResponse(testRunRepository.save(testRun));
    }

    @Override
    public TestRunResponse update(Long id, TestRunRequest request) {
        TestRun testRun = findTestRun(id);
        apply(testRun, request);
        return EntityMapper.toResponse(testRunRepository.save(testRun));
    }

    @Override
    public void delete(Long id) {
        testRunRepository.delete(findTestRun(id));
    }

    private TestRun findTestRun(Long id) {
        return testRunRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Test run not found: " + id));
    }

    private Project findProject(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found: " + id));
    }

    private TestFlow findTestFlow(Long id) {
        if (id == null) {
            return null;
        }
        return testFlowRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Test flow not found: " + id));
    }

    private TestCase findTestCase(Long id) {
        if (id == null) {
            return null;
        }
        return testCaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Test case not found: " + id));
    }

    private TestDataSet findDataSet(Long id) {
        if (id == null) {
            return null;
        }
        return testDataSetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Test data set not found: " + id));
    }

    private void apply(TestRun testRun, TestRunRequest request) {
        testRun.setProject(findProject(request.projectId()));
        testRun.setTestFlow(findTestFlow(request.testFlowId()));
        testRun.setTestCase(findTestCase(request.testCaseId()));
        testRun.setTestDataSet(findDataSet(request.testDataSetId()));
        testRun.setRunType(request.runType());
        testRun.setStatus(request.status());
        testRun.setStartedAt(request.startedAt());
        testRun.setEndedAt(request.endedAt());
        testRun.setDurationMs(request.durationMs());
        // Temporarily disabled until S3/object storage integration is finalized.
        // testRun.setVideoPath(request.videoPath());
        // testRun.setVideoUrl(request.videoUrl());
    }

    private TestRunDetailResponse toDetailResponse(TestRun testRun) {
        List<TestRunCaseDetailResponse> cases = testRunCaseRepository.findByTestRunIdOrderByIdAsc(testRun.getId())
                .stream()
                .map(this::toCaseDetailResponse)
                .toList();
        TestDataSetResponse dataSet = testRun.getTestDataSet() == null
                ? null
                : EntityMapper.toResponse(testRun.getTestDataSet());

        return new TestRunDetailResponse(
                testRun.getId(),
                testRun.getProject().getId(),
                testRun.getTestFlow() == null ? null : testRun.getTestFlow().getId(),
                testRun.getTestCase() == null ? null : testRun.getTestCase().getId(),
                testRun.getTestDataSet() == null ? null : testRun.getTestDataSet().getId(),
                testRun.getRunType(),
                testRun.getStatus(),
                testRun.getStartedAt(),
                testRun.getEndedAt(),
                testRun.getDurationMs(),
                testRun.getVideoPath(),
                testRun.getVideoUrl(),
                testRun.getCreatedAt(),
                testRun.getUpdatedAt(),
                dataSet,
                cases
        );
    }

    private TestRunCaseDetailResponse toCaseDetailResponse(TestRunCase runCase) {
        return new TestRunCaseDetailResponse(
                runCase.getId(),
                runCase.getTestRun().getId(),
                runCase.getTestCase() == null ? null : runCase.getTestCase().getId(),
                runCase.getTestDataSet() == null ? null : runCase.getTestDataSet().getId(),
                runCase.getStatus(),
                runCase.getStartedAt(),
                runCase.getEndedAt(),
                runCase.getDurationMs(),
                runCase.getErrorMessage(),
                runCase.getCreatedAt(),
                runCase.getUpdatedAt(),
                testRunStepRepository.findByTestRunCaseIdOrderByStepOrderAsc(runCase.getId())
                        .stream()
                        .map(EntityMapper::toResponse)
                        .toList()
        );
    }
}
