package com.rat.backend.service.impl;

import com.rat.backend.dto.request.TestCaseDataSetRequest;
import com.rat.backend.dto.response.TestCaseDataSetResponse;
import com.rat.backend.entity.TestCase;
import com.rat.backend.entity.TestCaseDataSet;
import com.rat.backend.entity.TestDataSet;
import com.rat.backend.exception.ResourceNotFoundException;
import com.rat.backend.mapper.EntityMapper;
import com.rat.backend.repository.TestCaseDataSetRepository;
import com.rat.backend.repository.TestCaseRepository;
import com.rat.backend.repository.TestDataSetRepository;
import com.rat.backend.service.TestCaseDataSetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TestCaseDataSetServiceImpl implements TestCaseDataSetService {

    private final TestCaseDataSetRepository testCaseDataSetRepository;
    private final TestCaseRepository testCaseRepository;
    private final TestDataSetRepository testDataSetRepository;

    @Override
    @Transactional(readOnly = true)
    public List<TestCaseDataSetResponse> getAll() {
        return testCaseDataSetRepository.findAll().stream().map(EntityMapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public TestCaseDataSetResponse getById(Long id) {
        return EntityMapper.toResponse(findCaseDataSet(id));
    }

    @Override
    public TestCaseDataSetResponse create(TestCaseDataSetRequest request) {
        TestCaseDataSet caseDataSet = new TestCaseDataSet();
        apply(caseDataSet, request);
        return EntityMapper.toResponse(testCaseDataSetRepository.save(caseDataSet));
    }

    @Override
    public TestCaseDataSetResponse update(Long id, TestCaseDataSetRequest request) {
        TestCaseDataSet caseDataSet = findCaseDataSet(id);
        apply(caseDataSet, request);
        return EntityMapper.toResponse(testCaseDataSetRepository.save(caseDataSet));
    }

    @Override
    public void delete(Long id) {
        testCaseDataSetRepository.delete(findCaseDataSet(id));
    }

    private TestCaseDataSet findCaseDataSet(Long id) {
        return testCaseDataSetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Test case data set not found: " + id));
    }

    private TestCase findTestCase(Long id) {
        return testCaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Test case not found: " + id));
    }

    private TestDataSet findDataSet(Long id) {
        return testDataSetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Test data set not found: " + id));
    }

    private void apply(TestCaseDataSet caseDataSet, TestCaseDataSetRequest request) {
        caseDataSet.setTestCase(findTestCase(request.testCaseId()));
        caseDataSet.setTestDataSet(findDataSet(request.testDataSetId()));
    }
}
