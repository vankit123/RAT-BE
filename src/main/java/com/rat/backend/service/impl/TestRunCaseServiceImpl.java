package com.rat.backend.service.impl;

import com.rat.backend.dto.request.TestRunCaseRequest;
import com.rat.backend.dto.response.TestRunCaseResponse;
import com.rat.backend.entity.TestCase;
import com.rat.backend.entity.TestDataSet;
import com.rat.backend.entity.TestRun;
import com.rat.backend.entity.TestRunCase;
import com.rat.backend.exception.ResourceNotFoundException;
import com.rat.backend.mapper.EntityMapper;
import com.rat.backend.repository.TestCaseRepository;
import com.rat.backend.repository.TestDataSetRepository;
import com.rat.backend.repository.TestRunCaseRepository;
import com.rat.backend.repository.TestRunRepository;
import com.rat.backend.service.TestRunCaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TestRunCaseServiceImpl implements TestRunCaseService {

    private final TestRunCaseRepository testRunCaseRepository;
    private final TestRunRepository testRunRepository;
    private final TestCaseRepository testCaseRepository;
    private final TestDataSetRepository testDataSetRepository;

    @Override
    @Transactional(readOnly = true)
    public List<TestRunCaseResponse> getAll() {
        return testRunCaseRepository.findAll().stream().map(EntityMapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public TestRunCaseResponse getById(Long id) {
        return EntityMapper.toResponse(findRunCase(id));
    }

    @Override
    public TestRunCaseResponse create(TestRunCaseRequest request) {
        TestRunCase runCase = new TestRunCase();
        apply(runCase, request);
        return EntityMapper.toResponse(testRunCaseRepository.save(runCase));
    }

    @Override
    public TestRunCaseResponse update(Long id, TestRunCaseRequest request) {
        TestRunCase runCase = findRunCase(id);
        apply(runCase, request);
        return EntityMapper.toResponse(testRunCaseRepository.save(runCase));
    }

    @Override
    public void delete(Long id) {
        testRunCaseRepository.delete(findRunCase(id));
    }

    private TestRunCase findRunCase(Long id) {
        return testRunCaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Test run case not found: " + id));
    }

    private TestRun findTestRun(Long id) {
        return testRunRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Test run not found: " + id));
    }

    private TestCase findTestCase(Long id) {
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

    private void apply(TestRunCase runCase, TestRunCaseRequest request) {
        runCase.setTestRun(findTestRun(request.testRunId()));
        runCase.setTestCase(findTestCase(request.testCaseId()));
        runCase.setTestDataSet(findDataSet(request.testDataSetId()));
        runCase.setStatus(request.status());
        runCase.setStartedAt(request.startedAt());
        runCase.setEndedAt(request.endedAt());
        runCase.setDurationMs(request.durationMs());
        runCase.setErrorMessage(request.errorMessage());
    }
}
