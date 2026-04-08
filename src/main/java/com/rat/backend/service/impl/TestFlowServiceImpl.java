package com.rat.backend.service.impl;

import com.rat.backend.dto.request.TestFlowRequest;
import com.rat.backend.dto.response.TestFlowResponse;
import com.rat.backend.entity.Project;
import com.rat.backend.entity.TestFlow;
import com.rat.backend.exception.ResourceNotFoundException;
import com.rat.backend.mapper.EntityMapper;
import com.rat.backend.repository.ProjectRepository;
import com.rat.backend.repository.TestFlowRepository;
import com.rat.backend.service.TestFlowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TestFlowServiceImpl implements TestFlowService {

    private final TestFlowRepository testFlowRepository;
    private final ProjectRepository projectRepository;

    @Override
    @Transactional(readOnly = true)
    public List<TestFlowResponse> getAll() {
        return testFlowRepository.findAll().stream().map(EntityMapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public TestFlowResponse getById(Long id) {
        return EntityMapper.toResponse(findTestFlow(id));
    }

    @Override
    public TestFlowResponse create(TestFlowRequest request) {
        TestFlow testFlow = new TestFlow();
        apply(testFlow, request);
        return EntityMapper.toResponse(testFlowRepository.save(testFlow));
    }

    @Override
    public TestFlowResponse update(Long id, TestFlowRequest request) {
        TestFlow testFlow = findTestFlow(id);
        apply(testFlow, request);
        return EntityMapper.toResponse(testFlowRepository.save(testFlow));
    }

    @Override
    public void delete(Long id) {
        testFlowRepository.delete(findTestFlow(id));
    }

    private TestFlow findTestFlow(Long id) {
        return testFlowRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Test flow not found: " + id));
    }

    private Project findProject(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found: " + id));
    }

    private void apply(TestFlow testFlow, TestFlowRequest request) {
        testFlow.setProject(findProject(request.projectId()));
        testFlow.setCode(request.code());
        testFlow.setName(request.name());
        testFlow.setDescription(request.description());
        testFlow.setType(request.type());
        testFlow.setStatus(request.status());
    }
}
