package com.rat.backend.service.impl;

import com.rat.backend.dto.request.TestCaseStepRequest;
import com.rat.backend.dto.response.TestCaseStepResponse;
import com.rat.backend.entity.TestCase;
import com.rat.backend.entity.TestCaseStep;
import com.rat.backend.exception.ResourceNotFoundException;
import com.rat.backend.mapper.EntityMapper;
import com.rat.backend.repository.TestCaseRepository;
import com.rat.backend.repository.TestCaseStepRepository;
import com.rat.backend.repository.TestRunStepRepository;
import com.rat.backend.service.TestCaseStepService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TestCaseStepServiceImpl implements TestCaseStepService {

    private final TestCaseStepRepository testCaseStepRepository;
    private final TestCaseRepository testCaseRepository;
    private final TestRunStepRepository testRunStepRepository;

    @Override
    @Transactional(readOnly = true)
    public List<TestCaseStepResponse> getAll() {
        return testCaseStepRepository.findAll().stream().map(EntityMapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public TestCaseStepResponse getById(Long id) {
        return EntityMapper.toResponse(findStep(id));
    }

    @Override
    public TestCaseStepResponse create(TestCaseStepRequest request) {
        TestCaseStep step = new TestCaseStep();
        apply(step, request);
        return EntityMapper.toResponse(testCaseStepRepository.save(step));
    }

    @Override
    public TestCaseStepResponse update(Long id, TestCaseStepRequest request) {
        TestCaseStep step = findStep(id);
        apply(step, request);
        return EntityMapper.toResponse(testCaseStepRepository.save(step));
    }

    @Override
    public void delete(Long id) {
        TestCaseStep step = findStep(id);
        testRunStepRepository.unlinkTestCaseStep(id);
        testCaseStepRepository.delete(step);
    }

    private TestCaseStep findStep(Long id) {
        return testCaseStepRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Test case step not found: " + id));
    }

    private TestCase findTestCase(Long id) {
        return testCaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Test case not found: " + id));
    }

    private void apply(TestCaseStep step, TestCaseStepRequest request) {
        step.setTestCase(findTestCase(request.testCaseId()));
        step.setStepOrder(request.stepOrder());
        step.setActionType(request.actionType());
        step.setTarget(request.target());
        step.setValue(request.value());
        step.setExpectedValue(request.expectedValue());
        step.setDescription(request.description());
    }
}
