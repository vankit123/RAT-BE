package com.rat.backend.service.impl;

import com.rat.backend.dto.request.TestFlowCaseRequest;
import com.rat.backend.dto.response.TestFlowCaseResponse;
import com.rat.backend.entity.TestCase;
import com.rat.backend.entity.TestFlow;
import com.rat.backend.entity.TestFlowCase;
import com.rat.backend.exception.ResourceNotFoundException;
import com.rat.backend.mapper.EntityMapper;
import com.rat.backend.repository.TestCaseRepository;
import com.rat.backend.repository.TestFlowCaseRepository;
import com.rat.backend.repository.TestFlowRepository;
import com.rat.backend.service.TestFlowCaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TestFlowCaseServiceImpl implements TestFlowCaseService {

    private final TestFlowCaseRepository testFlowCaseRepository;
    private final TestFlowRepository testFlowRepository;
    private final TestCaseRepository testCaseRepository;

    @Override
    @Transactional(readOnly = true)
    public List<TestFlowCaseResponse> getAll() {
        return testFlowCaseRepository.findAll().stream().map(EntityMapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public TestFlowCaseResponse getById(Long id) {
        return EntityMapper.toResponse(findFlowCase(id));
    }

    @Override
    public TestFlowCaseResponse create(TestFlowCaseRequest request) {
        TestFlowCase flowCase = new TestFlowCase();
        apply(flowCase, request);
        return EntityMapper.toResponse(testFlowCaseRepository.save(flowCase));
    }

    @Override
    public TestFlowCaseResponse update(Long id, TestFlowCaseRequest request) {
        TestFlowCase flowCase = findFlowCase(id);
        apply(flowCase, request);
        return EntityMapper.toResponse(testFlowCaseRepository.save(flowCase));
    }

    @Override
    public void delete(Long id) {
        testFlowCaseRepository.delete(findFlowCase(id));
    }

    private TestFlowCase findFlowCase(Long id) {
        return testFlowCaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Test flow case not found: " + id));
    }

    private TestFlow findTestFlow(Long id) {
        return testFlowRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Test flow not found: " + id));
    }

    private TestCase findTestCase(Long id) {
        return testCaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Test case not found: " + id));
    }

    private void apply(TestFlowCase flowCase, TestFlowCaseRequest request) {
        flowCase.setTestFlow(findTestFlow(request.testFlowId()));
        flowCase.setTestCase(findTestCase(request.testCaseId()));
        flowCase.setRunOrder(request.runOrder());
        flowCase.setRequired(request.required());
        flowCase.setStopOnFailure(request.stopOnFailure());
    }
}
