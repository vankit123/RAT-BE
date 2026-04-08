package com.rat.backend.service.impl;

import com.rat.backend.dto.request.TestDataSetRequest;
import com.rat.backend.dto.response.TestDataSetResponse;
import com.rat.backend.entity.Project;
import com.rat.backend.entity.TestDataSet;
import com.rat.backend.exception.ResourceNotFoundException;
import com.rat.backend.mapper.EntityMapper;
import com.rat.backend.repository.TestCaseDataSetRepository;
import com.rat.backend.repository.ProjectRepository;
import com.rat.backend.repository.TestDataSetRepository;
import com.rat.backend.repository.TestRunCaseRepository;
import com.rat.backend.repository.TestRunRepository;
import com.rat.backend.service.TestDataSetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TestDataSetServiceImpl implements TestDataSetService {

    private final TestDataSetRepository testDataSetRepository;
    private final ProjectRepository projectRepository;
    private final TestCaseDataSetRepository testCaseDataSetRepository;
    private final TestRunRepository testRunRepository;
    private final TestRunCaseRepository testRunCaseRepository;

    @Override
    @Transactional(readOnly = true)
    public List<TestDataSetResponse> getAll() {
        return testDataSetRepository.findAll().stream().map(EntityMapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public TestDataSetResponse getById(Long id) {
        return EntityMapper.toResponse(findDataSet(id));
    }

    @Override
    public TestDataSetResponse create(TestDataSetRequest request) {
        TestDataSet dataSet = new TestDataSet();
        apply(dataSet, request);
        return EntityMapper.toResponse(testDataSetRepository.save(dataSet));
    }

    @Override
    public TestDataSetResponse update(Long id, TestDataSetRequest request) {
        TestDataSet dataSet = findDataSet(id);
        apply(dataSet, request);
        return EntityMapper.toResponse(testDataSetRepository.save(dataSet));
    }

    @Override
    public void delete(Long id) {
        TestDataSet dataSet = findDataSet(id);
        testRunRepository.unlinkTestDataSet(id);
        testRunCaseRepository.unlinkTestDataSet(id);
        testCaseDataSetRepository.deleteByTestDataSetId(id);
        testDataSetRepository.delete(dataSet);
    }

    private TestDataSet findDataSet(Long id) {
        return testDataSetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Test data set not found: " + id));
    }

    private Project findProject(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found: " + id));
    }

    private void apply(TestDataSet dataSet, TestDataSetRequest request) {
        dataSet.setProject(findProject(request.projectId()));
        dataSet.setCode(request.code());
        dataSet.setName(request.name());
        dataSet.setDescription(request.description());
        dataSet.setDataJson(request.dataJson());
        dataSet.setExpectedJson(request.expectedJson());
        dataSet.setStatus(request.status());
    }
}
