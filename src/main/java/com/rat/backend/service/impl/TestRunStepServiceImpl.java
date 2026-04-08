package com.rat.backend.service.impl;

import com.rat.backend.dto.request.TestRunStepRequest;
import com.rat.backend.dto.response.TestRunStepResponse;
import com.rat.backend.entity.TestCaseStep;
import com.rat.backend.entity.TestRunCase;
import com.rat.backend.entity.TestRunStep;
import com.rat.backend.exception.ResourceNotFoundException;
import com.rat.backend.mapper.EntityMapper;
import com.rat.backend.repository.TestCaseStepRepository;
import com.rat.backend.repository.TestRunCaseRepository;
import com.rat.backend.repository.TestRunStepRepository;
import com.rat.backend.service.TestRunStepService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TestRunStepServiceImpl implements TestRunStepService {

    private final TestRunStepRepository testRunStepRepository;
    private final TestRunCaseRepository testRunCaseRepository;
    private final TestCaseStepRepository testCaseStepRepository;

    @Override
    @Transactional(readOnly = true)
    public List<TestRunStepResponse> getAll() {
        return testRunStepRepository.findAll().stream().map(EntityMapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public TestRunStepResponse getById(Long id) {
        return EntityMapper.toResponse(findRunStep(id));
    }

    @Override
    public TestRunStepResponse create(TestRunStepRequest request) {
        TestRunStep runStep = new TestRunStep();
        apply(runStep, request);
        return EntityMapper.toResponse(testRunStepRepository.save(runStep));
    }

    @Override
    public TestRunStepResponse update(Long id, TestRunStepRequest request) {
        TestRunStep runStep = findRunStep(id);
        apply(runStep, request);
        return EntityMapper.toResponse(testRunStepRepository.save(runStep));
    }

    @Override
    public void delete(Long id) {
        testRunStepRepository.delete(findRunStep(id));
    }

    private TestRunStep findRunStep(Long id) {
        return testRunStepRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Test run step not found: " + id));
    }

    private TestRunCase findRunCase(Long id) {
        return testRunCaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Test run case not found: " + id));
    }

    private TestCaseStep findCaseStep(Long id) {
        if (id == null) {
            return null;
        }
        return testCaseStepRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Test case step not found: " + id));
    }

    private void apply(TestRunStep runStep, TestRunStepRequest request) {
        runStep.setTestRunCase(findRunCase(request.testRunCaseId()));
        runStep.setTestCaseStep(findCaseStep(request.testCaseStepId()));
        runStep.setStepOrder(request.stepOrder());
        runStep.setActionType(request.actionType());
        runStep.setTarget(request.target());
        runStep.setValue(request.value());
        runStep.setExpectedValue(request.expectedValue());
        runStep.setActualValue(request.actualValue());
        runStep.setStatus(request.status());
        runStep.setStartedAt(request.startedAt());
        runStep.setEndedAt(request.endedAt());
        runStep.setDurationMs(request.durationMs());
        runStep.setErrorMessage(request.errorMessage());
    }
}
