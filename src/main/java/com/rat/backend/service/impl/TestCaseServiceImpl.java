package com.rat.backend.service.impl;

import com.rat.backend.dto.request.TestCaseRequest;
import com.rat.backend.dto.response.TestCaseResponse;
import com.rat.backend.entity.Project;
import com.rat.backend.entity.TestCase;
import com.rat.backend.exception.ResourceNotFoundException;
import com.rat.backend.mapper.EntityMapper;
import com.rat.backend.repository.ProjectRepository;
import com.rat.backend.repository.TestCaseDataSetRepository;
import com.rat.backend.repository.TestCaseRepository;
import com.rat.backend.repository.TestCaseStepRepository;
import com.rat.backend.repository.TestFlowCaseRepository;
import com.rat.backend.repository.TestRunCaseRepository;
import com.rat.backend.repository.TestRunRepository;
import com.rat.backend.repository.TestRunStepRepository;
import com.rat.backend.service.TestCaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TestCaseServiceImpl implements TestCaseService {

    private final TestCaseRepository testCaseRepository;
    private final ProjectRepository projectRepository;
    private final TestCaseStepRepository testCaseStepRepository;
    private final TestCaseDataSetRepository testCaseDataSetRepository;
    private final TestFlowCaseRepository testFlowCaseRepository;
    private final TestRunRepository testRunRepository;
    private final TestRunCaseRepository testRunCaseRepository;
    private final TestRunStepRepository testRunStepRepository;

    @Override
    @Transactional(readOnly = true)
    public List<TestCaseResponse> getAll() {
        return testCaseRepository.findAll().stream().map(EntityMapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public TestCaseResponse getById(Long id) {
        return EntityMapper.toResponse(findTestCase(id));
    }

    @Override
    public TestCaseResponse create(TestCaseRequest request) {
        TestCase testCase = new TestCase();
        apply(testCase, request);
        return EntityMapper.toResponse(testCaseRepository.save(testCase));
    }

    @Override
    public TestCaseResponse update(Long id, TestCaseRequest request) {
        TestCase testCase = findTestCase(id);
        apply(testCase, request);
        return EntityMapper.toResponse(testCaseRepository.save(testCase));
    }

    @Override
    public void delete(Long id) {
        TestCase testCase = findTestCase(id);
        testRunStepRepository.unlinkTestCaseStepsByTestCaseId(id);
        testCaseStepRepository.deleteByTestCaseId(id);
        testCaseDataSetRepository.deleteByTestCaseId(id);
        testFlowCaseRepository.deleteByTestCaseId(id);
        testRunRepository.unlinkTestCase(id);
        testRunCaseRepository.unlinkTestCase(id);
        testCaseRepository.delete(testCase);
    }

    private TestCase findTestCase(Long id) {
        return testCaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Test case not found: " + id));
    }

    private Project findProject(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found: " + id));
    }

    private void apply(TestCase testCase, TestCaseRequest request) {
        testCase.setProject(findProject(request.projectId()));
        testCase.setCode(request.code());
        testCase.setName(request.name());
        testCase.setDescription(request.description());
        testCase.setType(request.type());
        testCase.setStatus(request.status());
    }
}
